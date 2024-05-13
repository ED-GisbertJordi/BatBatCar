package controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.time.LocalDateTime;

import entidades.Usuario;
import entidades.Viaje;
import entidades.tiposViajes.*;
import excepciones.FechaPasadaException;

import gestores.ViajesManager;
import views.GestorIO;
import views.ListadoViajesView;
import excepciones.ViajeNoValidoException;

public class ViajesController {

    private Usuario user;
    private ViajesManager gestor;

    public ViajesController(Usuario user) {
        this.gestor = new ViajesManager();

        /* Por defecto, no hay establecido ningún usuario. Se deberá establecer a posteriori
         Si no quieres realizar el caso de uso 1 hasta el final puedes establecer un usuario
         por defecto. Por ejemplo: this.usuario = new Usuario("roberto1979", "12345"); */
        this.user = user;
    }

    public void setUsuario(Usuario usuario) {
        this.user = usuario;
    }
    
    private void printViajes(List<Viaje> lista) {
        (new ListadoViajesView(lista)).visualizar();
    }

    /**
     * Lista todos los viajes del sistema.
     */
    public void listarViajes() {
        printViajes(gestor.findAll());
    }

    private List getViajesCancelables() {
        List<Viaje> viajes = gestor.findAll();
        List<Viaje> viajesCancelabes = new ArrayList<>();
        for (Viaje viaje : viajes) {
            if (user.equals(viaje.getPropietario()) && (viaje instanceof ViajeCancelable || viaje instanceof ViajeFlexible)) {
                viajesCancelabes.add(viaje);
            }
        }
        return viajesCancelabes;
    }

    public void listarViajesCancelabes() {
        printViajes(getViajesCancelables());
    }

    private List getViajesReservables() {
        List<Viaje> viajes = gestor.findAll();
        List<Viaje> viajesReservables = new ArrayList<>();
        for (Viaje viaje : viajes) {
            if (!user.equals(viaje.getPropietario()) && !viaje.getCancelado() && !viaje.getCerrado()) {
                viajesReservables.add(viaje);
            }
        }
        return viajesReservables;
    }

    public void listarViajesReservables() {
        printViajes(getViajesReservables());
    }
    
    /**
     * Añade un viaje al sistema, preguntando previamente por toda la
     * información necesaria para crearlo.
     */
    public void anyadirViaje(Usuario propietario) throws ViajeNoValidoException{
        final int ESTANDAR = 1;
        final int CANCELABLE = 2;
        final int EXCLUSIVO = 3;
        final int FLEXIBLE = 4;
        
        final int MIN_PLAZAS = 1;
        final int MIN_PRECIO = 1;
        final int MIN_DURACION = 1;
        
        int numeroTipoViaje = GestorIO.getInt(
                ESTANDAR + "- Viaje Estánndar\n"
                + CANCELABLE + "- Viaje Cancelable\n"
                + EXCLUSIVO + "- Viaje Exclusivo\n"
                + FLEXIBLE + "- Viaje Flexible\n"
                + "Seleccione el tipo de viaje");

        String ruta = GestorIO.getString("Introduzca la ruta a realizar (Ej: Alcoy-Alicante)");
        LocalDateTime horaSalida = GestorIO.getFecheHora();
        int duracion = GestorIO.getInt("Introduzca la duracion del viaje en minutos", MIN_DURACION, Integer.MAX_VALUE);
        double precio = GestorIO.getInt("Introduzca el precio de cada plaza", MIN_PRECIO, Integer.MAX_VALUE);
        int plazasTotales = GestorIO.getInt("Introduzca la nuemro de plazas", MIN_PLAZAS , Integer.MAX_VALUE);
        int plazasOfertadas = GestorIO.getInt("Introduzca la nuemro de plazas disponibles", MIN_PLAZAS , Integer.MAX_VALUE);

        Viaje nuevo = null;
        switch (numeroTipoViaje) {
            case ESTANDAR -> {
                nuevo = new Viaje(propietario, ruta,horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
            }
            case CANCELABLE -> {
                nuevo = new ViajeCancelable(propietario, ruta, horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
            }
            case EXCLUSIVO -> {
                nuevo = new ViajeExclusivo(propietario, ruta, horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
            }
            case FLEXIBLE -> {
                nuevo = new ViajeFlexible(propietario, ruta, horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
            }
            default ->{
                throw new ViajeNoValidoException();
            }
        }
        this.gestor.add(nuevo);
        GestorIO.print(nuevo + " añadido con éxito");
    }

    public void cancelarViaje(int codigo) throws ViajeNoValidoException {
        List<Viaje> viajes = getViajesCancelables();
        Viaje v = getViaje(codigo);
        if (viajes.remove(v)) {
        gestor.cancel(v);
        GestorIO.print("El viaje se ha cancelado correctamente.");
        } else {
            throw new ViajeNoValidoException();
        }
    }

    public List<Viaje> buscarViaje(String sitio) {
        final int MIN_PLAZAS = 1;
        List<Viaje> viajes = gestor.findAll();
        List<Viaje> rutas = new ArrayList<>();
        for (Viaje viaje : viajes) {
            StringTokenizer tokenizer = new StringTokenizer(viaje.getRuta(), "-");
            if (isValido(viaje.getCodigo()) && !viaje.getPropietario().equals(user) && viaje.getOfertadas() >= MIN_PLAZAS) {
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken().toLowerCase();                
                    if (token.equals(sitio.toLowerCase())) {
                        rutas.add(viaje);
                    }
                }
            }
        }
        return rutas;
    }
    
    public void listarSitio(String sitio) {
        printViajes(buscarViaje(sitio));
    }

    public Viaje getViaje(int codigo) {
        List<Viaje> viajes = gestor.findAll();
        for (Viaje viaje : viajes) {
            if (viaje.getCodigo() == codigo) {
                return viaje;
            }
        }
        return null;
    }

    public boolean getModificable(int codigo) {
        Viaje v = getViaje(codigo);
        return isValido(codigo) && v instanceof ViajeFlexible;
    }

    public boolean getCancelable(int codigo) {
        Viaje v = getViaje(codigo);
        return isValido(codigo) && (v instanceof ViajeFlexible || v instanceof ViajeCancelable);
    }
    
    public boolean isValido(int codigo){
        Viaje v = getViaje(codigo);
        return v!=null && !v.getCerrado() && !v.getCancelado() && v.getPropietario() != null;        
    }
    
    public void testFecha(int codigo) throws FechaPasadaException{
        if (!getViaje(codigo).getHoraSalida().isAfter(LocalDateTime.now())) {
          throw new FechaPasadaException();
        }
    }
    
}

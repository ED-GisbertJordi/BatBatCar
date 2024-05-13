package controladores;

import java.util.AbstractList;
import java.util.ArrayList;

import entidades.Usuario;
import entidades.Viaje;
import gestores.ViajesManager;
import views.GestorIO;
import views.ListadoViajesView;

import java.util.List;
import java.util.StringTokenizer;

import entidades.tiposViajes.*;

public class ViajesController {

    private Usuario usuario;
    private ViajesManager gestor;

    public ViajesController(Usuario user) {
        this.gestor = new ViajesManager();

        /* Por defecto, no hay establecido ningún usuario. Se deberá establecer a posteriori
         Si no quieres realizar el caso de uso 1 hasta el final puedes establecer un usuario
         por defecto. Por ejemplo: this.usuario = new Usuario("roberto1979", "12345"); */
        this.usuario = user;
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
            if (usuario.equals(viaje.getPropietario()) && (viaje instanceof ViajeCancelable || viaje instanceof ViajeFlexible)) {
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
            if (!usuario.equals(viaje.getPropietario()) && !viaje.getCancelado() && !viaje.getCerrado()) {
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
    public void anyadirViaje(Usuario propietario) {
        final int ESTANDAR = 1;
        final int CANCELABLE = 2;
        final int EXCLUSIVO = 3;
        final int FLEXIBLE = 4;
        int numeroTipoViaje = GestorIO.getInt(
                ESTANDAR + "- Viaje Estánndar\n"
                + CANCELABLE + "- Viaje Cancelable\n"
                + EXCLUSIVO + "- Viaje Exclusivo\n"
                + FLEXIBLE + "- Viaje Flexible\n"
                + "Seleccione el tipo de viaje");
        if (numeroTipoViaje < ESTANDAR || numeroTipoViaje > FLEXIBLE) {

            String ruta = GestorIO.getString("Introduzca la ruta a realizar (Ej: Alcoy-Alicante)");
            int duracion = GestorIO.getInt("Introduzca la duracion del viaje en minutos");
            double precio = GestorIO.getInt("Introduzca el precio de cada plaza");
            int plazasTotales = GestorIO.getInt("Introduzca la nuemro de plazas");
            int plazasOfertadas = GestorIO.getInt("Introduzca la nuemro de plazas disponibles");

            Viaje nuevo = null;
            switch (numeroTipoViaje) {
                case ESTANDAR -> {
                    nuevo = new Viaje(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
                }
                case CANCELABLE -> {
                    nuevo = new ViajeCancelable(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
                }
                case EXCLUSIVO -> {
                    nuevo = new ViajeExclusivo(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
                }
                case FLEXIBLE -> {
                    nuevo = new ViajeFlexible(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
                }
            }
            this.gestor.add(nuevo);
            GestorIO.print(nuevo + " añadido con éxito");
         }else{
            GestorIO.print("Error: Tipo de viaje no valido.");
        }
        
    }

    public void cancelarViaje(int codigo) {
        List<Viaje> viajes = getViajesCancelables();
        Viaje v = new Viaje(codigo);
        if (isValido(codigo) && viajes.remove(v)) {
        gestor.cancel(v);
        GestorIO.print("El viaje se ha cancelado correctamente.");
        } else {
            GestorIO.print("El viaje no se ha podido cancelado, compruebe el código.");
        }
    }

    public List<Viaje> buscarViaje(String sitio) {
        List<Viaje> viajes = gestor.findAll();
        List<Viaje> rutas = new ArrayList<>();
        for (Viaje viaje : viajes) {
            StringTokenizer tokenizer = new StringTokenizer(viaje.getRuta(), "-");
            while (!viaje.getPropietario().equals(usuario) && tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().toLowerCase();                
                if (token.equals(sitio.toLowerCase())) {
                    rutas.add(viaje);
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
        return v != null && !v.getCerrado() && !v.getCancelado() && v instanceof ViajeFlexible;
    }

    public boolean getCancelable(int codigo) {
        Viaje v = getViaje(codigo);
        return v != null && !v.getCerrado() && !v.getCancelado() && (v instanceof ViajeFlexible || v instanceof ViajeCancelable);
    }
    
    public boolean isValido(int codigo){
        Viaje v = getViaje(codigo);
        return v!=null && !v.getCerrado() && !v.getCancelado() && v.getPropietario() != null;        
    }

}

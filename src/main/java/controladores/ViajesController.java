package controladores;

import java.util.AbstractList;
import java.util.ArrayList;

import entidades.Usuario;
import entidades.Viaje;
import gestores.ViajesManager;
import views.GestorIO;
import views.ListadoViajesView;

import java.util.List;

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
    
    private void printViajes(List<Viaje> lista){
        (new ListadoViajesView(lista)).visualizar();
    }
    
    /**
     * Lista todos los viajes del sistema.
     */
    public void listarViajes() {
        printViajes(gestor.findAll());
    }
    
    private List getViajesCancelables(){
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
    
    private List getViajesReservables(){
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
     * Añade un viaje al sistema, preguntando previamente por toda la información necesaria para crearlo.
     */
    public void anyadirViaje(TiposViajes tipoDeViaje, Usuario propietario, String ruta, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        Viaje nuevo = null;
        switch (tipoDeViaje) {
            case Estandar -> {
                nuevo = new Viaje(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
            }
            case Cancelable -> {
                nuevo = new ViajeCancelable(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
            }
            case Exclusivo -> {
                nuevo = new ViajeExclusivo(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
            }
            case Flexible -> {
                nuevo = new ViajeFlexible(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
            }
        }
        this.gestor.add(nuevo);
        GestorIO.print(nuevo+" añadido con éxito");
    }
    
    public void cancelarViaje(int codigo) {
        List<Viaje> viajes = getViajesCancelables();
        if (viajes.remove(new Viaje(codigo))) {
            gestor.cancel(new Viaje(codigo));
            GestorIO.print("El viaje se ha cancelado correctamente.");
        }else{
            GestorIO.print("El viaje no se ha podido cancelado, compruebe el código.");
        }
    }
    
    public void hacerReserva(int codigo){
        List viajes = getViajesReservables();
        
        
    }

}

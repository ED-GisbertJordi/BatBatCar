package entidades.tiposViajes;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeFlexible extends Viaje{

    public ViajeFlexible(Usuario propietario, String ruta, int duracion, int plazasTotales, double precio) {
        super(propietario, ruta, duracion, plazasTotales, precio);
    }
    
    public void cancelar(){
        super.setCancelado(true);
    }
    
    @Override
    public void cambiarPlazasReserva(Reserva reserva, int plazas) {
        super.cambiarPlazasReserva(reserva, plazas);
    }
    
    public void cambiarPrecio(double precio){
        super.setPrecio(precio);
    }
}

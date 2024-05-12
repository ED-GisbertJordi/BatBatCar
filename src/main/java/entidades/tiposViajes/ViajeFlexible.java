package entidades.tiposViajes;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeFlexible extends Viaje{

    public ViajeFlexible(Usuario propietario, String ruta, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        super(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
    }
    
    public void cancelar(){
        super.setCancelado(true);
    }
    
    @Override
    public Reserva cambiarPlazasReserva(Reserva reserva, int plazas) {
        return super.cambiarPlazasReserva(reserva, plazas);
    }
    
    public void cambiarPrecio(double precio){
        super.setPrecio(precio);
    }
    
    @Override
    public String getTipo(){
        return "Flexible";
    }
}

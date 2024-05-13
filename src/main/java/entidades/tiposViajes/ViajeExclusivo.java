package entidades.tiposViajes;

import java.time.LocalDateTime;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeExclusivo extends Viaje{

    public ViajeExclusivo(Usuario propietario, String ruta, LocalDateTime horaSalida, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        super(propietario, ruta, horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
    }
    
    
    
    @Override
    public Reserva hacerReserva(Usuario usuario, int plazas) {
        Reserva r = super.hacerReserva(usuario, plazas);
        super.setCerrado(true);
        return r;
    }
    
    @Override
    public String getTipo(){
        return "Exclusivo";
    }
    
}

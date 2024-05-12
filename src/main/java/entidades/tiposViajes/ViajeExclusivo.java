package entidades.tiposViajes;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeExclusivo extends Viaje{

    public ViajeExclusivo(Usuario propietario, String ruta, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        super(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
    }
    
    
    
    @Override
    public Reserva hacerReserva(Usuario usuario, int plazas) {
        super.setCerrado(true);
        return super.hacerReserva(usuario, plazas);
    }
    
    @Override
    public String getTipo(){
        return "Exclusivo";
    }
    
}

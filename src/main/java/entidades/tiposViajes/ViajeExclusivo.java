package entidades.tiposViajes;

import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeExclusivo extends Viaje{

    public ViajeExclusivo(Usuario propietario, String ruta, int duracion, int plazasTotales, double precio) {
        super(propietario, ruta, duracion, plazasTotales, precio);
    }
    
    
    
    @Override
    public void hacerReserva(Usuario usuario, int plazas) {
        super.hacerReserva(usuario, plazas);
        super.setCerrado(true);
    }
    
}

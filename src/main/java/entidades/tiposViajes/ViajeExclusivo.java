package entidades.tiposViajes;

import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeExclusivo extends Viaje{
    
    
    @Override
    public void hacerReserva(Usuario usuario, int plazas) {
        super.hacerReserva(usuario, plazas);
        super.setCerrado(true);
    }
    
}

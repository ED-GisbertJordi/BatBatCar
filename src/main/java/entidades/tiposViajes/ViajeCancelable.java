package entidades.tiposViajes;

import entidades.Usuario;
import entidades.Viaje;
/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeCancelable extends Viaje{
    
    public ViajeCancelable(Usuario propietario, String ruta, int duracion, int plazasTotales, double precio) {
        super(propietario, ruta, duracion, plazasTotales, precio);
    }
    
    @Override
    public void cancelarReserva(int codigo){
        super.cancelarReserva(codigo);
    }
    
}

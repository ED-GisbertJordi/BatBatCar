package entidades.tiposViajes;

import entidades.Usuario;
import entidades.Viaje;
/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeCancelable extends Viaje{
    
    public ViajeCancelable(Usuario propietario, String ruta, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        super(propietario, ruta, duracion, plazasTotales, plazasOfertadas, precio);
    }
    
    @Override
    public void cancelarReserva(int codigo){
        super.cancelarReserva(codigo);
    }
    
    @Override
    public String getTipo(){
        return "Cancelable";
    }
}

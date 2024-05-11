package entidades.tiposViajes;

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
    public void hacerReserva(Usuario usuario, int plazas) {
        super.hacerReserva(usuario, plazas);
        super.setCerrado(true);
    }
    
    @Override
    public String getTipo(){
        return "Exclusivo";
    }
    
}

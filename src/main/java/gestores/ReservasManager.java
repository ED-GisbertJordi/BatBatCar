package gestores;

import java.util.ArrayList;
import java.util.List;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;
import entidades.tiposViajes.ViajeExclusivo;
import entidades.tiposViajes.ViajeFlexible;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ReservasManager {

    private List<Reserva> reservas;

    public ReservasManager() {
        reservas = new ArrayList<>();
        init();
    }
    
    
    public boolean add(Reserva reserva){
        return reservas.add(reserva);
    }
    
    public boolean remove(Reserva reserva){
        return reservas.remove(reserva);
    }
    
    
    /**
     * Obtiene todas las reservas
     * @return
     */
    public List<Reserva> findAll() {
        return reservas;
    }
    
    public void init() {
        
    }

}
















package gestores;

import entidades.Viaje;

import java.util.ArrayList;
import java.util.List;

import entidades.Usuario;
import entidades.tiposViajes.ViajeCancelable;
import entidades.tiposViajes.ViajeExclusivo;
import entidades.tiposViajes.ViajeFlexible;

/**
 * Gestor de viajes. Manejará la lista de los viajes tanto para almancenar nueva 
 * información sobre ella como para recuperar la totalidad o parte de la información
 * como también información relacionada con dicha lista.
 * @author batoi
 */

public class ViajesManager {

    private List<Viaje> viajes;

    public ViajesManager() {
        this.viajes = new ArrayList<>();
        init();
    }

    /**
     * Añade un nuevo viaje al repositorio
     * @param viaje
     */
    public boolean add(Viaje viaje) {
        return viajes.add(viaje);
    }
    
    /**
     * Cancela un viaje
     * @param viaje
     */
    public boolean cancel(Viaje viaje){
        return viajes.remove(viaje);
    }

    /**
     * Obtiene el número de viajes actualmente registrados
     * @return
     */
    public int getNumViajes() {
        throw new UnsupportedOperationException("Por implementar");
    }
    
    /**
     * Obtiene todos los viajes
     * @return
     */
    public List<Viaje> findAll() {
        return viajes;
    }

    private void init() {
        // añade a la colección "viajes" todos los viajes que creas necesario tener de inicio en tu sistema
        // this.add(new Viaje(....));
        this.add(new Viaje(new Usuario("Jordi", "1234"), "Random", 30, 110, 110, 5));
        this.add(new ViajeCancelable(new Usuario("Jordi", "1234"), "Tex", 60, 30, 30, 10));
        this.add(new ViajeFlexible(new Usuario("Jordi", "1234"), "Batoi", 90, 80, 200, 15));
        this.add(new ViajeFlexible(new Usuario("Anon", "1234"), "Batoi", 90, 80, 200, 15));
        this.add(new ViajeExclusivo(new Usuario("Jordi", "1234"), "Raro", 90, 5, 1, 5));
        
        
    }
}

package entidades.tiposViajes;

import java.time.LocalDateTime;

import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeCancelable extends Viaje {

    public ViajeCancelable(Usuario propietario, String ruta, LocalDateTime horaSalida, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        super(propietario, ruta,horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
    }

    @Override
    public void cancelarReserva(int codigo) {
        super.cancelarReserva(codigo);
    }

    @Override
    public String getTipo() {
        return "Cancelable";
    }

    @Override
    public String toString() {
        return "Viaje de tipo " + getTipo() + " del propietario " + getPropietario() + " con código " + getCodigo() + " y ruta " + getRuta() + "";
    }
}

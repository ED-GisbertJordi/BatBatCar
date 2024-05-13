package entidades.tiposViajes;

import java.time.LocalDateTime;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ViajeFlexible extends Viaje {

    public ViajeFlexible(Usuario propietario, String ruta, LocalDateTime horaSalida, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        super(propietario, ruta, horaSalida, duracion, plazasTotales, plazasOfertadas, precio);
    }

    @Override
    public void cancelar() {
        super.cancelar();
    }

    @Override
    public void cancelarReserva(int codigo) {
        super.cancelarReserva(codigo);
    }

    @Override
    public Reserva cambiarPlazasReserva(Reserva reserva, int plazas) {
        return super.cambiarPlazasReserva(reserva, plazas);
    }

    public void cambiarPrecio(double precio) {
        super.setPrecio(precio);
    }

    @Override
    public String getTipo() {
        return "Flexible";
    }

    @Override
    public String toString() {
        return "Viaje de tipo " + getTipo() + " del propietario " + getPropietario() + " con código " + getCodigo() + " y ruta " + getRuta() + "";
    }
}

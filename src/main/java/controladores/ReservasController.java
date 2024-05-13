package controladores;

import java.util.ArrayList;
import java.util.List;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;
import entidades.tiposViajes.ViajeCancelable;
import entidades.tiposViajes.ViajeFlexible;
import excepciones.ReservaNoValidaException;
import excepciones.ViajeNoValidoException;
import gestores.ReservasManager;
import views.GestorIO;
import views.ListadoReservasView;
import views.TicketView;

/**
 * @author Jordi Gisbert Ferriz
 */
public class ReservasController {

    private ReservasManager gestor;
    private Usuario user;

    public ReservasController(Usuario usuario) {
        gestor = new ReservasManager();
        this.user = usuario;
    }

    public void anyadirReserva(Viaje viaje, Usuario usuario) throws ReservaNoValidaException, ViajeNoValidoException {
        final int NUM_PLAZA_MIN = 1;
        if (comprobarUsuario(viaje, usuario) && viaje.getOfertadas() >= NUM_PLAZA_MIN) {
            int plazas = GestorIO.getInt("Introduzaca el numero de plazas a reserva", NUM_PLAZA_MIN, viaje.getOfertadas());
            Reserva reserva = viaje.hacerReserva(usuario, plazas);
            if (reserva != null) {
                this.gestor.add(reserva);
                GestorIO.print("Reserva realizada con éxito. A continuación se mostrará el ticket de confirmación.");
                (new TicketView(usuario, viaje.getCodigo(), plazas)).visualizar();
            } else {
                throw new ReservaNoValidaException();
            }
        } else {
            throw new ViajeNoValidoException();
        }
    }

    private boolean comprobarUsuario(Viaje viaje, Usuario usuario) {
        return !viaje.getPropietario().equals(usuario);
    }

    private void printReservas(List<Reserva> lista) {
        (new ListadoReservasView(lista)).visualizar();
    }

    /**
     * Lista todas las reservas del sistema.
     */
    public void listarReservas() {
        printReservas(gestor.findAll());
    }

    public void listarReservasModificables() {
        printReservas(getReservasModificables());
    }

    public List<Reserva> getReservasModificables() {
        List<Reserva> r = gestor.findAll();
        List<Reserva> reservas = new ArrayList<>();
        for (Reserva reserva : r) {
            Viaje viaje = reserva.getViaje();
            if (viaje instanceof ViajeFlexible && !viaje.getCancelado() && !viaje.getCerrado() && reserva.getUsuario().equals(user)) {
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public void listarReservasCancelables() {
        printReservas(getReservasModificables());
    }

    public List<Reserva> getReservasCancelables() {
        List<Reserva> r = gestor.findAll();
        List<Reserva> reservas = new ArrayList<>();
        for (Reserva reserva : r) {
            Viaje viaje = reserva.getViaje();
            if ((viaje instanceof ViajeFlexible || viaje instanceof ViajeCancelable) && !viaje.getCancelado() && !viaje.getCerrado() && reserva.getUsuario().equals(user)) {
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public Reserva getReserva(int codigo) {
        List<Reserva> reservas = gestor.findAll();
        for (Reserva reserva : reservas) {
            if (reserva.getCodigo() == codigo) {
                return reserva;
            }
        }
        return null;
    }

    public boolean isValido(int codigo) {
        List<Reserva> reservas = gestor.findAll();
        for (Reserva reserva : reservas) {
            if (reserva.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    public void modificarReserva(Reserva reserva) throws ViajeNoValidoException, ReservaNoValidaException {
        final int NUM_PLAZA_MIN = 1;
        ViajeFlexible v = (ViajeFlexible) reserva.getViaje();
        if (comprobarUsuario(reserva.getViaje(), user) && v.getOfertadas() >= NUM_PLAZA_MIN) {
            int plazas = GestorIO.getInt("Introduzaca el numero de plazas a reserva", reserva.getPlazas(), v.getOfertadas());
            Reserva r = v.cambiarPlazasReserva(reserva, plazas);
            if (r != null) {
                GestorIO.print("Reserva realizada con éxito. A continuación se mostrará el ticket de confirmación.");
                (new TicketView(user, reserva.getViaje().getCodigo(), plazas)).visualizar();
            } else {
                throw new ReservaNoValidaException();
            }
        } else {
            throw new ViajeNoValidoException();
        }
    }

    public void cancelarReserva(Reserva reserva) throws ReservaNoValidaException {
        if (comprobarUsuario(reserva.getViaje(), user)) {
            ViajeFlexible v = (ViajeFlexible) reserva.getViaje();
            v.cancelarReserva(reserva.getCodigo());
            gestor.remove(reserva);
            GestorIO.print("Reserva del " + reserva.getViaje().toString() + " Cancelada con éxito.");
        } else {
            throw new ReservaNoValidaException();
        }

    }
}

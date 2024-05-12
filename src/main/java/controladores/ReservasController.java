package controladores;

import java.util.ArrayList;
import java.util.List;

import entidades.Reserva;
import entidades.Usuario;
import entidades.Viaje;
import entidades.tiposViajes.ViajeFlexible;
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

    public void anyadirReserva(Viaje viaje, Usuario usuario) {
        final int NUM_PLAZA_MIN = 1;
        if (comprovaciones(viaje, usuario)) {

            int plazas = GestorIO.getInt("Introduzaca el numero de plazas a reserva", NUM_PLAZA_MIN, viaje.getOfertadas());
            Reserva reserva = viaje.hacerReserva(usuario, plazas);
            if (reserva != null) {
                this.gestor.add(reserva);
                GestorIO.print("Reserva realizada con éxito. A continuación se mostrará el ticket de confirmación.");
                (new TicketView(usuario, viaje.getCodigo(), plazas)).visualizar();
            } else {
                GestorIO.print("Error: No se puede hacer la reserva, porque ya tiene una en este Viaje.");
            }
        }

    }

    private boolean comprovaciones(Viaje viaje, Usuario usuario) {
        final int NUM_PLAZA_MIN = 1;
        if (viaje != null) {
            if (!viaje.getCerrado() && !viaje.getCancelado()) {
                if (!viaje.getPropietario().equals(usuario)) {
                    if (viaje.getOfertadas() >= NUM_PLAZA_MIN) {
                        return true;
                    } else {
                        GestorIO.print("Error: No hay plazas disponiebles en ese Viaje.");
                    }
                } else {
                    GestorIO.print("Error: No puedes hacer una reserva en un Viajes si eres tu el Propietario.");
                }
            } else {
                GestorIO.print("Error: El Viaje esta Cerrado/Cancelado.");
            }
        } else {
            GestorIO.print("El código no corresponde con un Viaje valido.");
        }
        return false;
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

    public Reserva getReserva(int codigo) {
        List<Reserva> reservas = gestor.findAll();
        for (Reserva reserva : reservas) {
            if (reserva.getCodigo() == codigo) {
                return reserva;
            }
        }
        return null;
    }

    public void modReserva(Reserva reserva) {
        if (comprovaciones(reserva.getViaje(), user)) {
            ViajeFlexible v = (ViajeFlexible) reserva.getViaje();
            int plazas = GestorIO.getInt("Introduzaca el numero de plazas a reserva", reserva.getPlazas(), v.getOfertadas());
            Reserva r = v.cambiarPlazasReserva(reserva, plazas);
            if (r != null) {
                GestorIO.print("Reserva realizada con éxito. A continuación se mostrará el ticket de confirmación.");
                (new TicketView(user, reserva.getViaje().getCodigo(), plazas)).visualizar();
            }else{
                GestorIO.print("No se ha podido realizar la modificación.");
            }

        }

    }

}

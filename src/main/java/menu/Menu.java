package menu;

import controladores.*;
import entidades.Usuario;
import excepciones.UsuarioSinEstablecerException;
import views.GestorIO;

/**
 * Clase que gestiona el menú de opciones. A partir de esta clase se ejecutan
 * las diferentes opciones del menú (casos de uso).
 *
 * @author batoi
 */
public class Menu {

    private static final int OPCION_LOG = 1;
    private static final int OPCION_LISTA_VIAJES = 2;
    private static final int OPCION_ADD_VIAJE = 3;
    private static final int OPCION_CANCELAR_VIAJE = 4;
    private static final int OPCION_ADD_RESERVA = 5;
    private static final int OPCION_MOD_RESERVA = 6;
    private static final int OPCION_CANCELAR_RESERVA = 7;
    private static final int OPCION_BUSCAR_VIAJE_Y_ADD_RESERVA = 8;
    private static final int OPCION_SALIR = 9;

    private ViajesController viajesController;
    private UsuariosController usuariosController;
    private ReservasController reservasController;
    private int opcionSeleccionada;
    private Usuario user;
    private boolean iniciado;

    public Menu() {
        this.viajesController = new ViajesController(null);
        this.reservasController = new ReservasController(null);
        this.usuariosController = new UsuariosController();
        user = null;
        iniciado = false;
    }

    public void iniciar() {

        GestorIO.print("BatBatCar\n=========\n");

        // Ampliar método para que se soliciten las opciones hasta que se indique la opción salir        
        do {
            mostrarOpciones();
            opcionSeleccionada = solicitarOpcion();
            try {
                ejecutarOpcion(opcionSeleccionada);
            } catch (UsuarioSinEstablecerException e) {
                GestorIO.print(e.getMessage() + "\n");
            }
        } while (opcionSeleccionada != OPCION_SALIR);
        GestorIO.print("Adiós");

    }

    private void mostrarOpciones() {
        GestorIO.print(+OPCION_LOG + ") Establece usuario (login)\n"
                + OPCION_LISTA_VIAJES + ") Lista todos los viajes\n"
                + OPCION_ADD_VIAJE + ") Añadir viaje\n"
                + OPCION_CANCELAR_VIAJE + ") Cancelar viaje\n"
                + OPCION_ADD_RESERVA + ") Realizar reserva\n"
                + OPCION_MOD_RESERVA + ") Modificar reserva\n"
                + OPCION_CANCELAR_RESERVA + ") Cancelar reserva\n"
                + OPCION_BUSCAR_VIAJE_Y_ADD_RESERVA + ") Buscar viaje y realizar reserva\n"
                + OPCION_SALIR + ") Salir\n"
                + "Seleccione una opción [" + OPCION_LOG + "-" + OPCION_SALIR + "]\n");
    }

    private int solicitarOpcion() {
        // Implementar método para solicitar la opción al usuario
        return GestorIO.getInt("Introduce la opción", OPCION_LOG, OPCION_SALIR);
    }

    private void init(Usuario user) {
        this.viajesController = new ViajesController(user);
        this.reservasController = new ReservasController(user);
    }

    private void ejecutarOpcion(int opcionSeleccionada) throws UsuarioSinEstablecerException {
        if (!iniciado && opcionSeleccionada != OPCION_LOG && opcionSeleccionada != OPCION_LISTA_VIAJES) {
            throw new UsuarioSinEstablecerException();
        }

        switch (opcionSeleccionada) {
            case OPCION_LOG -> {
                user = usuariosController.log();
                init(user);
                iniciado = true;
            }
            case OPCION_LISTA_VIAJES -> viajesController.listarViajes();
            case OPCION_ADD_VIAJE -> viajesController.anyadirViaje(user);
            case OPCION_CANCELAR_VIAJE -> {
                viajesController.listarViajesCancelabes();
                int codigo = GestorIO.getInt("Introduce el código del viaje a seleccionar");
                viajesController.cancelarViaje(codigo);
            }
            case OPCION_ADD_RESERVA -> {
                viajesController.listarViajesReservables();
                int codigo = GestorIO.getInt("Introduce el código del viaje a seleccionar");
                reservasController.anyadirReserva(viajesController.getViaje(codigo), user);
            }
            case OPCION_MOD_RESERVA -> {
                reservasController.listarReservasModificables();
                if (!reservasController.getReservasModificables().isEmpty()) {
                    int codigo = GestorIO.getInt("Introduce el código de la reserva a modificar");
                    if (reservasController.getReserva(codigo) != null) {
                        if (viajesController.getModificable(reservasController.getReserva(codigo).getViaje().getCodigo())) {
                            reservasController.modificarReserva(reservasController.getReserva(codigo));
                        } else {
                            GestorIO.print("Error: El viaje no permite Cambios en las reservas.");
                        }
                    } else {
                        GestorIO.print("El código no corresponde con un Viaje valido.");
                    }
                }
            }
            case OPCION_CANCELAR_RESERVA -> {
                reservasController.listarReservasCancelables();
                if (!reservasController.getReservasCancelables().isEmpty()) {
                    int codigo = GestorIO.getInt("Introduce el código de la reserva a modificar");
                    if (reservasController.getReserva(codigo) != null) {
                        if (viajesController.getCancelable(reservasController.getReserva(codigo).getViaje().getCodigo())) {
                            reservasController.cancelarReserva(reservasController.getReserva(codigo));
                        } else {
                            GestorIO.print("Error: El viaje no permite Cambios en las reservas.");
                        }
                    } else {
                        GestorIO.print("El código no corresponde con un Viaje valido.");
                    }
                }
            }
            case OPCION_BUSCAR_VIAJE_Y_ADD_RESERVA -> {
                String sitio = GestorIO.getString("Introduzca la ciudad a la que desea viajar");
                viajesController.listarSitio(sitio);
                if (!viajesController.buscarViaje(sitio).isEmpty()) {
                    if (GestorIO.confirmar("¿Quiere realizar una reserva?")) {
                            viajesController.buscarViaje(sitio);
                            int codigo = GestorIO.getInt("Introduce el código del viaje a seleccionar");
                            reservasController.anyadirReserva(viajesController.getViaje(codigo), user);
                    }
                }else{
                    GestorIO.print("Lo sentimos no hay viajes a ese sitio.");
                }
            }
        }
    }

}

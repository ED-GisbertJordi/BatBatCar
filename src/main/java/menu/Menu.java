package menu;

import controladores.*;
import entidades.Usuario;
import excepciones.*;
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

    public Menu() {
        this.viajesController = new ViajesController(null);
        this.reservasController = new ReservasController(null);
        this.usuariosController = new UsuariosController();
        user = null;
    }

    public void iniciar() {

        GestorIO.print("BatBatCar\n=========\n");

        // Ampliar método para que se soliciten las opciones hasta que se indique la opción salir        
        do {
            mostrarOpciones();
            opcionSeleccionada = solicitarOpcion();
            try {
                ejecutarOpcion(opcionSeleccionada);
            } catch (UsuarioSinEstablecerException | ViajeNoValidoException | ReservaNoValidaException | ReservaNoCancelableException e) {
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
        this.viajesController.setUsuario(user);
        this.reservasController.setUsuario(user);
    }

    private void ejecutarOpcion(int opcionSeleccionada) throws UsuarioSinEstablecerException, ViajeNoValidoException, ReservaNoValidaException, ReservaNoCancelableException {
        if (user==null && opcionSeleccionada != OPCION_LOG && opcionSeleccionada != OPCION_LISTA_VIAJES) {
            throw new UsuarioSinEstablecerException();
        }

        switch (opcionSeleccionada) {
            case OPCION_LOG -> {
                user = usuariosController.log();
                init(user);
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
                if (viajesController.isValido(codigo)) {
                    reservasController.anyadirReserva(viajesController.getViaje(codigo), user);
                }else{
                    throw new ViajeNoValidoException();
                }
            }
            case OPCION_MOD_RESERVA -> {
                reservasController.listarReservasModificables();
                if (!reservasController.getReservasModificables().isEmpty()) {
                    int codigo = GestorIO.getInt("Introduce el código de la reserva a modificar");
                    if (reservasController.isValido(codigo) && viajesController.getModificable(reservasController.getReserva(codigo).getViaje().getCodigo())) {
                        reservasController.modificarReserva(reservasController.getReserva(codigo));
                    } else {
                        throw new ViajeNoValidoException();
                    }
                }else{
                    GestorIO.print("No hay Reservas en Viajes Modificables.");
                }
            }
            case OPCION_CANCELAR_RESERVA -> {
                reservasController.listarReservasCancelables();
                if (!reservasController.getReservasCancelables().isEmpty()) {
                    int codigo = GestorIO.getInt("Introduce el código de la reserva a modificar");
                    if (reservasController.isValido(codigo)) {
                        if (viajesController.getCancelable(reservasController.getReserva(codigo).getViaje().getCodigo())) {
                            reservasController.cancelarReserva(reservasController.getReserva(codigo));
                        } else {
                            throw new ReservaNoCancelableException();
                        }
                    } else {
                        throw new ReservaNoValidaException();
                    }
                }else{
                    GestorIO.print("No hay Reservas en Viajes Cancelables.");
                }
            }
            case OPCION_BUSCAR_VIAJE_Y_ADD_RESERVA -> {
                String sitio = GestorIO.getString("Introduzca la ciudad a la que desea viajar");
                viajesController.listarSitio(sitio);
                if (!viajesController.buscarViaje(sitio).isEmpty()) {
                    if (GestorIO.confirmar("¿Quiere realizar una reserva?")) {
                        viajesController.buscarViaje(sitio);
                        int codigo = GestorIO.getInt("Introduce el código del viaje a seleccionar");
                        if (viajesController.isValido(codigo)) {
                            reservasController.anyadirReserva(viajesController.getViaje(codigo), user);
                        } else {
                            throw new ViajeNoValidoException();
                        }
                    }
                }else{
                    GestorIO.print("No se ha encontrado ningun Viaje a ese lugar.");
                }
            }
        }
    }

}

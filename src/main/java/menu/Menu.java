package menu;

import controladores.*;
import entidades.Usuario;
import views.GestorIO;

/**
 * Clase que gestiona el menú de opciones. A partir de esta clase se ejecutan
 * las diferentes opciones del menú (casos de uso).
 * @author batoi
 */

public class Menu {

    private static final int OPCION_LOG = 1;
    private static final int OPCION_LISTA_LOG = 2;
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
    private boolean iniciado;
    
    public Menu() {
        this.viajesController = new ViajesController();
        this.usuariosController = new UsuariosController();
        this.reservasController = new ReservasController();
    }

    public void iniciar(){

        GestorIO.print("BatBatCar\n=========\n");
        
        // Ampliar método para que se soliciten las opciones hasta que se indique la opción salir        
        do {
            mostrarOpciones();
            opcionSeleccionada = solicitarOpcion();
            ejecutarOpcion(opcionSeleccionada);
        } while (opcionSeleccionada!=9);
        GestorIO.print("Adiós");
        
        
    }

    private void mostrarOpciones() {
        GestorIO.print(
                +OPCION_LOG+") Establece usuario (login)\n"
                +OPCION_LISTA_LOG+") Lista todos los viajes\n"
                +OPCION_ADD_VIAJE+") Añadir viaje\n"
                +OPCION_CANCELAR_VIAJE+") Cancelar viaje\n"
                +OPCION_ADD_RESERVA+") Realizar reserva\n"
                +OPCION_MOD_RESERVA+") Modificar reserva\n"
                +OPCION_CANCELAR_RESERVA+") Cancelar reserva\n"
                +OPCION_BUSCAR_VIAJE_Y_ADD_RESERVA+") Buscar viaje y realizar reserva\n"
                +OPCION_SALIR+") Salir\n"
                +"Seleccione una opción ["+OPCION_LOG+"-"+OPCION_SALIR+"]\n");
    }
    
    private int solicitarOpcion() {
        // Implementar método para solicitar la opción al usuario
        return GestorIO.getInt("Introduce la opción", OPCION_LOG, OPCION_SALIR);
    }
    
    private void ejecutarOpcion(int opcionSeleccionada) {
        // Implementar método para ejecutar la opción recibida
        switch (opcionSeleccionada) {
            case 1 -> {
                iniciado = usuariosController.log();
                if (!iniciado){
                    opcionSeleccionada = OPCION_SALIR;
                }
            }
            case 2 -> {
                if(iniciado){
                    viajesController.listarViajes();
                }else{
                    inicie();
                }
            }
            case 3 -> viajesController.listarViajes();
            case 4 -> viajesController.listarViajes();
            case 5 -> viajesController.listarViajes();
            case 6 -> viajesController.listarViajes();
            case 7 -> viajesController.listarViajes();
            case 8 -> viajesController.listarViajes();
        }
    }
    
    private void inicie(){
        GestorIO.print("Inicie sesion antes de realizar la acción.\n");
    }

}

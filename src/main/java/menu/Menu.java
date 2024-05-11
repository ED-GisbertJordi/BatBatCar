package menu;

import controladores.*;
import entidades.Usuario;
import entidades.Viaje;
import entidades.tiposViajes.TiposViajes;
import views.GestorIO;

/**
 * Clase que gestiona el menú de opciones. A partir de esta clase se ejecutan
 * las diferentes opciones del menú (casos de uso).
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
        this.usuariosController = new UsuariosController();
        this.reservasController = new ReservasController();
        user = null;
        iniciado = false;
    }

    public void iniciar(){

        GestorIO.print("BatBatCar\n=========\n");
        
        // Ampliar método para que se soliciten las opciones hasta que se indique la opción salir        
        do {
            mostrarOpciones();
            opcionSeleccionada = solicitarOpcion();
            ejecutarOpcion(opcionSeleccionada);
        } while (opcionSeleccionada!=OPCION_SALIR);
        GestorIO.print("Adiós");
        
        
    }

    private void mostrarOpciones() {
        GestorIO.print(+OPCION_LOG+") Establece usuario (login)\n"
                +OPCION_LISTA_VIAJES+") Lista todos los viajes\n"
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
            case OPCION_LOG -> {
                user = usuariosController.log();
                this.viajesController = new ViajesController(user);
                iniciado = true;
                if (!iniciado){
                    opcionSeleccionada = OPCION_SALIR;
                }
            }
            case OPCION_LISTA_VIAJES -> viajesController.listarViajes();
            case OPCION_ADD_VIAJE -> {
                if(iniciado){
                    final int ESTANDAR = 1;
                    final int CANCELABLE = 2;
                    final int EXCLUSIVO = 3;
                    final int FLEXIBLE = 4;
                    int numeroTipoViaje = GestorIO.getInt(
                            ESTANDAR+"- Viaje Estánndar\n"+
                            CANCELABLE+"- Viaje Cancelable\n"+
                            EXCLUSIVO+"- Viaje Exclusivo\n"+
                            FLEXIBLE+"- Viaje Flexible\n"+                                        
                            "Seleccione el tipo de viaje");
                    TiposViajes tipo= (numeroTipoViaje==1)? TiposViajes.Estandar : (numeroTipoViaje==2)? TiposViajes.Cancelable : (numeroTipoViaje==3)? TiposViajes.Exclusivo : TiposViajes.Flexible;
                    
                    String ruta = GestorIO.getString("Introduzca la ruta a realizar (Ej: Alcoy-Alicante)");
                    int duracion = GestorIO.getInt("Introduzca la duracion del viaje en minutos");
                    double precio = GestorIO.getInt("Introduzca el precio de cada plaza");
                    int plazas = GestorIO.getInt("Introduzca la nuemro de plazas");
                    int plazasDispo = GestorIO.getInt("Introduzca la nuemro de plazas disponibles");
                    
                    viajesController.anyadirViaje(tipo, user, ruta, duracion, plazas, plazasDispo, precio);
                }else{
                    inicie();
                }
            }
            case OPCION_CANCELAR_VIAJE -> {
                if(iniciado){
                    viajesController.listarViajesCancelabes();
                    int codigo = GestorIO.getInt("Introduce el código del viaje a seleccionar");
                    viajesController.cancelarViaje(codigo);
                }else{
                    inicie();
                }
            }
            case OPCION_ADD_RESERVA -> {
                if(iniciado){
                    viajesController.listarViajesReservables();
                    viajesController.listarViajesReservables();
                    
                }else{
                    inicie();
                }
            }
            case OPCION_MOD_RESERVA -> {
                if(iniciado){
                    viajesController.listarViajes();
                }else{
                    inicie();
                }
            }
            case OPCION_CANCELAR_RESERVA -> {
                if(iniciado){
                    viajesController.listarViajes();
                }else{
                    inicie();
                }
            }
            case OPCION_BUSCAR_VIAJE_Y_ADD_RESERVA -> {
                if(iniciado){
                    viajesController.listarViajes();
                }else{
                    inicie();
                }
            }
        }
    }
    
    private void inicie(){
        GestorIO.print("Inicie sesion antes de realizar la acción.\n");
    }

}

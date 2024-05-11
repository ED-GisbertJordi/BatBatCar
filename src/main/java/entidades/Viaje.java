package entidades;

/*
 * Clase que representa a un viaje estándar
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import entidades.Usuario;

public class Viaje {
    private HashSet<Integer> codigos = new HashSet<>();
    private int codigo;
    private Usuario propietario;
    private String ruta;
    private int duracion;
    private int plazasTotales;
    private int plazasOfertadas = 0;
    private double precio;
    private boolean cerrado;
    private boolean cancelado;
    private ArrayList<Reserva> reservas;

    public Viaje() {
        this.codigo = 0;
    }

    public Viaje(Usuario propietario, String ruta, int duracion, int plazasTotales, double precio) {
        ponerCodigo();
        this.propietario = propietario;
        this.ruta = ruta;
        this.duracion = duracion;
        this.plazasTotales = plazasTotales;
        this.precio = precio;
        this.cerrado = false;
        this.cancelado = false;
    }

    /* dar de alta viaje */
    public void altaViaje(Usuario propietario, String ruta, int duracion, int plazasTotales, double precio) {
        this.propietario = propietario;
        this.ruta = ruta;
        this.duracion = duracion;
        this.plazasTotales = plazasTotales;
        this.precio = precio;
    }

    protected void setPrecio(double precio) {
        this.precio = precio;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    protected void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }

    public boolean getCerrado() {
        return cerrado;
    }

    protected void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public boolean getCancelado() {
        return cancelado;
    }

    public int getPlazasDisonibles() {
        return plazasTotales - plazasOfertadas;
    }

    public void hacerReserva(Usuario usuario, int plazas) {
        reservas.add(new Reserva(usuario, this, plazas));
    }

    protected void cambiarPlazasReserva(Reserva reserva, int plazas) {
        if (!cerrado && this.getPlazasDisonibles() >= plazas) {
            this.hacerReserva(reserva.getUsuario(), plazas);
            reservas.remove(reserva);
        }
    }

    protected void cancelarReserva(int codigo) {
        reservas.remove(new Reserva(codigo));
    }
    
    private void ponerCodigo(){
        Random random = new Random();
        boolean insertado;
        int cod;
        do{
            cod = random.nextInt(0, Integer.MAX_VALUE);
            insertado = codigos.add(cod);            
        }while (!insertado);
        codigo = cod;
    }

    @Override
    public int hashCode() {
        return propietario.hashCode() + duracion + plazasTotales + ruta.hashCode();
    }

}

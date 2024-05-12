package entidades;

/*
 * Clase que representa a un viaje estándar
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import entidades.Usuario;

public class Viaje {

    private static HashSet<Integer> codigos = new HashSet<>();
    private int codigo;
    private Usuario propietario;
    private String ruta;
    private int duracion;
    private int plazasTotales;
    private int plazasOfertadas;
    private double precio;
    private boolean cerrado;
    private boolean cancelado;
    private HashSet<Reserva> reservas;

    public Viaje() {
        this.codigo = 0;
    }

    public Viaje(Usuario propietario, String ruta, int duracion, int plazasTotales, int plazasOfertadas, double precio) {
        ponerCodigo();
        this.propietario = propietario;
        this.ruta = ruta;
        this.duracion = duracion;
        this.plazasTotales = plazasTotales;
        this.plazasOfertadas = (plazasOfertadas <= plazasTotales) ? plazasOfertadas : plazasTotales;
        this.precio = precio;
        this.cerrado = false;
        this.cancelado = false;
        reservas = new HashSet<>();
    }

    public Viaje(int codigo) {
        this.codigo = codigo;
    }

    /* dar de alta viaje */
    public void altaViaje(Usuario propietario, String ruta, int duracion, int plazasTotales, double precio) {
        this.propietario = propietario;
        this.ruta = ruta;
        this.duracion = duracion;
        this.plazasTotales = plazasTotales;
        this.precio = precio;
    }

    public int getCodigo() {
        return codigo;
    }

    protected void setPrecio(double precio) {
        this.precio = precio;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public String getRuta() {
        return ruta;
    }

    public double getPrecio() {
        return precio;
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

    public int getOfertadas() {
        return plazasOfertadas;
    }

    public String getTipo() {
        return "Estandar";
    }

    public Reserva hacerReserva(Usuario usuario, int plazas) {
        Reserva r = new Reserva(usuario, this, plazas);
        if (reservas.add(r)) {
            plazasOfertadas -= plazas;
            return r;
        }
        return null;
    }

    protected Reserva cambiarPlazasReserva(Reserva reserva, int plazas) {
        if (!cerrado && this.getOfertadas() >= plazas) {
            Usuario user = reserva.getUsuario();
            cancelarReserva(reserva.getCodigo());
            return this.hacerReserva(user, plazas);
        }
        return null;
    }

    protected void cancelarReserva(int codigo) {
        for (Reserva r : reservas) {
            if (r.isIgual(codigo)) {
                plazasOfertadas += r.getPlazas();
                reservas.remove(r);
                
                break;
            }
        }
    }

    private void ponerCodigo() {
        Random random = new Random();
        boolean insertado;
        int cod;
        do {
            cod = random.nextInt(0, Integer.MAX_VALUE);
            insertado = codigos.add(cod);
        } while (!insertado);
        codigo = cod;
    }

    @Override
    public int hashCode() {
        return propietario.hashCode() + duracion + plazasTotales + ruta.hashCode();
    }

    @Override
    public boolean equals(Object viaje) {
        Viaje v = (Viaje) viaje;
        return this.propietario.equals(v.propietario) && this.ruta.equals(v.ruta) && this.plazasTotales == v.plazasTotales && this.precio == v.precio;
    }

    @Override
    public String toString() {
        return "Viaje de tipo " + getTipo() + " del propietario " + propietario + " con código " + codigo + " y ruta " + ruta + " con " + plazasOfertadas + " plazas";
    }
}

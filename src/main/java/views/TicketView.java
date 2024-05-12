package views;

/**
 * Vista dedicada a los listados de viajes. De cada viaje se muestra su código,
 * ruta, precio, propietario, tipo de viaje, plazas disponibles y si está
 * cancelado.
 */
import java.util.Iterator;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import entidades.Viaje;

import java.util.List;

import entidades.Usuario;

public class TicketView {

    private final Usuario user;
    private final int codigo;
    private final int plazas;
    
    private static final int ANCHO_TABLA = 100;

    public TicketView(Usuario user, int codigo, int plazas) {
        this.user = user;
        this.codigo = codigo;
        this.plazas = plazas;
    }

    private AsciiTable buildASCIITable() {

        AsciiTable view = new AsciiTable();
        view.addRule();
        view.addRow("*", "*");
        view.addRule();
        view.addRow(null, "Reserva con código "+String.valueOf(codigo));
        view.addRule();
        view.addRow("Usuario", user.toString());
        view.addRule();
        view.addRow("Plazas", String.valueOf(plazas));
        view.addRule();
        view.setTextAlignment(TextAlignment.CENTER);
        return view;
    }

    @Override
    public String toString() {
        return buildASCIITable().render(ANCHO_TABLA);
    }

    public void visualizar() {
        System.out.println(buildASCIITable().render(ANCHO_TABLA));
    }
}

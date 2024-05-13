package views;

/**
 * Vista dedicada a los listados de viajes. De cada viaje se muestra su código,
 * ruta, precio, propietario, tipo de viaje, plazas disponibles y si está
 * cancelado.
 */
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.List;

import entidades.Reserva;

public class ListadoReservasView {

    private final List<Reserva> reservas;

    private static final int ANCHO_TABLA = 100;

    public ListadoReservasView(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    private AsciiTable buildASCIITable() {

        AsciiTable view = new AsciiTable();
        view.addRule();
        view.addRow("*", "*", "*", "*","*", "*");
        view.addRule();
        view.addRow(null,null,  null, null, null , "Listado Reservas");
        view.addRule();
        view.addRow("Cod. Reserva", "Cod. Viaje", "Propietario", null, "Plazas Reservadas", "Fecha");
        view.addRule();
        generarFilasViajes(view);
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

    private void generarFilasViajes(AsciiTable tabla) {

        // Implementa este método usando un bucle que itere sobre la lista de viajes y mostrando uno por fila.
        Iterator i = reservas.iterator();
        while (i.hasNext()) {
            Reserva next =  (Reserva) i.next();
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm");
            tabla.addRow(String.valueOf(next.getCodigo()), String.valueOf(next.getViaje().getCodigo()), next.getViaje().getPropietario().toString(),null, String.valueOf(next.getPlazas()), next.getHora().format(formateador));

        }
        /*
        tabla.addRow(1, null, "Barcelona-Alicante", 45, "roberto1979", "Estándar", 3, "No");
        tabla.addRule();
        tabla.addRow(2, null, "Alcoy-Elche", 10, "sergio123", "Estándar", 5, "Sí");
        */
        tabla.addRule();
        GestorIO.print("\n");
    }

}

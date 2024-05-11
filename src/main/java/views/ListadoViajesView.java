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

public class ListadoViajesView {

    private final List<Viaje> viajes;

    private static final int ANCHO_TABLA = 100;

    public ListadoViajesView(List<Viaje> viajes) {
        this.viajes = viajes;
    }

    private AsciiTable buildASCIITable() {

        AsciiTable view = new AsciiTable();
        view.addRule();
        view.addRow("*", "*", "*", "*", "*", "*", "*", "*");
        view.addRule();
        view.addRow(null, null, null, null, null, null, null, "Listado Viajes");
        view.addRule();
        view.addRow("Cod. Viaje", null, "Ruta", "Precio", "Propietario", "Tipo", "Plazas Disponibles", "Cancelado");
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
        //                      codigo       sepadador          ruta                           precio                  propietario                 tipo                plazas dispo    cancelado
        Iterator i = viajes.iterator();
        while (i.hasNext()) {
            Viaje next =  (Viaje) i.next();
            
            tabla.addRow(next.getCodigo(), null, next.getRuta(), next.getPrecio(), next.getPropietario(), next.getTipo(), next.getPlazasDisonibles(), (next.getCancelado()? "Si" : "No"));

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

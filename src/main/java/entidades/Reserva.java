package entidades;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Random;


public class Reserva {
    private int codigo;
    private static HashSet<Integer> codigos = new HashSet<>();
    private Usuario usuario;
    private LocalDateTime hora;
    private int plazas;
    private Viaje viaje;

    public Reserva(Usuario usuario, Viaje viaje, int plazas) {
        if (!usuario.equals(viaje.getPropietario()) && !viaje.getCancelado() && !viaje.getCerrado() && viaje.getOfertadas() >= plazas) {
            ponerCodigo();
            this.usuario = usuario;
            this.plazas = plazas;
            this.viaje = viaje;
            this.hora = LocalDateTime.now();
            System.out.println(this.hora);

         }
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

    public int getCodigo() {
        return codigo;
    }
    
    public Usuario getUsuario(){
        return usuario;
    }

    public LocalDateTime getHora() {
        return hora;
    }
    
    public Viaje getViaje() {
        return viaje;
    }
    
    public int getPlazas(){
        return plazas;
    }
    
    public boolean isIgual(int codigo){
        return this.codigo == codigo;
    }
    
    @Override
    public boolean equals(Object reserva) {
        Reserva r = (Reserva) reserva;
        return this.usuario.equals(r.usuario) && this.viaje.equals(r.viaje);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(usuario, viaje);
    }

}

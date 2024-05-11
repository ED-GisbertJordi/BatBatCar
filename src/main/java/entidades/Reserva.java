package entidades;

import java.util.HashSet;
import java.util.Random;


public class Reserva {
    private int codigo;
    private static HashSet<Integer> codigos = new HashSet<>();
    private Usuario usuario;
    private int plazas;
    private Viaje viaje;

    public Reserva(Usuario usuario, Viaje viaje, int plazas) {
        if (usuario.equals(viaje.getPropietario()) && !viaje.getCancelado() && !viaje.getCerrado() && viaje.getPlazasDisonibles() >= plazas) {
            ponerCodigo();
            this.usuario = usuario;
            this.plazas = plazas;
            this.viaje = viaje;
         }
    }
    
    protected Reserva(int codigo){
        this.codigo = codigo;
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
    
    public Usuario getUsuario(){
        return usuario;
    }
    
    public int getPlazas(){
        return plazas;
    }
    
    @Override
    public boolean equals(Object reserva) {
        Reserva r = (Reserva) reserva;
        return r.codigo == this.codigo;
    }
    
    @Override
    public int hashCode() {
        return usuario.hashCode()+viaje.hashCode();
    }

}

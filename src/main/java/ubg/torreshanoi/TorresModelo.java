/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubg.torreshanoi;

import ubg.torreshanoi.TorresVista.Disco;
import ubg.torreshanoi.TorresVista.Torre;

/**
 *
 * @author ulises
 */
public class TorresModelo {

    private TorresControlador controlador;
    private int aros = 3;
    private Torre torre1 = new Torre();
    private Torre torre2 = new Torre();
    private Torre torre3 = new Torre();
    private Disco discoAMover = null;    

    public TorresModelo() {
    }

    public TorresControlador getControlador() {
        return controlador;
    }

    public void setControlador(TorresControlador controlador) {
        this.controlador = controlador;
    }

    public Disco getDiscoAMover() {
        return discoAMover;
    }

    public void setDiscoAMover(Disco discoAMover) {
        this.discoAMover = discoAMover;
    }

    public int getAros() {
        return aros;
    }

    public void setAros(int aros) {
        this.aros = aros;
    }

    public Torre getTorre1() {
        return torre1;
    }

    public void setTorre1(Torre torre1) {
        this.torre1 = torre1;
    }

    public Torre getTorre2() {
        return torre2;
    }

    public void setTorre2(Torre torre2) {
        this.torre2 = torre2;
    }

    public Torre getTorre3() {
        return torre3;
    }

    public void setTorre3(Torre torre3) {
        this.torre3 = torre3;
    }

    public int discosTorre1() {
        return torre1.getComponentCount();
    }

    public int discosTorre2() {
        return torre2.getComponentCount();
    }

    public int discosTorre3() {
        return torre3.getComponentCount();
    }

    public Disco ultimoDiscoTorre1() {
        return (Disco) torre1.getComponent(discosTorre1() - 1);
    }

    public Disco ultimoDiscoTorre2() {
        return (Disco) torre2.getComponent(discosTorre2() - 1);
    }

    public Disco ultimoDiscoTorre3() {
        return (Disco) torre3.getComponent(discosTorre3() - 1);
    }

    public void tomarDiscoTorre1() {
        this.discoAMover = this.ultimoDiscoTorre1();        
        this.controlador.quitarUltimoDisco(torre1);
    }

    public void tomarDiscoTorre2() {
        this.discoAMover = this.ultimoDiscoTorre2();        
        this.controlador.quitarUltimoDisco(torre2);
    }

    public void tomarDiscoTorre3() {
        this.discoAMover = this.ultimoDiscoTorre3();
        this.controlador.quitarUltimoDisco(torre3);        
    }

    /**
     * controla el estado del jugador
     *
     * @return true si puede tomar, false si puede dejar
     */
    public boolean tomarODejarDisco() {
        return discoAMover == null;
    }

    /**
     * valida que el movimiento que quiere hacer el jugador sea válido
     *
     * @param torre torre a colocar disco
     * @param discoAMover disco que queremos colocar
     * @return
     */
    public boolean esMovimientoValido(Torre torre, Disco discoAMover) {
        int w = torre.getComponent(torre.getComponentCount() - 1).getWidth();
        int w2 = discoAMover.getWidth();
        return w > w2;
    }

    /**
     * hace el negocio del movimiento del disco
     *
     * @param torre torre a la cual mover el disco
     */
    void moverATorre(Torre torre) {                
        this.controlador.agregarDiscoEnTorre(torre, discoAMover);
        this.discoAMover = null;
    }

    /**
     * valida si el jugador ya ganó
     *
     * @return true si el jugador ya gano, falso en caso contrario
     */
    public boolean yaGane() {
        return this.aros == torre3.getComponentCount();
    }

}

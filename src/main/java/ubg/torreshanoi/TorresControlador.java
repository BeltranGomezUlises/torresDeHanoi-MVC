/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubg.torreshanoi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import ubg.torreshanoi.TorresVista.Disco;
import ubg.torreshanoi.TorresVista.Torre;

/**
 *
 * @author ulises
 */
public class TorresControlador implements MouseListener {

    private TorresVista vista;
    private TorresModelo modelo;
    private boolean animando;

    public TorresControlador(TorresVista vista, TorresModelo modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }

    public TorresVista getVista() {
        return vista;
    }

    public void setVista(TorresVista vista) {
        this.vista = vista;
    }

    public TorresModelo getModelo() {
        return modelo;
    }

    public void setModelo(TorresModelo modelo) {
        this.modelo = modelo;
    }

    public boolean isAnimando() {
        return animando;
    }

    public void setAnimando(boolean animando) {
        this.animando = animando;
    }

    public void iniciarJuego(int numeroDiscos) {
        vista.limpiarTores();
        vista.myInitComponents(numeroDiscos);
    }

    public void reiniciarJuego(int numeroDiscos) {
        vista.limpiarTores();
        vista.inicializarDiscos(numeroDiscos);
        modelo.setAros(numeroDiscos);
    }

    public boolean esMovimientoValido(Torre torer, Disco disco) {
        return this.modelo.esMovimientoValido(torer, disco);
    }

    public Torre torre1() {
        return this.modelo.getTorre1();
    }

    public Torre torre2() {
        return this.modelo.getTorre2();
    }

    public Torre torre3() {
        return this.modelo.getTorre3();
    }

    /**
     * realiza la accion de quitar el disco de una torre
     *
     * @param torre es la torre a la cual quitarle el ultimo disco
     */
    public void quitarUltimoDisco(Torre torre) {
        this.vista.removerDisco(torre);
    }

    /**
     * controla el estado del jugador
     *
     * @return true si puede tomar, false si puede dejar
     */
    public boolean tomarODejarDisco() {
        return this.modelo.tomarODejarDisco();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!this.animando) {
            //tomar o dejar disco             
            if (modelo.tomarODejarDisco()) {//si estoy en tiempo de tomar
                if (modelo.discosTorre1() > 0) {
                    if (e.getSource() == modelo.ultimoDiscoTorre1()) {
                        modelo.tomarDiscoTorre1();
                        return;
                    }
                }
                if (modelo.discosTorre2() > 0) {
                    if (e.getSource() == modelo.ultimoDiscoTorre2()) {
                        modelo.tomarDiscoTorre2();
                        return;
                    }
                }
                if (modelo.discosTorre3() > 0) {
                    if (e.getSource() == modelo.ultimoDiscoTorre3()) {
                        modelo.tomarDiscoTorre3();
                        return;
                    }
                }
            } else {//estoy en tiempo de dejar            
                if (e.getSource() == modelo.getTorre1()) {
                    Disco discoAMover = modelo.getDiscoAMover();
                    if (modelo.getTorre1().getComponentCount() == 0) {
                        modelo.moverATorre(modelo.getTorre1());
                    } else {
                        if (modelo.esMovimientoValido(modelo.getTorre1(), discoAMover)) {
                            modelo.moverATorre(modelo.getTorre1());
                        } else {
                            vista.mostrarMovimientoInvalido();
                        }
                    }
                    return;
                }
                if (e.getSource() == modelo.getTorre2()) {
                    Disco discoAMover = modelo.getDiscoAMover();
                    if (modelo.getTorre2().getComponentCount() == 0) {
                        modelo.moverATorre(modelo.getTorre2());
                    } else {
                        if (modelo.esMovimientoValido(modelo.getTorre2(), discoAMover)) {
                            modelo.moverATorre(modelo.getTorre2());
                        } else {
                            vista.mostrarMovimientoInvalido();
                        }
                    }
                    return;
                }
                if (e.getSource() == modelo.getTorre3()) {
                    Disco discoAMover = modelo.getDiscoAMover();
                    if (modelo.getTorre3().getComponentCount() == 0) {
                        modelo.moverATorre(modelo.getTorre3());
                    } else {
                        if (modelo.esMovimientoValido(modelo.getTorre3(), discoAMover)) {
                            modelo.moverATorre(modelo.getTorre3());
                        } else {
                            vista.mostrarMovimientoInvalido();
                        }
                    }
                    if (modelo.yaGane()) {
                        vista.mostrarJuegoTerminado();
                    }
                    return;
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Eventos no usados">
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
//</editor-fold>

    /**
     * realiza la accion de agregarle un disco a la torre
     *
     * @param torre torre a la cual agregarle un disco
     * @param discoAMover disco a agregar
     */
    void agregarDiscoEnTorre(Torre torre, Disco discoAMover) {
        this.vista.agregarDiscoEnTorre(torre, discoAMover);
    }

}

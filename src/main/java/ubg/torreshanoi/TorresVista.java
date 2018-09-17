/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ubg.torreshanoi;

import java.awt.Color;
import static java.awt.Color.WHITE;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author ulises
 */
public class TorresVista extends javax.swing.JFrame {

    private TorresControlador controlador;
    private JButton btnIniciar;
    private JComboBox cmbDiscos;
    private JLabel lbCmbDiscos;

    /**
     * Creates new form Vista
     */
    public TorresVista() {
        initComponents();
        this.setResizable(false);
    }

    public TorresControlador getControlador() {
        return controlador;
    }

    public void setControlador(TorresControlador controlador) {
        this.controlador = controlador;
    }

    public void myInitComponents(int numeroDeDiscos) {
        panelPrincipal.setBackground(WHITE);
        panelPrincipal.setLayout(null);

        //torre1        
        Torre torre1 = this.controlador.torre1();
        torre1.setBounds(0, 10, 220, 300);
        torre1.addMouseListener(this.controlador);
        this.inicializarDiscos(numeroDeDiscos);
        panelPrincipal.add(torre1);

        //torre2
        Torre torre2 = this.controlador.torre2();
        torre2.setBounds(220, 10, 220, 300);
        torre2.addMouseListener(this.controlador);
        panelPrincipal.add(torre2);

        //torre3
        Torre torre3 = this.controlador.torre3();
        torre3.setBounds(440, 10, 220, 300);
        torre3.addMouseListener(this.controlador);
        panelPrincipal.add(torre3);

        //lb numero de aros
        lbCmbDiscos = new JLabel("Discos:");
        lbCmbDiscos.setBounds(670, 60, 150, 20);
        panelPrincipal.add(lbCmbDiscos);

        //btn iniciar
        btnIniciar = new JButton("Iniciar");
        btnIniciar.setBounds(670, 140, 90, 30);
        btnIniciar.addActionListener(__ -> {
            int nuevoNumeroDiscos = (int) cmbDiscos.getSelectedItem();
            controlador.reiniciarJuego(nuevoNumeroDiscos);
        });
        panelPrincipal.add(btnIniciar);

        //combo con numero de discos
        cmbDiscos = new JComboBox();
        for (int i = 3; i <= 8; i++) {
            cmbDiscos.addItem(i);
        }
        cmbDiscos.setBounds(670, 80, 90, 30);
        panelPrincipal.add(cmbDiscos);
    }

    /**
     * coloca en el estado de arranque del juego
     *
     * @param numeroDiscos numero de discos con los que se quiere jugar
     */
    public void inicializarDiscos(int numeroDiscos) {
        Disco discoInicial = new Disco();
        discoInicial.setBounds(90, 180, 45, 20);
        discoInicial.addMouseListener(this.controlador);
        Torre torre1 = this.controlador.torre1();
        torre1.add(discoInicial);
        for (int i = 1; i <= numeroDiscos - 1; i++) {
            Disco otroDisco = new Disco();
            otroDisco.addMouseListener(this.controlador);
            torre1.add(otroDisco);
        }
        calcularAchuras(numeroDiscos, torre1);
        torre1.updateUI();
    }

    /**
     * calcula y asigna las posiciones y anchuras iniciales de los discos
     *
     * @param numeroDiscos
     */
    public void calcularAchuras(int numeroDiscos, Torre torre1) {
        if (numeroDiscos >= 0) {

            for (int j = 1; j <= numeroDiscos - 1; j++) {
                Disco discoAnterior = (Disco) torre1.getComponent(j - 1);
                //posiciones y tamaño del aro anterior
                int x = discoAnterior.getX();
                int y = discoAnterior.getY();
                int w = discoAnterior.getWidth();
                int h = discoAnterior.getHeight();

                Disco discoSiguiente = (Disco) torre1.getComponent(j);
                discoSiguiente.setBounds(x, y - h, w, h);
                discoAnterior.setBounds(x - 10, y, w + 20, h);
                torre1.setComponentZOrder(discoSiguiente, j);
                torre1.setComponentZOrder(discoAnterior, j - 1);
            }
            calcularAchuras(numeroDiscos - 1, torre1);
        }
    }

    /**
     * remueve de las torres todos los discos
     */
    public void limpiarTores() {
        this.controlador.torre1().removeAll();
        this.controlador.torre2().removeAll();
        this.controlador.torre3().removeAll();

        this.controlador.torre1().updateUI();
        this.controlador.torre2().updateUI();
        this.controlador.torre3().updateUI();
    }

    /**
     * remueve de la vista un disco de la torre y actualiza su estado
     *
     * @param torre la torre a removerle un disco
     */
    public void removerDisco(Torre torre) {
        Disco discoRemover = (Disco) torre.getComponent(torre.getComponentCount() - 1);
        final Timer t = new Timer(10, e -> {
            discoRemover.setBounds(
                    discoRemover.getX(),
                    discoRemover.getY() - 4,
                    discoRemover.getWidth(),
                    discoRemover.getHeight()
            );
            discoRemover.updateUI();
            if (discoRemover.getY() + discoRemover.getHeight() < 0) {
                Timer thisTimer = (Timer) e.getSource();
                thisTimer.stop();
                torre.remove(discoRemover);
                torre.updateUI();
                controlador.setAnimando(false);
            }
        });
        controlador.setAnimando(true);
        t.start();

    }

    /**
     * lanza un dialogo con mensaje de movimiento inválido
     */
    public void mostrarMovimientoInvalido() {
        JOptionPane.showMessageDialog(null, "Movimiento inválido", "Atención", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * lanza un dialogo con mensaje de juego terminado
     */
    public void mostrarJuegoTerminado() {
        JOptionPane.showMessageDialog(null, "Juego terminado", "Fin del juego", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Agrega un disco a una torre
     *
     * @param torre torre a agregar el disco
     * @param discoAMover disco a agregar
     */
    void agregarDiscoEnTorre(Torre torre, Disco discoAMover) {
        final int discosDeTorre = torre.getComponentCount();
        torre.add(discoAMover);
        final Timer t = new Timer(10, e -> {
            int yLimite = 180;
            if (discosDeTorre != 0) {
                yLimite = 180 - (20 * (torre.getComponentCount() - 1));
            }
            discoAMover.setBounds(
                    discoAMover.getX(),
                    discoAMover.getY() + 4,
                    discoAMover.getWidth(),
                    discoAMover.getHeight()
            );
            discoAMover.updateUI();
            if (discoAMover.getY() > yLimite) {
                Timer thisTimer = (Timer) e.getSource();
                thisTimer.stop();
                discoAMover.setBounds(
                        discoAMover.getX(),
                        yLimite,
                        discoAMover.getWidth(),
                        discoAMover.getHeight()
                );
                torre.updateUI();
                controlador.setAnimando(false);
            }
        });
        controlador.setAnimando(true);
        t.start();

    }

    public static class Torre extends JPanel {

        public Torre() {
            this.setLayout(null);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(Color.WHITE);
            //base            
            g.setColor(new Color(118, 60, 40));
            g.fillRect(10, 200, 200, 15);

            g.setColor(Color.black);
            g.drawRect(10, 200, 200, 15);
            //palo
            g.setColor(new Color(166, 94, 46));
            g.fillRect(110, 0, 5, 200);
        }
    }

    public static class Disco extends JPanel {

        public Disco() {
            Random random = new Random();
            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();
            Color color = new Color(r, g, b);
            this.setBackground(color);
            this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Torres de hanoi");

        panelPrincipal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout panelPrincipalLayout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(panelPrincipalLayout);
        panelPrincipalLayout.setHorizontalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        panelPrincipalLayout.setVerticalGroup(
            panelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 236, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}

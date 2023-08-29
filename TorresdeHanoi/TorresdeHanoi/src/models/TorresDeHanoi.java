package models;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Erika Camargo
 */
public class TorresDeHanoi extends JFrame {

	private int nivel;
    private int cantidadDiscos;
    private TorresPanel torresPanel;
    private JComboBox<String> nivelComboBox;
    private int selectedTowerIndex = -1;
    private int mouseXOffset = 0;

    public TorresDeHanoi() {
        nivelComboBox = new JComboBox<>(new String[]{"Principiante", "Novato", "Intermedio", "Avanzado","Experto", "Maestro"});
        nivelComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarNivel();
            }
        });

        try {
            BufferedReader br = new BufferedReader(new FileReader("./data/nivel.txt"));
            nivel = Integer.parseInt(br.readLine());
            cantidadDiscos = Integer.parseInt(br.readLine());
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Torres de Hanoi");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        torresPanel = new TorresPanel(cantidadDiscos);
        add(torresPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Seleccionar nivel:"));
        controlPanel.add(nivelComboBox);
        JButton resolverButton = new JButton("Resolver");
        resolverButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                torresPanel.resolver();
            }
        });
        controlPanel.add(resolverButton);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
        
        torresPanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedTowerIndex = torresPanel.getTowerIndex(e.getX());
                if (selectedTowerIndex != -1) {
                    mouseXOffset = e.getX() - torresPanel.getTowerX(selectedTowerIndex);
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (selectedTowerIndex != -1) {
                    int targetTowerIndex = torresPanel.getTowerIndex(e.getX());
                    if (targetTowerIndex != -1 && targetTowerIndex != selectedTowerIndex) {
                        torresPanel.moveDisk(selectedTowerIndex, targetTowerIndex);
                    }
                }
                selectedTowerIndex = -1;
            }
        });

        torresPanel.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (selectedTowerIndex != -1) {
                    int newX = e.getX() - mouseXOffset;
                    torresPanel.setTowerX(selectedTowerIndex, newX);
                    repaint();
                }
            }
        });

    }
    
    

    private void actualizarNivel() {
        try {
            int seleccion = nivelComboBox.getSelectedIndex() + 1;
            BufferedReader br = new BufferedReader(new FileReader("./data/nivel.txt"));
            for (int i = 0; i < seleccion; i++) {
                nivel = Integer.parseInt(br.readLine());
                cantidadDiscos = Integer.parseInt(br.readLine());
            }
            br.close();
            torresPanel.actualizarCantidadDiscos(cantidadDiscos);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TorresDeHanoi();
            }
        });
    }
}

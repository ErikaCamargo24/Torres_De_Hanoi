package models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class TorresPanel extends JPanel {

    private int cantidadDiscos;
    private Torre torreA, torreB, torreC;
    private int torreAX = 50; // Posición X de la Torre A
    private int torreBX = 300; // Posición X de la Torre B
    private int torreCX = 550; // Posición X de la Torre C
    
    private ArrayList<Torre> torres;

    public TorresPanel(int cantidadDiscos) {
    	torres = new ArrayList<>();
        this.cantidadDiscos = cantidadDiscos;
        torreA = new Torre(cantidadDiscos, "A");
        torreB = new Torre(0, "B");
        torreC = new Torre(0, "C");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xTorreA = 50;
        int xTorreB = 300;
        int xTorreC = 550;
        int yBase = 300;
        int yAltura = 20;

        dibujarTorre(g, torreA.getDiscos(), xTorreA, yBase, yAltura);
        dibujarTorre(g, torreB.getDiscos(), xTorreB, yBase, yAltura);
        dibujarTorre(g, torreC.getDiscos(), xTorreC, yBase, yAltura);
    }


    private void dibujarTorre(Graphics g, java.util.Stack<Disco> discos, int x, int y, int altura) {
        g.setColor(Color.BLACK);
        g.drawRect(x + 40, y - discos.size() * altura, 20, discos.size() * altura);
        g.drawRect(x, y, 100, 20);

        int alturaActual = y;
        for (Disco disco : discos) {
            int diametro = disco.getDiametro();
            int xDisco = x + 40 - diametro / 2;

            Color discoColor = new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256));
            g.setColor(discoColor);

            // Relleno del disco
            g.fillRect(xDisco, alturaActual - altura, diametro, altura);

            // Contorno negro del disco
            g.setColor(Color.BLACK);
            g.drawRect(xDisco, alturaActual - altura, diametro, altura);

            alturaActual -= altura;
        }

        g.setColor(Color.BLACK);
        g.drawString("Torre", x + 40, y + 20);
    }

    public void resolver() {
        resolverHanoi(cantidadDiscos, torreA, torreC, torreB);
    }

    private void resolverHanoi(int n, Torre origen, Torre destino, Torre auxiliar) {
        if (n > 0) {
            resolverHanoi(n - 1, origen, auxiliar, destino);
            Disco disco = origen.pop();
            destino.push(disco);
            repaint();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resolverHanoi(n - 1, auxiliar, destino, origen);
        }
    }
    
    
    public void actualizarCantidadDiscos(int nuevaCantidadDiscos) {
        this.cantidadDiscos = nuevaCantidadDiscos;
        torreA = new Torre(cantidadDiscos, "A");
        torreB = new Torre(0, "B");
        torreC = new Torre(0, "C");
        repaint();
    }

    public int getTowerIndex(int mouseX) {
        if (mouseX >= torreAX && mouseX <= torreAX + 100) {
            return 0;
        } else if (mouseX >= torreBX && mouseX <= torreBX + 100) {
            return 1;
        } else if (mouseX >= torreCX && mouseX <= torreCX + 100) {
            return 2;
        } else {
            return -1;
        }
    }

    public int getTowerX(int towerIndex) {
        if (towerIndex == 0) {
            return torreAX;
        } else if (towerIndex == 1) {
            return torreBX;
        } else if (towerIndex == 2) {
            return torreCX;
        } else {
            return -1;
        }
    }

    public void setTowerX(int towerIndex, int newX) {
        if (towerIndex == 0) {
            torreAX = newX;
        } else if (towerIndex == 1) {
            torreBX = newX;
        } else if (towerIndex == 2) {
            torreCX = newX;
        }
    }

    public void moveDisk(int sourceTowerIndex, int targetTowerIndex) {
        Torre sourceTower = torres.get(sourceTowerIndex);
        Torre targetTower = torres.get(targetTowerIndex);

        if (!sourceTower.isEmpty()) {
            Disco diskToMove = sourceTower.pop();
            if (targetTower.isEmpty() || diskToMove.getDiametro() < targetTower.peek().getDiametro()) {
                targetTower.push(diskToMove);
            } else {
                sourceTower.push(diskToMove);
            }
        }
    }

}

package models;

import java.awt.Color;
import java.awt.Graphics;

public class Torre {
    private java.util.Stack<Disco> discos;
    private String nombre;

    public Torre(int cantidadDiscos, String nombre) {
        discos = new java.util.Stack<Disco>();
        this.nombre = nombre;
        for (int i = cantidadDiscos; i > 0; i--) {
            discos.push(new Disco(i * 20));
        }
    }

    public Disco pop() {
        return discos.pop();
    }

    public void push(Disco disco) {
        discos.push(disco);
    }

    public void dibujar(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.drawRect(x + 40, y + 200, 20, 200);
        g.drawRect(x, y + 380, 100, 20);

        int altura = 380;
        for (Disco disco : discos) {
        	g.setColor(Color.BLACK);
            g.setColor(Color.BLUE);
            g.fillRect(x + 40 - disco.getDiametro() / 2, altura, disco.getDiametro(), 20);
            altura -= 20;
        }

        g.setColor(Color.BLACK);
        g.drawString(nombre, x + 40, y + 220);
    }
    
    public java.util.Stack<Disco> getDiscos() {
        return discos;
    }
    
    public boolean isEmpty() {
        return discos.isEmpty();
    }
    
    public Disco peek() {
        if (!discos.isEmpty()) {
            return discos.peek();
        } else {
            return null; // Devuelve null si la torre está vacía
        }
    }
    
}
// Juego de consola de Java Puzzler....

import javax.swing.*;
import java.awt.*;

public class PuzzlerX {
    public static void main(String[] args) {
        // Crear la ventana principal
        JFrame frame = new JFrame("Puzzler-X");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        // Crear los componentes de la interfaz
        JPanel boardPanel = new JPanel(new GridLayout(4, 4));
        JLabel moveLabel = new JLabel("Move: 0");
        JLabel timeLabel = new JLabel("Time: 00:00");
        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        // Establecer los colores de fondo y de texto
        frame.getContentPane().setBackground(Color.WHITE);
        boardPanel.setBackground(Color.WHITE);
        moveLabel.setForeground(Color.BLACK);
        timeLabel.setForeground(Color.BLACK);

        // Agregar los componentes a la ventana
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(moveLabel, BorderLayout.NORTH);
        frame.add(timeLabel, BorderLayout.SOUTH);
        frame.add(startButton, BorderLayout.WEST);
        frame.add(stopButton, BorderLayout.EAST);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}

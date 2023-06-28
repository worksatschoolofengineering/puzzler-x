import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PuzzleXApp {
    private JFrame frame;
    private JPanel puzzlePanel;
    private JButton startButton;
    private JButton stopButton;
    private JTextField movesField;
    private JTextField timeField;
    
    private int movesCount;
    private Timer timer;
    private int seconds;
    
    public PuzzleXApp() {
        // Crear la ventana principal
        frame = new JFrame("Puzzle-X");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        
        // Crear el panel del tablero de juego
        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(4, 4, 5, 5));
        
        // Crear los botones del tablero de juego
        for (int i = 1; i <= 15; i++) {
            JButton button = new JButton(String.valueOf(i));
            puzzlePanel.add(button);
        }
        
        // Crear el botón "Start"
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        
        // Crear el botón "Stop"
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });
        
        // Crear el contador de movimientos
        movesField = new JTextField("Moves: 0");
        movesField.setEditable(false);
        
        // Crear el contador de tiempo
        timeField = new JTextField("Time: 0s");
        timeField.setEditable(false);
        
        // Añadir los componentes a la ventana
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(puzzlePanel, BorderLayout.CENTER);
        frame.getContentPane().add(startButton, BorderLayout.NORTH);
        frame.getContentPane().add(stopButton, BorderLayout.SOUTH);
        frame.getContentPane().add(movesField, BorderLayout.WEST);
        frame.getContentPane().add(timeField, BorderLayout.EAST);
        
        // Mostrar la ventana
        frame.setVisible(true);
    }
    
    private void startGame() {
        // Iniciar el juego
        movesCount = 0;
        seconds = 0;
        
        // Actualizar los contadores
        movesField.setText("Moves: " + movesCount);
        timeField.setText("Time: " + seconds + "s");
        
        // Iniciar el temporizador
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
                timeField.setText("Time: " + seconds + "s");
            }
        });
        timer.start();
    }
    
    private void stopGame() {
        // Detener el juego
        if (timer != null) {
            timer.stop();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PuzzleXApp();
            }
        });
    }
}

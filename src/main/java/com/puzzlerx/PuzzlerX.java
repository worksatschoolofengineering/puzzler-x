import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzlerX {
    private JFrame frame;
    private JPanel puzzlePanel;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JTextField movesField;
    private JTextField timeField;

    private int movesCount;
    private Timer timer;
    private int seconds;
    private int minutes;
    private int hours;

    private boolean gameStarted;

    public PuzzlerX() {
        // Crear la ventana principal
        frame = new JFrame("Puzzler-X");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);

        // Crear el panel del tablero de juego
        puzzlePanel = new JPanel();
        puzzlePanel.setLayout(new GridLayout(4, 4, 5, 5));
        puzzlePanel.setBackground(Color.WHITE); // Establecer el color de fondo blanco

        // Crear los botones del tablero de juego
        List<JButton> buttons = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setFocusable(false);
            button.addActionListener(new MoveButtonListener());
            button.setEnabled(false);
            button.setBackground(Color.RED); // Establecer el color de fondo rojo
            buttons.add(button);
            puzzlePanel.add(button);
        }
        JButton emptyButton = new JButton();
        emptyButton.setFocusable(false);
        emptyButton.addActionListener(new MoveButtonListener());
        emptyButton.setEnabled(false);
        buttons.add(emptyButton);
        puzzlePanel.add(emptyButton);

        // Crear el botón "Start"
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        startButton.setBackground(Color.RED); // Establecer el color de fondo rojo

        // Crear el botón "Stop"
        stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stopGame();
            }
        });
        stopButton.setEnabled(false);
        stopButton.setBackground(Color.RED); // Establecer el color de fondo rojo

        // Crear el botón "Reset"
        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });
        resetButton.setEnabled(false);
        resetButton.setBackground(Color.RED); // Establecer el color de fondo rojo

        // Crear el contador de movimientos
        movesField = new JTextField("Moves: 0");
        movesField.setEditable(false);
        movesField.setEnabled(false);

        // Crear el contador de tiempo
        timeField = new JTextField("Time: 00:00:00");
        timeField.setEditable(false);
        timeField.setEnabled(false);

        // Añadir los componentes a la ventana
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(puzzlePanel, BorderLayout.CENTER);
        frame.getContentPane().add(startButton, BorderLayout.NORTH);
        frame.getContentPane().add(stopButton, BorderLayout.SOUTH);
        frame.getContentPane().add(resetButton, BorderLayout.WEST);
        frame.getContentPane().add(movesField, BorderLayout.WEST);
        frame.getContentPane().add(timeField, BorderLayout.EAST);

        // Establecer el color de fondo de la ventana
        frame.getContentPane().setBackground(Color.WHITE);

        // Mostrar la ventana
        frame.setVisible(true);
    }

    private class MoveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!gameStarted) {
                return;
            }

            JButton button = (JButton) e.getSource();
            String buttonText = button.getText();

            if (buttonText.equals("")) {
                return;
            }

            JButton emptyButton = findEmptyButton();
            if (emptyButton == null) {
                return;
            }

            int buttonIndex = puzzlePanel.getComponentZOrder(button);
            int emptyButtonIndex = puzzlePanel.getComponentZOrder(emptyButton);

            // Verificar si el botón seleccionado está adyacente al espacio vacío en sentido vertical u horizontal
            int rowDiff = Math.abs(buttonIndex / 4 - emptyButtonIndex / 4);
            int colDiff = Math.abs(buttonIndex % 4 - emptyButtonIndex % 4);
            if ((rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
                emptyButton.setText(buttonText);
                button.setText("");

                movesCount++;
                movesField.setText("Moves: " + movesCount);

                if (checkWin()) {
                    stopGame();
                    JOptionPane.showMessageDialog(frame, "Congratulations! You solved the puzzle in " + movesCount + " moves.");
                }
            }
        }

        private JButton findEmptyButton() {
            Component[] components = puzzlePanel.getComponents();
            for (Component component : components) {
                JButton button = (JButton) component;
                if (button.getText().equals("")) {
                    return button;
                }
            }
            return null;
        }
    }

    private void startGame() {
        // Iniciar el juego
        movesCount = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;

        // Actualizar los contadores
        movesField.setText("Moves: " + movesCount);
        updateTimeField();

        // Habilitar los botones y contadores
        enableButtons(true);
        movesField.setEnabled(true);
        timeField.setEnabled(true);
        stopButton.setEnabled(true);
        resetButton.setEnabled(false);

        // Barajar las posiciones de los botones
        List<JButton> buttons = new ArrayList<>();
        Component[] components = puzzlePanel.getComponents();
        for (Component component : components) {
            JButton button = (JButton) component;
            buttons.add(button);
        }
        Collections.shuffle(buttons);

        // Actualizar las posiciones de los botones en el panel
        puzzlePanel.removeAll();
        for (JButton button : buttons) {
            puzzlePanel.add(button);
        }

        // Iniciar el temporizador
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                    if (minutes == 60) {
                        minutes = 0;
                        hours++;
                    }
                }
                updateTimeField();
            }
        });
        timer.start();

        gameStarted = true;
        startButton.setEnabled(false);
    }

    private void stopGame() {
        // Detener el juego
        if (timer != null) {
            timer.stop();
        }

        // Deshabilitar los botones y contadores
        enableButtons(false);
        movesField.setEnabled(false);
        timeField.setEnabled(false);
        stopButton.setEnabled(false);
        resetButton.setEnabled(true);

        gameStarted = false;
        startButton.setEnabled(true);
    }

    private void resetGame() {
        // Restablecer el juego
        stopGame();
        startGame();
    }

    private boolean checkWin() {
        Component[] components = puzzlePanel.getComponents();
        for (int i = 0; i < components.length - 1; i++) {
            JButton button = (JButton) components[i];
            String buttonText = button.getText();
            if (!buttonText.equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return true;
    }

    private void updateTimeField() {
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeField.setText("Time: " + timeString);
    }

    private void enableButtons(boolean enabled) {
        Component[] components = puzzlePanel.getComponents();
        for (Component component : components) {
            JButton button = (JButton) component;
            button.setEnabled(enabled);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PuzzlerX();
            }
        });
    }
}

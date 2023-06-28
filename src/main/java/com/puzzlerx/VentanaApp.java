import javax.swing.JFrame;
import javax.swing.JLabel;

public class VentanaApp {

    public static void main(String[] args) {
        // Crear una instancia de JFrame (ventana)
        JFrame ventana = new JFrame("Mi Aplicación");
        
        // Configurar dimensiones de la ventana
        ventana.setSize(400, 300);
        
        // Establecer operación al cerrar la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Crear un componente de etiqueta (texto)
        JLabel etiqueta = new JLabel("¡Hola, mundo!");
        
        // Agregar el componente a la ventana
        ventana.getContentPane().add(etiqueta);
        
        // Hacer visible la ventana
        ventana.setVisible(true);
    }
}



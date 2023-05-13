import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Button implements ActionListener {
    //Atributos
    boolean activo = false;

    //Atributos Socket
    Socket cliente;
    ObjectOutputStream output;
    final int PUERTO = 1234;
    final String IP = "localhost";

    //Atributos Interfaz
    JFrame frame;
    JPanel panel;
    JButton button;

    //Constructor
    public Button() {
        frame = new JFrame("Tarea Programada POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button = new JButton("Enceder");
        button.addActionListener(this);
        button.setActionCommand("encender");

        panel = new JPanel(new BorderLayout());
        panel.add(button, BorderLayout.EAST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

    }

    //Metodos
    public void conectar(){
        try {
            System.out.println(activo);
            cliente = new Socket(IP, PUERTO);
            output = new ObjectOutputStream(cliente.getOutputStream());
            output.writeObject(activo);
            output.flush();
            output.close();
            cliente.close();
            JOptionPane.showMessageDialog(null, activo ? "Encendido" : "Apagado");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("encender")){
            button.setText("apagar");
            button.setActionCommand("apagar");
            activo = true;
            conectar();
        }
        else{
            button.setText("encender");
            button.setActionCommand("encender");
            activo = false;
            conectar();
        }
    }

}

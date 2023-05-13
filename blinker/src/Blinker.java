import javax.swing.*;
import java.awt.*;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Blinker extends Thread {
    //Atributos
    boolean activo = false;

    //Atributos Socket
    Socket cliente;
    ServerSocket servidor;
    ObjectInputStream input;
    final int PUERTO = 1234;

    //Atributos Interfaz
    JFrame frame;
    JPanel panel;
    JLabel blinker;

    //Constructor
    public Blinker() {
        start();
        frame = new JFrame("Tarea Programada POO");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        blinker = new JLabel();
        blinker.setPreferredSize(new Dimension(100, 100));
        blinker.setOpaque(true);
        blinker.setBackground(Color.WHITE);


        panel = new JPanel(new BorderLayout());
        panel.add(blinker, BorderLayout.WEST);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        blink();
    }

    //Getters & Setters
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    //Metodos
    public void run(){
        abrirConexion();
    }
    public void abrirConexion() {
        try {
            while (true) {
                servidor = new ServerSocket(PUERTO);
                cliente = servidor.accept();
                input = new ObjectInputStream(cliente.getInputStream());
                boolean activo = (boolean) input.readObject();
                input.close();
                cliente.close();
                servidor.close();
                this.setActivo(activo);
                System.out.println(this.activo);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void blink(){
        while(true){
            System.out.println(activo);
            if (activo){
                try {
                    blinker.setBackground(Color.YELLOW);
                    Thread.sleep(500);
                    blinker.setBackground(Color.WHITE);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

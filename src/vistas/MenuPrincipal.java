package vistas;

import javax.swing.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("Sistema de Inventario y Ventas");
        setSize(300,300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton b1 = new JButton("Productos");
        b1.setBounds(70,50,150,40);
        add(b1);

        JButton b2 = new JButton("Clientes");
        b2.setBounds(70,110,150,40);
        add(b2);

        JButton b3 = new JButton("Ventas");
        b3.setBounds(70,170,150,40);
        add(b3);

        b1.addActionListener(e -> new VentanaProducto());
        b2.addActionListener(e -> new VentanaCliente());
        b3.addActionListener(e -> new VentanaVenta());
        
        JButton b4 = new JButton("Reportes");
b4.setBounds(70, 230, 150, 40);
add(b4);

b4.addActionListener(e -> new VentanaReportes());


        setVisible(true);
    }
}

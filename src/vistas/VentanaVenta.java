package vistas;

import dao.ClienteDAO;
import dao.ProductoDAO;
import dao.VentaDAO;
import modelos.Cliente;
import modelos.Producto;
import javax.swing.*;
import java.util.ArrayList;

public class VentanaVenta extends JFrame {

    JComboBox<String> boxClientes, boxProductos;
    JTextField txtCantidad;

    ClienteDAO clienteDAO = new ClienteDAO();
    ProductoDAO productoDAO = new ProductoDAO();
    VentaDAO ventaDAO = new VentaDAO();

    public VentanaVenta() {

        setTitle("Registrar Venta");
        setSize(400, 300);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel l1 = new JLabel("Cliente:");
        l1.setBounds(20,20,100,30);
        add(l1);

        boxClientes = new JComboBox<>();
        boxClientes.setBounds(150,20,200,30);
        add(boxClientes);

        JLabel l2 = new JLabel("Producto:");
        l2.setBounds(20,70,100,30);
        add(l2);

        boxProductos = new JComboBox<>();
        boxProductos.setBounds(150,70,200,30);
        add(boxProductos);

        JLabel l3 = new JLabel("Cantidad:");
        l3.setBounds(20,120,100,30);
        add(l3);

        txtCantidad = new JTextField();
        txtCantidad.setBounds(150,120,200,30);
        add(txtCantidad);

        JButton btnVender = new JButton("Registrar Venta");
        btnVender.setBounds(150,170,150,30);
        add(btnVender);

        cargarClientes();
        cargarProductos();

        btnVender.addActionListener(e -> registrar());

        setVisible(true);
    }

    void cargarClientes() {
        ArrayList<Cliente> lista = clienteDAO.listarClientes();
        for (Cliente c : lista) {
            boxClientes.addItem(c.id + " - " + c.nombre);
        }
    }

    void cargarProductos() {
        ArrayList<Producto> lista = productoDAO.listarProductos();
        for (Producto p : lista) {
            boxProductos.addItem(p.id + " - " + p.nombre);
        }
    }

    void registrar() {
        int idCliente = Integer.parseInt(boxClientes.getSelectedItem().toString().split(" - ")[0]);
        int idProducto = Integer.parseInt(boxProductos.getSelectedItem().toString().split(" - ")[0]);
        int cantidad = Integer.parseInt(txtCantidad.getText());

        ventaDAO.registrarVenta(idCliente, idProducto, cantidad);

        JOptionPane.showMessageDialog(this, "Venta registrada!");
    }
}

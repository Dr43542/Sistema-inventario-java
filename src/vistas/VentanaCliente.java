package vistas;

import dao.ClienteDAO;
import modelos.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VentanaCliente extends JFrame {

    JTextField txtNombre, txtTelefono;
    JTable tabla;
    DefaultTableModel modelo;

    ClienteDAO dao = new ClienteDAO();

    public VentanaCliente() {

        setTitle("Gestión de Clientes");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel l1 = new JLabel("Nombre:");
        l1.setBounds(20, 20, 100, 30);
        add(l1);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 20, 150, 30);
        add(txtNombre);

        JLabel l2 = new JLabel("Telefono:");
        l2.setBounds(20, 60, 100, 30);
        add(l2);

        txtTelefono = new JTextField();
        txtTelefono.setBounds(120, 60, 150, 30);
        add(txtTelefono);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(300, 30, 100, 30);
        add(btnAgregar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(300, 70, 100, 30);
        add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(300, 110, 100, 30);
        add(btnEliminar);

        modelo = new DefaultTableModel(new String[]{"ID","Nombre","Telefono"}, 0);
        tabla = new JTable(modelo);

        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 150, 540, 200);
        add(sp);

        cargarTabla();

        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                txtTelefono.setText(tabla.getValueAt(fila, 2).toString());
            }
        });

        setVisible(true);
    }

    void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Cliente> lista = dao.listarClientes();

        for (Cliente c : lista) {
            modelo.addRow(new Object[]{c.id, c.nombre, c.telefono});
        }
    }

    void agregar() {
        dao.insertarCliente(txtNombre.getText(), txtTelefono.getText());
        cargarTabla();
    }

    void editar() {
        int fila = tabla.getSelectedRow();
        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

        dao.actualizarCliente(id, txtNombre.getText(), txtTelefono.getText());
        cargarTabla();
    }

    void eliminar() {
        int fila = tabla.getSelectedRow();
        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

        dao.eliminarCliente(id);
        cargarTabla();
    }
}

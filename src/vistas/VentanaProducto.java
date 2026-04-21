package vistas;

import dao.ProductoDAO;
import modelos.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class VentanaProducto extends JFrame {

    JTextField txtNombre, txtPrecio, txtStock;
    JTable tabla;
    DefaultTableModel modelo;

    ProductoDAO dao = new ProductoDAO();

    public VentanaProducto() {

        setTitle("Gestión de Productos");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel l1 = new JLabel("Nombre:");
        l1.setBounds(20, 20, 100, 30);
        add(l1);

        txtNombre = new JTextField();
        txtNombre.setBounds(120, 20, 150, 30);
        add(txtNombre);

        JLabel l2 = new JLabel("Precio:");
        l2.setBounds(20, 60, 100, 30);
        add(l2);

        txtPrecio = new JTextField();
        txtPrecio.setBounds(120, 60, 150, 30);
        add(txtPrecio);

        JLabel l3 = new JLabel("Stock:");
        l3.setBounds(20, 100, 100, 30);
        add(l3);

        txtStock = new JTextField();
        txtStock.setBounds(120, 100, 150, 30);
        add(txtStock);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(300, 30, 100, 30);
        add(btnAgregar);

        JButton btnEditar = new JButton("Editar");
        btnEditar.setBounds(300, 70, 100, 30);
        add(btnEditar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(300, 110, 100, 30);
        add(btnEliminar);

        modelo = new DefaultTableModel(new String[]{"ID","Nombre","Precio","Stock"}, 0);
        tabla = new JTable(modelo);

        JScrollPane sp = new JScrollPane(tabla);
        sp.setBounds(20, 150, 540, 200);
        add(sp);

        // Cargar datos
        cargarTabla();

        // EVENTOS
        btnAgregar.addActionListener(e -> agregar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText(tabla.getValueAt(fila, 1).toString());
                txtPrecio.setText(tabla.getValueAt(fila, 2).toString());
                txtStock.setText(tabla.getValueAt(fila, 3).toString());
            }
        });

        setVisible(true);
    }

    void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Producto> lista = dao.listarProductos();

        for (Producto p : lista) {
            modelo.addRow(new Object[]{p.id, p.nombre, p.precio, p.stock});
        }
    }

    void agregar() {
        dao.insertarProducto(
            txtNombre.getText(),
            Double.parseDouble(txtPrecio.getText()),
            Integer.parseInt(txtStock.getText())
        );
        cargarTabla();
    }

    void editar() {
        int fila = tabla.getSelectedRow();
        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

        dao.actualizarProducto(
            id,
            txtNombre.getText(),
            Double.parseDouble(txtPrecio.getText()),
            Integer.parseInt(txtStock.getText())
        );
        cargarTabla();
    }

    void eliminar() {
        int fila = tabla.getSelectedRow();
        int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());

        dao.eliminarProducto(id);
        cargarTabla();
    }
}

package vistas;

import dao.ReporteDAO;
import modelos.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VentanaReportes extends JFrame {

    JTable tabla;
    DefaultTableModel modelo;
    ReporteDAO rdao = new ReporteDAO();

    public VentanaReportes() {

        setTitle("Reportes del Sistema");
        setSize(700, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        
        modelo = new DefaultTableModel();
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 150, 650, 300);
        add(scroll);

        
        JButton btnBajoStock = new JButton("Productos con bajo stock");
        btnBajoStock.setBounds(20, 20, 250, 40);
        add(btnBajoStock);

        JButton btnRango = new JButton("Ventas por rango de fechas");
        btnRango.setBounds(20, 70, 250, 40);
        add(btnRango);

        JButton btnCliente = new JButton("Ventas por cliente");
        btnCliente.setBounds(300, 20, 250, 40);
        add(btnCliente);

        JButton btnVendidos = new JButton("Productos más vendidos");
        btnVendidos.setBounds(300, 70, 250, 40);
        add(btnVendidos);

        
        btnBajoStock.addActionListener(e -> {
            modelo.setRowCount(0);
            modelo.setColumnCount(0);
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Precio");
            modelo.addColumn("Stock");

            ArrayList<Producto> lista = rdao.productosBajoStock();

            for (Producto p : lista) {
                modelo.addRow(new Object[]{
                        p.id,
                        p.nombre,
                        p.precio,
                        p.stock
                });
            }
        });

        
        btnRango.addActionListener(e -> {

            String f1 = JOptionPane.showInputDialog(this, "Fecha inicial (YYYY-MM-DD):");
            String f2 = JOptionPane.showInputDialog(this, "Fecha final (YYYY-MM-DD):");

            if (f1 == null || f2 == null) return;

            modelo.setRowCount(0);
            modelo.setColumnCount(0);

            modelo.addColumn("ID Venta");
            modelo.addColumn("ID Cliente");
            modelo.addColumn("Total");
            modelo.addColumn("Fecha");

            var lista = rdao.ventasPorRango(f1, f2);

            for (String[] fila : lista) {
                modelo.addRow(fila);
            }

        });

        
        btnCliente.addActionListener(e -> {

            String id = JOptionPane.showInputDialog(this, "ID del cliente:");

            if (id == null) return;

            modelo.setRowCount(0);
            modelo.setColumnCount(0);

            modelo.addColumn("ID Venta");
            modelo.addColumn("Total");
            modelo.addColumn("Fecha");

            var lista = rdao.ventasPorCliente(Integer.parseInt(id));

            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        });

        
        btnVendidos.addActionListener(e -> {

            modelo.setRowCount(0);
            modelo.setColumnCount(0);

            modelo.addColumn("Producto");
            modelo.addColumn("Total Vendido");

            var lista = rdao.productosVendidos();

            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        });

        setVisible(true);
    }
}


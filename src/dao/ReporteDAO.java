package dao;

import conexion.Conexion;
import modelos.Producto;
import java.sql.*;
import java.util.ArrayList;

public class ReporteDAO {

    // 1. Productos con bajo stock
    public ArrayList<Producto> productosBajoStock() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "SELECT * FROM producto WHERE stock < 5"
            );
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.id = rs.getInt("id");
                p.nombre = rs.getString("nombre");
                p.precio = rs.getDouble("precio");
                p.stock = rs.getInt("stock");
                lista.add(p);
            }

            cn.close();
        } catch (Exception e) {
            System.out.println("Error reporte bajo stock: " + e);
        }

        return lista;
    }

    // 2. Ventas por rango de fechas
    public ArrayList<String[]> ventasPorRango(String f1, String f2) {
        ArrayList<String[]> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "SELECT id, id_cliente, total, fecha FROM venta WHERE fecha BETWEEN ? AND ?"
            );
            ps.setString(1, f1);
            ps.setString(2, f2);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("id"),
                    rs.getString("id_cliente"),
                    rs.getString("total"),
                    rs.getString("fecha")
                });
            }

            cn.close();
        } catch (Exception e) {
            System.out.println("Error ventas por rango: " + e);
        }

        return lista;
    }

    // 3. Ventas por cliente
    public ArrayList<String[]> ventasPorCliente(int idCliente) {
        ArrayList<String[]> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "SELECT id, total, fecha FROM venta WHERE id_cliente = ?"
            );
            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("id"),
                    rs.getString("total"),
                    rs.getString("fecha")
                });
            }

            cn.close();
        } catch (Exception e) {
            System.out.println("Error ventas por cliente: " + e);
        }

        return lista;
    }

    // 4. Productos más vendidos
    public ArrayList<String[]> productosVendidos() {
        ArrayList<String[]> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "SELECT p.nombre, SUM(dv.cantidad) AS total_vendido " +
                "FROM detalle_venta dv " +
                "JOIN producto p ON dv.id_producto = p.id " +
                "GROUP BY p.id ORDER BY total_vendido DESC"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new String[]{
                    rs.getString("nombre"),
                    rs.getString("total_vendido")
                });
            }

            cn.close();
        } catch (Exception e) {
            System.out.println("Error productos vendidos: " + e);
        }

        return lista;
    }
}




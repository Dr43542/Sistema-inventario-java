package dao;

import conexion.Conexion;
import modelos.Producto;
import java.sql.*;
import java.util.ArrayList;

public class ProductoDAO {

    public void insertarProducto(String nombre, double precio, int stock) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)"
            );

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            ps.executeUpdate();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error insertar: " + e);
        }
    }

    public void actualizarProducto(int id, String nombre, double precio, int stock) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "UPDATE producto SET nombre=?, precio=?, stock=? WHERE id=?"
            );

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            ps.setInt(4, id);
            ps.executeUpdate();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error actualizar: " + e);
        }
    }

    public void eliminarProducto(int id) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "DELETE FROM producto WHERE id=?"
            );

            ps.setInt(1, id);
            ps.executeUpdate();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error eliminar: " + e);
        }
    }

    public ArrayList<Producto> listarProductos() {
        ArrayList<Producto> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM producto");
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
            System.out.println("Error listar: " + e);
        }

        return lista;
    }
}

package dao;

import conexion.Conexion;
import modelos.Cliente;
import java.sql.*;
import java.util.ArrayList;

public class ClienteDAO {

    public void insertarCliente(String nombre, String telefono) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO cliente (nombre, telefono) VALUES (?, ?)"
            );
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.executeUpdate();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error insertar: " + e);
        }
    }

    public void actualizarCliente(int id, String nombre, String telefono) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "UPDATE cliente SET nombre=?, telefono=? WHERE id=?"
            );
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setInt(3, id);
            ps.executeUpdate();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error actualizar: " + e);
        }
    }

    public void eliminarCliente(int id) {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement(
                "DELETE FROM cliente WHERE id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
            cn.close();
        } catch (Exception e) {
            System.out.println("Error eliminar: " + e);
        }
    }

    public ArrayList<Cliente> listarClientes() {
        ArrayList<Cliente> lista = new ArrayList<>();

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement("SELECT * FROM cliente");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.id = rs.getInt("id");
                c.nombre = rs.getString("nombre");
                c.telefono = rs.getString("telefono");
                lista.add(c);
            }

            cn.close();
        } catch (Exception e) {
            System.out.println("Error listar: " + e);
        }

        return lista;
    }
}

package dao;

import conexion.Conexion;
import java.sql.*;

public class VentaDAO {

    public void registrarVenta(int idCliente, int idProducto, int cantidad) {

        Connection cn = null;

        try {
            cn = Conexion.conectar();
            cn.setAutoCommit(false);

            // 1. Insertar venta (total luego)
            PreparedStatement psVenta = cn.prepareStatement(
                "INSERT INTO venta (id_cliente, total) VALUES (?, 0)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            psVenta.setInt(1, idCliente);
            psVenta.executeUpdate();

            ResultSet rsVenta = psVenta.getGeneratedKeys();
            int idVenta = rsVenta.next() ? rsVenta.getInt(1) : 0;

            // 2. Obtener precio del producto
            PreparedStatement psPrecio = cn.prepareStatement(
                "SELECT precio FROM producto WHERE id=?"
            );
            psPrecio.setInt(1, idProducto);
            ResultSet rs = psPrecio.executeQuery();

            double precio = 0;
            if (rs.next()) precio = rs.getDouble("precio");

            double subtotal = precio * cantidad;

            // 3. Insertar detalle venta
            PreparedStatement psDet = cn.prepareStatement(
                "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, subtotal) VALUES (?, ?, ?, ?)"
            );
            psDet.setInt(1, idVenta);
            psDet.setInt(2, idProducto);
            psDet.setInt(3, cantidad);
            psDet.setDouble(4, subtotal);
            psDet.executeUpdate();

            // 4. Actualizar total
            PreparedStatement psUpd = cn.prepareStatement(
                "UPDATE venta SET total=? WHERE id=?"
            );
            psUpd.setDouble(1, subtotal);
            psUpd.setInt(2, idVenta);
            psUpd.executeUpdate();

            // 5. Descontar stock
            PreparedStatement psStock = cn.prepareStatement(
                "UPDATE producto SET stock = stock - ? WHERE id=?"
            );
            psStock.setInt(1, cantidad);
            psStock.setInt(2, idProducto);
            psStock.executeUpdate();

            cn.commit();
            System.out.println("Venta registrada.");

        } catch (Exception e) {
            try {
                if (cn != null) cn.rollback();
            } catch (Exception ex) {
            }
            System.out.println("Error: " + e);
        }
    }
}

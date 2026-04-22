CREATE DATABASE tienda;
USE tienda;

CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    precio DOUBLE,
    stock INT
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(30)
);

CREATE TABLE venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    total DOUBLE,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES cliente(id)
);

CREATE TABLE detalle_venta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT,
    id_producto INT,
    cantidad INT,
    subtotal DOUBLE,
    FOREIGN KEY (id_venta) REFERENCES venta(id),
    FOREIGN KEY (id_producto) REFERENCES producto(id)
);



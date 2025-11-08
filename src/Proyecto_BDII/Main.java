package Proyecto_BDII;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    private Producto producto = new Producto();
    private Cliente cliente = new Cliente();
    private Venta venta = new Venta();

    public Main() {
        setTitle("CRUD Completo - Proyecto BDII");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();

        pestañas.addTab("Productos", crearPanelProductos());
        pestañas.addTab("Clientes", crearPanelClientes());
        pestañas.addTab("Ventas", crearPanelVentas());

        add(pestañas);
    }

    // -------------------------------------------
    // PANEL PRODUCTOS
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Producto"));

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtDesc = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();

        form.add(new JLabel("ID Producto:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Descripción:")); form.add(txtDesc);
        form.add(new JLabel("Precio:")); form.add(txtPrecio);
        form.add(new JLabel("Stock:")); form.add(txtStock);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton crear = new JButton("Crear");
        JButton leer = new JButton("Leer");
        JButton actualizar = new JButton("Actualizar");
        JButton borrar = new JButton("Borrar");

        for (JButton b : new JButton[]{crear, leer, actualizar, borrar}) {
            Estilos.aplicarEstiloBoton(b);
            botones.add(b);
        }

        JTable tabla = new JTable();
        Estilos.aplicarEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);

        // Acciones
        crear.addActionListener(e -> {
            producto.Crear(txtId.getText(), txtNombre.getText(), txtDesc.getText(),
                    Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
            producto.Leer(tabla);
        });

        leer.addActionListener(e -> producto.Leer(tabla));

        actualizar.addActionListener(e -> {
            producto.Actualizar(txtId.getText(), txtNombre.getText(), txtDesc.getText(),
                    Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
            producto.Leer(tabla);
        });

        borrar.addActionListener(e -> {
            producto.Borrar(txtId.getText());
            producto.Leer(tabla);
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.NORTH);
        norte.add(botones, BorderLayout.CENTER);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // -------------------------------------------
    // PANEL CLIENTES
    private JPanel crearPanelClientes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        JTextField txtId = new JTextField();
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();

        form.add(new JLabel("ID Cliente:")); form.add(txtId);
        form.add(new JLabel("Nombre:")); form.add(txtNombre);
        form.add(new JLabel("Apellido:")); form.add(txtApellido);
        form.add(new JLabel("Teléfono:")); form.add(txtTelefono);
        form.add(new JLabel("Dirección:")); form.add(txtDireccion);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton crear = new JButton("Crear");
        JButton leer = new JButton("Leer");
        JButton actualizar = new JButton("Actualizar");
        JButton borrar = new JButton("Borrar");

        for (JButton b : new JButton[]{crear, leer, actualizar, borrar}) {
            Estilos.aplicarEstiloBoton(b);
            botones.add(b);
        }

        JTable tabla = new JTable();
        Estilos.aplicarEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);

        // Acciones
        crear.addActionListener(e -> {
            cliente.Crear(txtId.getText(), txtNombre.getText(), txtApellido.getText(),
                    txtTelefono.getText(), txtDireccion.getText());
            cliente.Leer(tabla);
        });

        leer.addActionListener(e -> cliente.Leer(tabla));

        actualizar.addActionListener(e -> {
            cliente.Actualizar(txtId.getText(), txtNombre.getText(), txtApellido.getText(),
                    txtTelefono.getText(), txtDireccion.getText());
            cliente.Leer(tabla);
        });

        borrar.addActionListener(e -> {
            cliente.Borrar(txtId.getText());
            cliente.Leer(tabla);
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.NORTH);
        norte.add(botones, BorderLayout.CENTER);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // -------------------------------------------
    // PANEL VENTAS
    private JPanel crearPanelVentas() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Datos de la Venta"));

        JTextField txtId = new JTextField();
        JTextField txtFecha = new JTextField();
        JTextField txtTotal = new JTextField();

        form.add(new JLabel("ID Venta:")); form.add(txtId);
        form.add(new JLabel("Fecha (YYYY-MM-DD):")); form.add(txtFecha);
        form.add(new JLabel("Total:")); form.add(txtTotal);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton crear = new JButton("Crear");
        JButton leer = new JButton("Leer");
        JButton actualizar = new JButton("Actualizar");
        JButton borrar = new JButton("Borrar");

        for (JButton b : new JButton[]{crear, leer, actualizar, borrar}) {
            Estilos.aplicarEstiloBoton(b);
            botones.add(b);
        }

        JTable tabla = new JTable();
        Estilos.aplicarEstiloTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);

        // Acciones
        crear.addActionListener(e -> {
            venta.Crear(txtId.getText(), txtFecha.getText(), Double.parseDouble(txtTotal.getText()));
            venta.Leer(tabla);
        });

        leer.addActionListener(e -> venta.Leer(tabla));

        actualizar.addActionListener(e -> {
            venta.Actualizar(txtId.getText(), txtFecha.getText(), Double.parseDouble(txtTotal.getText()));
            venta.Leer(tabla);
        });

        borrar.addActionListener(e -> {
            venta.Borrar(txtId.getText());
            venta.Leer(tabla);
        });

        JPanel norte = new JPanel(new BorderLayout());
        norte.add(form, BorderLayout.NORTH);
        norte.add(botones, BorderLayout.CENTER);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // -------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

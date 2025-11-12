package Proyecto_BDII;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        // --- Al hacer clic en una fila, se cargan los datos en los campos ---
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                txtId.setText(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                txtNombre.setText(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
                txtDesc.setText(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
                txtPrecio.setText(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());
                txtStock.setText(tabla.getValueAt(tabla.getSelectedRow(), 4).toString());
            }
        });

        crear.addActionListener(e -> {
            List<String> errores = new ArrayList<>();
            List<JTextField> camposErroneos = new ArrayList<>();

            if (!esDouble(txtPrecio)) {
                errores.add("• El campo 'Precio' debe ser un número real (usa punto decimal).");
                camposErroneos.add(txtPrecio);
            }

            if (!esNumero(txtStock)) {
                errores.add("• El campo 'Stock' debe ser un número entero.");
                camposErroneos.add(txtStock);
            }

            if (!errores.isEmpty()) {
                mostrarErrores(errores, camposErroneos);
                return;
            }

            producto.Crear(txtId.getText(), txtNombre.getText(), txtDesc.getText(),
                    Double.parseDouble(txtPrecio.getText()), Integer.parseInt(txtStock.getText()));
            producto.Leer(tabla);
        });

        leer.addActionListener(e -> producto.Leer(tabla));

        actualizar.addActionListener(e -> {
            List<String> errores = new ArrayList<>();
            List<JTextField> camposErroneos = new ArrayList<>();

            if (!esDouble(txtPrecio)) {
                errores.add("• El campo 'Precio' debe ser un número real (usa punto decimal).");
                camposErroneos.add(txtPrecio);
            }

            if (!esNumero(txtStock)) {
                errores.add("• El campo 'Stock' debe ser un número entero.");
                camposErroneos.add(txtStock);
            }

            if (!errores.isEmpty()) {
                mostrarErrores(errores, camposErroneos);
                return;
            }

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

        // --- Click en tabla para llenar los campos ---
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                txtId.setText(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                txtNombre.setText(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
                txtApellido.setText(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
                txtTelefono.setText(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());
                txtDireccion.setText(tabla.getValueAt(tabla.getSelectedRow(), 4).toString());
            }
        });

        crear.addActionListener(e -> {
            List<String> errores = new ArrayList<>();
            List<JTextField> camposErroneos = new ArrayList<>();

            if (!esTelefonoValido(txtTelefono.getText())) {
                errores.add("• El teléfono debe contener exactamente 10 dígitos numéricos (0-9).");
                camposErroneos.add(txtTelefono);
            }

            if (!errores.isEmpty()) {
                mostrarErrores(errores, camposErroneos);
                return;
            }

            cliente.Crear(txtId.getText(), txtNombre.getText(), txtApellido.getText(),
                    txtTelefono.getText(), txtDireccion.getText());
            cliente.Leer(tabla);
        });

        leer.addActionListener(e -> cliente.Leer(tabla));

        actualizar.addActionListener(e -> {
            if (!esTelefonoValido(txtTelefono.getText())) {
                mostrarErrores(
                        List.of("• El teléfono debe contener exactamente 10 dígitos numéricos (0-9)."),
                        List.of(txtTelefono)
                );
                return;
            }

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

        // --- Click en tabla para llenar campos ---
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                txtId.setText(tabla.getValueAt(tabla.getSelectedRow(), 0).toString());
                txtFecha.setText(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
                txtTotal.setText(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
            }
        });

        crear.addActionListener(e -> {
            if (!esDouble(txtTotal)) {
                mostrarErrores(
                        List.of("• El campo 'Total' debe ser un número real (usa punto decimal)."),
                        List.of(txtTotal)
                );
                return;
            }
            venta.Crear(txtId.getText(), txtFecha.getText(), Double.parseDouble(txtTotal.getText()));
            venta.Leer(tabla);
        });

        leer.addActionListener(e -> venta.Leer(tabla));

        actualizar.addActionListener(e -> {
            if (!esDouble(txtTotal)) {
                mostrarErrores(
                        List.of("• El campo 'Total' debe ser un número real (usa punto decimal)."),
                        List.of(txtTotal)
                );
                return;
            }
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
    // MÉTODOS DE VALIDACIÓN Y ERRORES
    private boolean esNumero(JTextField campo) {
        try {
            Integer.parseInt(campo.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esDouble(JTextField campo) {
        try {
            Double.parseDouble(campo.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esTelefonoValido(String telefono) {
        return telefono.matches("^[0-9]{10}$");
    }

    private void mostrarErrores(List<String> mensajes, List<JTextField> campos) {
        StringBuilder sb = new StringBuilder("Se encontraron los siguientes errores:\n\n");
        for (String msg : mensajes) sb.append(msg).append("\n");

        JOptionPane.showMessageDialog(this, sb.toString(),
                "Error de validación", JOptionPane.WARNING_MESSAGE);

        for (JTextField campo : campos) parpadearCampo(campo);
    }

    private void parpadearCampo(JTextField campo) {
        Color original = campo.getBackground();
        new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    campo.setBackground(Color.RED);
                    Thread.sleep(200);
                    campo.setBackground(original);
                    Thread.sleep(200);
                }
            } catch (InterruptedException ignored) {}
        }).start();
    }

    // -------------------------------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

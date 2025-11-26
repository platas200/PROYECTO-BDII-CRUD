package Proyecto_BDII;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {

    private Producto producto = new Producto();
    private Cliente cliente = new Cliente();
    private Venta venta = new Venta();

    private JTable tablaProductos;
    private JTable tablaClientes;

    // --- Componentes carrito ---
    private JComboBox<String> comboClientes;
    private JComboBox<String> comboProductos;
    private JTextField txtPrecio;
    private JTextField txtCantidad;
    private JTable tablaCarrito;
    private JLabel lblTotal;
    private DefaultTableModel modeloCarrito;

    // --- Componentes historial ventas ---
    private JTable tablaVentasHistorial;
    private JTable tablaDetallesVenta;
    private DefaultTableModel modeloVentasHistorial;
    private DefaultTableModel modeloDetallesVenta;

    public Main() {
        setTitle("Sistema Profesional de Ventas - BDII");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // para poder abrir varias ventanas
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Productos", panelProductos());
        pestañas.addTab("Clientes", panelClientes());
        pestañas.addTab("Carrito", panelCarrito());
        pestañas.addTab("Ventas", panelVentasHistorial());

        add(pestañas);
    }

    // ------------------- PANEL PRODUCTOS -------------------
    private JPanel panelProductos() {
        JPanel panel = new JPanel(new BorderLayout());

        tablaProductos = new JTable();
        Estilos.aplicarEstiloTabla(tablaProductos);
        producto.Leer(tablaProductos);
        JScrollPane scroll = new JScrollPane(tablaProductos);

        JPanel panelBotones = new JPanel();
        JButton btnCrear = new JButton("Crear Producto");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRefrescar = new JButton("Refrescar");

        Estilos.aplicarEstiloBoton(btnCrear);
        Estilos.aplicarEstiloBoton(btnActualizar);
        Estilos.aplicarEstiloBoton(btnEliminar);
        Estilos.aplicarEstiloBoton(btnRefrescar);

        // Crear producto
        btnCrear.addActionListener(e -> {
            JTextField id = new JTextField();
            JTextField nombre = new JTextField();
            JTextField descripcion = new JTextField();
            JTextField precio = new JTextField();
            JTextField stock = new JTextField();

            precio.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    try {
                        double val = Double.parseDouble(precio.getText());
                        if(val < 0) throw new NumberFormatException();
                        precio.setBackground(Color.WHITE);
                    } catch(NumberFormatException ex) {
                        precio.setBackground(Color.PINK);
                    }
                }
            });

            stock.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    try {
                        int val = Integer.parseInt(stock.getText());
                        if(val < 0) throw new NumberFormatException();
                        stock.setBackground(Color.WHITE);
                    } catch(NumberFormatException ex) {
                        stock.setBackground(Color.PINK);
                    }
                }
            });

            Object[] campos = {
                    "ID:", id,
                    "Nombre:", nombre,
                    "Descripción:", descripcion,
                    "Precio:", precio,
                    "Stock:", stock
            };

            int opcion = JOptionPane.showConfirmDialog(null, campos, "Crear Producto", JOptionPane.OK_CANCEL_OPTION);
            if(opcion == JOptionPane.OK_OPTION){
                try {
                    double precioVal = Double.parseDouble(precio.getText());
                    int stockVal = Integer.parseInt(stock.getText());
                    if(precioVal < 0){ JOptionPane.showMessageDialog(null, "Precio inválido"); return; }
                    if(stockVal < 0){ JOptionPane.showMessageDialog(null, "Stock inválido"); return; }
                    producto.Crear(id.getText(), nombre.getText(), descripcion.getText(), precioVal, stockVal);
                    producto.Leer(tablaProductos);
                    refreshCombos();
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Precio o stock inválido.");
                }
            }
        });

        // Actualizar producto
        btnActualizar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if(fila==-1){ JOptionPane.showMessageDialog(null,"Selecciona un producto."); return; }

            String id = tablaProductos.getValueAt(fila, 0).toString();
            String nombreOld = tablaProductos.getValueAt(fila, 1).toString();
            String descOld = tablaProductos.getValueAt(fila, 2).toString();
            String precioOld = tablaProductos.getValueAt(fila, 3).toString();
            String stockOld = tablaProductos.getValueAt(fila, 4).toString();

            JTextField txtNombre = new JTextField(nombreOld);
            JTextField txtDesc = new JTextField(descOld);
            JTextField txtPrecio = new JTextField(precioOld);
            JTextField txtStock = new JTextField(stockOld);

            txtPrecio.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e) {
                    try { double val = Double.parseDouble(txtPrecio.getText()); if(val<0) throw new NumberFormatException();
                        txtPrecio.setBackground(Color.WHITE);
                    } catch(NumberFormatException ex){ txtPrecio.setBackground(Color.PINK); }
                }
            });

            txtStock.addKeyListener(new KeyAdapter() {
                public void keyReleased(KeyEvent e){
                    try { int val = Integer.parseInt(txtStock.getText()); if(val<0) throw new NumberFormatException();
                        txtStock.setBackground(Color.WHITE);
                    } catch(NumberFormatException ex){ txtStock.setBackground(Color.PINK); }
                }
            });

            Object[] campos = {
                    "Nombre:", txtNombre,
                    "Descripción:", txtDesc,
                    "Precio:", txtPrecio,
                    "Stock:", txtStock
            };

            int opcion = JOptionPane.showConfirmDialog(null, campos,"Actualizar Producto",JOptionPane.OK_CANCEL_OPTION);
            if(opcion == JOptionPane.OK_OPTION){
                try {
                    double precioVal = Double.parseDouble(txtPrecio.getText());
                    int stockVal = Integer.parseInt(txtStock.getText());
                    if(precioVal<0){ JOptionPane.showMessageDialog(null,"Precio inválido"); return; }
                    if(stockVal<0){ JOptionPane.showMessageDialog(null,"Stock inválido"); return; }
                    producto.Actualizar(id, txtNombre.getText(), txtDesc.getText(), precioVal, stockVal);
                    producto.Leer(tablaProductos);
                    refreshCombos();
                } catch(NumberFormatException ex){ JOptionPane.showMessageDialog(null,"Precio o stock inválido."); }
            }
        });

        // Eliminar producto
        btnEliminar.addActionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if(fila==-1){ JOptionPane.showMessageDialog(null,"Selecciona un producto."); return; }
            String id = tablaProductos.getValueAt(fila,0).toString();
            producto.Borrar(id);
            producto.Leer(tablaProductos);
            refreshCombos();
        });

        btnRefrescar.addActionListener(e -> producto.Leer(tablaProductos));

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    // ------------------- PANEL CLIENTES -------------------
    private JPanel panelClientes() {
        JPanel panel = new JPanel(new BorderLayout());

        tablaClientes = new JTable();
        Estilos.aplicarEstiloTabla(tablaClientes);
        cliente.Leer(tablaClientes);
        JScrollPane scroll = new JScrollPane(tablaClientes);

        JPanel panelBotones = new JPanel();
        JButton btnCrear = new JButton("Crear Cliente");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnRefrescar = new JButton("Refrescar");

        Estilos.aplicarEstiloBoton(btnCrear);
        Estilos.aplicarEstiloBoton(btnActualizar);
        Estilos.aplicarEstiloBoton(btnEliminar);
        Estilos.aplicarEstiloBoton(btnRefrescar);

        btnCrear.addActionListener(e -> {
            JTextField id = new JTextField();
            JTextField nombre = new JTextField();
            JTextField apellido = new JTextField();
            JTextField telefono = new JTextField();
            JTextField direccion = new JTextField();

            telefono.addKeyListener(new KeyAdapter(){
                public void keyReleased(KeyEvent e){
                    if(!telefono.getText().matches("\\d{0,10}")) telefono.setBackground(Color.PINK);
                    else telefono.setBackground(Color.WHITE);
                }
            });

            Object[] campos = {
                    "ID:", id,
                    "Nombre:", nombre,
                    "Apellido:", apellido,
                    "Teléfono:", telefono,
                    "Dirección:", direccion
            };

            int opcion = JOptionPane.showConfirmDialog(null, campos,"Crear Cliente",JOptionPane.OK_CANCEL_OPTION);
            if(opcion==JOptionPane.OK_OPTION){
                if(!telefono.getText().matches("\\d{10}")){
                    JOptionPane.showMessageDialog(null,"Teléfono inválido, debe tener 10 dígitos.");
                    return;
                }
                cliente.Crear(id.getText(), nombre.getText(), apellido.getText(), telefono.getText(), direccion.getText());
                cliente.Leer(tablaClientes);
                refreshCombos();
            }
        });

        btnActualizar.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if(fila==-1){ JOptionPane.showMessageDialog(null,"Selecciona un cliente."); return; }

            String id = tablaClientes.getValueAt(fila,0).toString();
            JTextField txtNombre = new JTextField(tablaClientes.getValueAt(fila,1).toString());
            JTextField txtApellido = new JTextField(tablaClientes.getValueAt(fila,2).toString());
            JTextField txtTelefono = new JTextField(tablaClientes.getValueAt(fila,3).toString());
            JTextField txtDireccion = new JTextField(tablaClientes.getValueAt(fila,4).toString());

            txtTelefono.addKeyListener(new KeyAdapter(){
                public void keyReleased(KeyEvent e){
                    if(!txtTelefono.getText().matches("\\d{0,10}")) txtTelefono.setBackground(Color.PINK);
                    else txtTelefono.setBackground(Color.WHITE);
                }
            });

            Object[] campos = {
                    "Nombre:", txtNombre,
                    "Apellido:", txtApellido,
                    "Teléfono:", txtTelefono,
                    "Dirección:", txtDireccion
            };

            int opcion = JOptionPane.showConfirmDialog(null, campos,"Actualizar Cliente",JOptionPane.OK_CANCEL_OPTION);
            if(opcion == JOptionPane.OK_OPTION){
                if(!txtTelefono.getText().matches("\\d{10}")){
                    JOptionPane.showMessageDialog(null,"Teléfono inválido, debe tener 10 dígitos.");
                    return;
                }
                cliente.Actualizar(id, txtNombre.getText(), txtApellido.getText(), txtTelefono.getText(), txtDireccion.getText());
                cliente.Leer(tablaClientes);
                refreshCombos();
            }
        });

        btnEliminar.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if(fila==-1){ JOptionPane.showMessageDialog(null,"Selecciona un cliente."); return; }
            String id = tablaClientes.getValueAt(fila,0).toString();
            cliente.Borrar(id);
            cliente.Leer(tablaClientes);
            refreshCombos();
        });

        btnRefrescar.addActionListener(e -> cliente.Leer(tablaClientes));

        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnRefrescar);

        panel.add(scroll, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);

        return panel;
    }

    // ------------------- PANEL CARRITO -------------------
    private JPanel panelCarrito() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel pnlSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        comboClientes = new JComboBox<>();
        comboProductos = new JComboBox<>();
        txtPrecio = new JTextField(7); txtPrecio.setEditable(false);
        txtCantidad = new JTextField(3);

        refreshCombos();

        comboProductos.addActionListener(e -> {
            if(comboProductos.getSelectedItem()!=null){
                String idProd = extractIdFromComboItem(comboProductos.getSelectedItem().toString());
                double precioVal = producto.getPrecio(idProd);
                txtPrecio.setText(String.valueOf(precioVal));
            }
        });

        JButton btnAgregar = new JButton("Agregar");
        Estilos.aplicarEstiloBoton(btnAgregar);
        btnAgregar.addActionListener(e -> agregarAlCarrito());

        pnlSeleccion.add(new JLabel("Cliente:")); pnlSeleccion.add(comboClientes);
        pnlSeleccion.add(new JLabel("Producto:")); pnlSeleccion.add(comboProductos);
        pnlSeleccion.add(new JLabel("Precio:")); pnlSeleccion.add(txtPrecio);
        pnlSeleccion.add(new JLabel("Cantidad:")); pnlSeleccion.add(txtCantidad);
        pnlSeleccion.add(btnAgregar);

        String[] columnas = {"ID Producto","Nombre","Precio Unitario","Cantidad","Subtotal"};
        modeloCarrito = new DefaultTableModel(columnas,0);
        tablaCarrito = new JTable(modeloCarrito);
        Estilos.aplicarEstiloTabla(tablaCarrito);
        JScrollPane scroll = new JScrollPane(tablaCarrito);

        JButton btnEliminar = new JButton("Eliminar seleccionado");
        Estilos.aplicarEstiloBoton(btnEliminar);
        btnEliminar.addActionListener(e -> {
            int fila = tablaCarrito.getSelectedRow();
            if(fila!=-1){
                modeloCarrito.removeRow(fila);
                actualizarTotal();
            }
        });

        JPanel pnlInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotal = new JLabel("TOTAL: $0.0");
        JButton btnVender = new JButton("Finalizar Venta");
        Estilos.aplicarEstiloBoton(btnVender);

        // ---------- NUEVO: SwingWorker para no bloquear GUI ----------
        btnVender.addActionListener(e -> {
            if(modeloCarrito.getRowCount()==0){
                JOptionPane.showMessageDialog(this,"El carrito está vacío.");
                return;
            }
            if(comboClientes.getSelectedItem()==null){
                JOptionPane.showMessageDialog(this,"Selecciona un cliente.");
                return;
            }

            // Construir listas
            List<String> productosList = new ArrayList<>();
            List<Integer> cantidadesList = new ArrayList<>();
            for(int i=0;i<modeloCarrito.getRowCount();i++){
                String idProd = modeloCarrito.getValueAt(i,0).toString();
                String nombre = modeloCarrito.getValueAt(i,1).toString();
                productosList.add(idProd + " - " + nombre);
                int cantidad = Integer.parseInt(modeloCarrito.getValueAt(i,3).toString());
                cantidadesList.add(cantidad);
            }
            String clienteVal = comboClientes.getSelectedItem().toString();

            // Thread-safe con SwingWorker
            SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    return venta.realizarVenta(clienteVal, clienteVal, productosList, cantidadesList);
                }

                @Override
                protected void done() {
                    try {
                        boolean exito = get();
                        if(exito){
                            modeloCarrito.setRowCount(0);
                            actualizarTotal();
                            refreshCombos();
                            producto.Leer(tablaProductos);
                            cargarVentasEnTabla();
                            JOptionPane.showMessageDialog(null,"Venta finalizada correctamente.");
                        } else {
                            JOptionPane.showMessageDialog(null,"No se pudo finalizar la venta. Revisa los errores.");
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null,"Error finalizando venta: " + ex.getMessage());
                    }
                }
            };
            worker.execute();
        });

        JButton btnTodos = new JButton("Todos los productos");
        Estilos.aplicarEstiloBoton(btnTodos);
        btnTodos.addActionListener(e -> mostrarTodosProductos());

        pnlInferior.add(btnTodos); pnlInferior.add(lblTotal); pnlInferior.add(btnVender); pnlInferior.add(btnEliminar);

        panel.add(pnlSeleccion,BorderLayout.NORTH);
        panel.add(scroll,BorderLayout.CENTER);
        panel.add(pnlInferior,BorderLayout.SOUTH);

        return panel;
    }

    // ------------------- PANEL HISTORIAL VENTAS -------------------
    private JPanel panelVentasHistorial() {
        JPanel panel = new JPanel(new BorderLayout());

        // --- Tabla de ventas ---
        String[] columnasVentas = {"ID Venta", "Fecha", "Total"};
        modeloVentasHistorial = new DefaultTableModel(columnasVentas,0){
            @Override
            public boolean isCellEditable(int row,int col){ return false; }
        };
        tablaVentasHistorial = new JTable(modeloVentasHistorial);
        Estilos.aplicarEstiloTabla(tablaVentasHistorial);
        JScrollPane scrollVentas = new JScrollPane(tablaVentasHistorial);

        // --- Tabla de detalles ---
        String[] columnasDetalles = {"ID Detalle","ID Producto","Nombre Producto","Cantidad","Precio Unitario","Subtotal"};
        modeloDetallesVenta = new DefaultTableModel(columnasDetalles,0){
            @Override
            public boolean isCellEditable(int row,int col){ return false; }
        };
        tablaDetallesVenta = new JTable(modeloDetallesVenta);
        Estilos.aplicarEstiloTabla(tablaDetallesVenta);
        JScrollPane scrollDetalles = new JScrollPane(tablaDetallesVenta);
        scrollDetalles.setPreferredSize(new Dimension(100,180));

        // --- Botones ---
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefrescar = new JButton("Refrescar");
        Estilos.aplicarEstiloBoton(btnRefrescar);
        btnRefrescar.addActionListener(e -> cargarVentasEnTabla());
        pnlBotones.add(btnRefrescar);

        // Botón eliminar venta
        JButton btnEliminarVenta = new JButton("Eliminar Venta");
        Estilos.aplicarEstiloBoton(btnEliminarVenta);
        btnEliminarVenta.addActionListener(e -> eliminarVentaSeleccionada());
        pnlBotones.add(btnEliminarVenta);

        panel.add(pnlBotones, BorderLayout.NORTH);

        // --- Panel central con tablas ---
        JPanel centro = new JPanel(new BorderLayout());
        centro.add(scrollVentas, BorderLayout.CENTER);
        centro.add(scrollDetalles, BorderLayout.SOUTH);

        panel.add(centro, BorderLayout.CENTER);

        // Cargar ventas al inicio
        cargarVentasEnTabla();

        // Mostrar detalles al seleccionar una venta
        tablaVentasHistorial.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                int fila = tablaVentasHistorial.getSelectedRow();
                if(fila!=-1){
                    String idVenta = tablaVentasHistorial.getValueAt(fila,0).toString();
                    cargarDetallesVenta(idVenta);
                }
            }
        });

        return panel;
    }

    // ------------------- FUNCIONES AUXILIARES -------------------
    private void agregarAlCarrito(){
        if(comboProductos.getSelectedItem()==null) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto.");
            return;
        }

        String[] partes = comboProductos.getSelectedItem().toString().split(" - ", 2);
        String idProd = partes[0];
        String nombre = partes.length > 1 ? partes[1] : partes[0];

        double precio = producto.getPrecio(idProd);
        int stock = producto.getStock(idProd);

        int cantidad;
        try{
            cantidad = Integer.parseInt(txtCantidad.getText());
            if(cantidad<=0) throw new NumberFormatException();
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Cantidad inválida");
            return;
        }

        if(cantidad>stock){
            JOptionPane.showMessageDialog(this,"Stock insuficiente. Disponible: "+stock);
            return;
        }

        double subtotal = precio*cantidad;
        modeloCarrito.addRow(new Object[]{idProd,nombre,precio,cantidad,subtotal});
        actualizarTotal();
    }

    private void actualizarTotal(){
        double total=0;
        for(int i=0;i<modeloCarrito.getRowCount();i++){
            Object val = modeloCarrito.getValueAt(i,4);
            if(val!=null){
                total+= Double.parseDouble(val.toString());
            }
        }
        lblTotal.setText("TOTAL: $"+total);
    }

    private void mostrarTodosProductos(){
        JTable tabla = new JTable();
        producto.Leer(tabla);
        Estilos.aplicarEstiloTabla(tabla);
        JOptionPane.showMessageDialog(this,new JScrollPane(tabla),"Todos los productos",JOptionPane.PLAIN_MESSAGE);
    }

    private void cargarVentasEnTabla(){
        modeloVentasHistorial.setRowCount(0);
        try(Connection con = Conexion.getConexion();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT IDVENTA, FECHAVENTA, TOTAL FROM VENTA " +
                                           "ORDER BY CAST(SUBSTRING(IDVENTA, 2) AS UNSIGNED) ASC")){

            while(rs.next()){
                Object fechaObj = rs.getDate("FECHAVENTA");
                Object totalObj = rs.getDouble("TOTAL");
                modeloVentasHistorial.addRow(new Object[]{
                        rs.getString("IDVENTA"),
                        fechaObj != null ? fechaObj.toString() : "",
                        totalObj
                });
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error cargando ventas: " + e.getMessage());
        }
    }

    private void cargarDetallesVenta(String idVenta){
        modeloDetallesVenta.setRowCount(0);
        String sql = "SELECT D.IDDETALLE, D.IDPRODUCTO, P.NOMBREPRODUCTO, D.CANTIDAD, D.PRECIO_UNITARIO, D.SUBTOTAL " +
                     "FROM DETALLEVENTA D " +
                     "JOIN PRODUCTO P ON D.IDPRODUCTO = P.IDPRODUCTO " +
                     "WHERE D.IDVENTA = ?";
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, idVenta);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    modeloDetallesVenta.addRow(new Object[]{
                            rs.getString("IDDETALLE"),
                            rs.getString("IDPRODUCTO"),
                            rs.getString("NOMBREPRODUCTO"),
                            rs.getInt("CANTIDAD"),
                            rs.getDouble("PRECIO_UNITARIO"),
                            rs.getDouble("SUBTOTAL")
                    });
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Error cargando detalles: " + e.getMessage());
        }
    }

    private void refreshCombos(){
        // Clientes
        comboClientes.removeAllItems();
        List<String> listaClientes = cliente.obtenerListaClientes();
        for(String c : listaClientes) comboClientes.addItem(c);

        // Productos
        comboProductos.removeAllItems();
        List<String> listaProductos = producto.obtenerListaProductos();
        for(String p : listaProductos) comboProductos.addItem(p);
    }

    private String extractIdFromComboItem(String comboValue) {
        if (comboValue == null) return "";
        if (comboValue.contains(" - ")) return comboValue.split(" - ")[0].trim();
        return comboValue.trim();
    }

    // ------------------- ELIMINAR VENTA SELECCIONADA -------------------
    private void eliminarVentaSeleccionada() {
        int fila = tablaVentasHistorial.getSelectedRow();
        if(fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una venta para eliminar.");
            return;
        }

        String idVenta = tablaVentasHistorial.getValueAt(fila, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Estás seguro de eliminar la venta " + idVenta + "?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if(confirm != JOptionPane.YES_OPTION) return;

        // Primero eliminamos los detalles de la venta
        String sqlDetalles = "DELETE FROM DETALLEVENTA WHERE IDVENTA = ?";
        String sqlVenta = "DELETE FROM VENTA WHERE IDVENTA = ?";

        try(Connection con = Conexion.getConexion();
            PreparedStatement psDetalles = con.prepareStatement(sqlDetalles);
            PreparedStatement psVenta = con.prepareStatement(sqlVenta)) {

            psDetalles.setString(1, idVenta);
            psDetalles.executeUpdate();

            psVenta.setString(1, idVenta);
            psVenta.executeUpdate();

            cargarVentasEnTabla();
            modeloDetallesVenta.setRowCount(0); // limpiar tabla de detalles
            JOptionPane.showMessageDialog(this, "Venta eliminada correctamente.");

        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error eliminando venta: " + e.getMessage());
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }

}

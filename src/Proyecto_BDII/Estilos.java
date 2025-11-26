package Proyecto_BDII;

import java.awt.*;
import javax.swing.*;

public class Estilos {
    public static Font fuenteBoton = new Font("Arial", Font.BOLD, 14);
    public static Color colorBoton = new Color(139, 0, 0);
    public static Color textoBlanco = Color.WHITE;

    public static void aplicarEstiloBoton(JButton boton) {
        boton.setFont(fuenteBoton);
        boton.setBackground(colorBoton);
        boton.setForeground(textoBlanco);
        boton.setFocusPainted(false);
    }

    public static void aplicarEstiloTabla(JTable tabla) {
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(25);
        tabla.setShowGrid(true);
        tabla.setGridColor(Color.LIGHT_GRAY);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
    }
}

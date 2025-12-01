# Proyecto BDII - CRUD

Este repositorio contiene el proyecto de BDII con gestión de Productos, Clientes y Ventas.

## Descripción
El proyecto permite:
- Crear, leer, actualizar y eliminar **Productos** y **Clientes**.
- Gestionar un **Carrito de ventas** y finalizar ventas.
- Controlar la **integridad de datos** y la **concurrencia** en las ventas.

## Sprint 2
- Implementación de transacciones **ACID** para garantizar integridad de datos.
- Bloqueo de filas en stock (`SELECT ... FOR UPDATE`) para evitar condiciones de carrera.
- Manejo de errores y rollback automático en caso de fallo durante la venta.
- **Reporte del Sprint 2:** [Google Docs](https://docs.google.com/document/d/1TU0np6JLTZzIjk5Bfh_nFlX5Gfq2TUVb3VCzAI-cdNk/edit?usp=sharing)

## Base de Datos
La base de datos se encuentra en la carpeta `BaseDeDatos`:
- `proyecto_bdii_SPRINT_2.sql` contiene la estructura y los datos actualizados.

## Cómo ejecutar
1. Abrir el proyecto en tu IDE Java (ej. IntelliJ, Eclipse, NetBeans).
2. Configurar la conexión a MySQL (`Conexion.java`).
3. Ejecutar `Main.java`.

## 🎥 Video de demostración
Puedes ver el video de evidencia de concurrencia aquí:
👉 [Ver video en Google Drive](https://drive.google.com/file/d/1eDfSyDiNhpqNJA2J8uhdS7FrNZeRCiyB/view?usp=sharing)

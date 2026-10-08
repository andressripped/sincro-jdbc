package co.edu.sena.sincro;

import co.edu.sena.sincro.conexion.ConexionBD;
import co.edu.sena.sincro.dao.ClienteDAO;
import co.edu.sena.sincro.modelo.Cliente;

import java.util.List;
import java.util.Scanner;

/**
 * Modulo de gestion de clientes del sistema Sincro.
 * Menu por consola con las operaciones de insertar, consultar,
 * actualizar y eliminar sobre la base de datos MySQL.
 */
public class Main {

    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("=========================================");
        System.out.println("   SINCRO - Gestion de clientes");
        System.out.println("=========================================");

        if (!ConexionBD.probarConexion()) {
            System.out.println("No se pudo conectar a la base de datos.");
            System.out.println("Revisa que MySQL este encendido y que los datos");
            System.out.println("de ConexionBD.java sean los correctos.");
            return;
        }

        System.out.println("Conectado a la base de datos sincro\n");

        boolean seguir = true;
        while (seguir) {
            mostrarMenu();
            String opcion = teclado.nextLine().trim();

            switch (opcion) {
                case "1" -> registrarCliente();
                case "2" -> listarClientes();
                case "3" -> buscarCliente();
                case "4" -> actualizarCliente();
                case "5" -> eliminarCliente();
                case "0" -> {
                    System.out.println("\nHasta luego.");
                    seguir = false;
                }
                default -> System.out.println("Opcion no valida.\n");
            }
        }
        teclado.close();
    }

    private static void mostrarMenu() {
        System.out.println("-----------------------------------------");
        System.out.println("  1. Registrar cliente");
        System.out.println("  2. Listar todos los clientes");
        System.out.println("  3. Buscar cliente por id");
        System.out.println("  4. Actualizar cliente");
        System.out.println("  5. Eliminar cliente");
        System.out.println("  0. Salir");
        System.out.println("-----------------------------------------");
        System.out.print("Opcion: ");
    }

    private static void registrarCliente() {
        System.out.println("\n--- Registrar cliente ---");

        System.out.print("Nombre completo: ");
        String nombre = teclado.nextLine().trim();

        System.out.print("Telefono: ");
        String telefono = teclado.nextLine().trim();

        System.out.print("Correo: ");
        String correo = teclado.nextLine().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            System.out.println("Todos los campos son obligatorios.\n");
            return;
        }

        Cliente cliente = new Cliente(nombre, telefono, correo);

        if (clienteDAO.insertar(cliente)) {
            System.out.println("Cliente registrado con id " + cliente.getIdCliente() + "\n");
        } else {
            System.out.println("No se pudo registrar el cliente.\n");
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- Clientes registrados ---");

        List<Cliente> clientes = clienteDAO.consultarTodos();

        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.\n");
            return;
        }

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
        System.out.println("Total: " + clientes.size() + " clientes\n");
    }

    private static void buscarCliente() {
        System.out.println("\n--- Buscar cliente ---");
        System.out.print("Id del cliente: ");

        int id = leerEntero();
        if (id < 0) return;

        Cliente cliente = clienteDAO.consultarPorId(id);

        if (cliente != null) {
            System.out.println(cliente + "\n");
        } else {
            System.out.println("No existe un cliente con ese id.\n");
        }
    }

    private static void actualizarCliente() {
        System.out.println("\n--- Actualizar cliente ---");
        System.out.print("Id del cliente: ");

        int id = leerEntero();
        if (id < 0) return;

        Cliente cliente = clienteDAO.consultarPorId(id);

        if (cliente == null) {
            System.out.println("No existe un cliente con ese id.\n");
            return;
        }

        System.out.println("Datos actuales: " + cliente);
        System.out.println("Deja vacio lo que no quieras cambiar.");

        System.out.print("Nuevo nombre: ");
        String nombre = teclado.nextLine().trim();
        if (!nombre.isEmpty()) {
            cliente.setNombreCompleto(nombre);
        }

        System.out.print("Nuevo telefono: ");
        String telefono = teclado.nextLine().trim();
        if (!telefono.isEmpty()) {
            cliente.setTelefonoContacto(telefono);
        }

        System.out.print("Nuevo correo: ");
        String correo = teclado.nextLine().trim();
        if (!correo.isEmpty()) {
            cliente.setEmailUsuario(correo);
        }

        if (clienteDAO.actualizar(cliente)) {
            System.out.println("Cliente actualizado.\n");
        } else {
            System.out.println("No se pudo actualizar.\n");
        }
    }

    private static void eliminarCliente() {
        System.out.println("\n--- Eliminar cliente ---");
        System.out.print("Id del cliente: ");

        int id = leerEntero();
        if (id < 0) return;

        Cliente cliente = clienteDAO.consultarPorId(id);

        if (cliente == null) {
            System.out.println("No existe un cliente con ese id.\n");
            return;
        }

        System.out.println("Vas a eliminar: " + cliente);
        System.out.print("Confirmas? (s/n): ");

        if (teclado.nextLine().trim().equalsIgnoreCase("s")) {
            if (clienteDAO.eliminar(id)) {
                System.out.println("Cliente eliminado.\n");
            } else {
                System.out.println("No se pudo eliminar.\n");
            }
        } else {
            System.out.println("Cancelado.\n");
        }
    }

    // lee un numero y avisa si el usuario escribio otra cosa
    private static int leerEntero() {
        try {
            return Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Debes escribir un numero.\n");
            return -1;
        }
    }
}

# Sincro - Modulo de gestion de clientes

Modulo en Java con conexion a MySQL mediante JDBC.
Proyecto formativo Sincro, sistema de gestion de reservas para comercios de servicios.

Evidencia GA7-220501096-AA2-EV01
Julian Andres Albarracin Martinez - Ficha 3336228

## Estructura

```
src/co/edu/sena/sincro/
├── Main.java                  menu por consola
├── modelo/Cliente.java        representa la tabla t_cliente
├── conexion/ConexionBD.java   conexion con MySQL
└── dao/ClienteDAO.java        operaciones sobre la base
```

## Funcionalidades

| Operacion | Metodo | SQL |
|---|---|---|
| Insertar | `insertar()` | INSERT |
| Consultar todos | `consultarTodos()` | SELECT |
| Consultar por id | `consultarPorId()` | SELECT WHERE |
| Actualizar | `actualizar()` | UPDATE |
| Eliminar | `eliminar()` | DELETE |

## Requisitos

- JDK 21
- MySQL 8.0 con la base de datos `sincro`
- MySQL Connector/J (incluido en la carpeta lib)

## Configuracion

Antes de ejecutar, revisar los datos de conexion en
`src/co/edu/sena/sincro/conexion/ConexionBD.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/sincro";
private static final String USUARIO = "root";
private static final String CLAVE = "1234";
```

## Estandares de codificacion aplicados

- Clases en PascalCase: `Cliente`, `ClienteDAO`, `ConexionBD`
- Metodos y variables en camelCase: `consultarPorId`, `nombreCompleto`
- Paquetes en minuscula: `co.edu.sena.sincro.modelo`
- Constantes en mayuscula: `URL`, `USUARIO`, `CLAVE`
- Atributos privados con metodos get y set

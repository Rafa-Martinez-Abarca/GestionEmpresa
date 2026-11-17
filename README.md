# Gestión de una empresa de desarrollo de software

Proyecto de aprendizaje en Java 21 que combina un modelo de clientes, empleados, proyectos y tareas con una aplicación de consola conectada a PostgreSQL mediante JDBC.

La aplicación permite gestionar clientes y proyectos, trabajar con presupuestos usando `BigDecimal` y consultar información relacionada. Incluye pruebas automatizadas con JUnit para comprobar tanto la lógica en memoria como el acceso real a una base de datos de pruebas.

## Qué hace el proyecto

El menú de `PruebaConexion` ofrece 14 opciones, además de salir:

- Buscar clientes por id o por nombre.
- Consultar clientes sin proyectos o con un mínimo de proyectos, también filtrando por nombre.
- Insertar clientes y recuperar su id generado.
- Actualizar el email de un cliente y eliminar clientes con confirmación.
- Insertar proyectos asociados a clientes existentes y recuperar su id generado.
- Consultar un proyecto, su presupuesto y los proyectos de un cliente.
- Actualizar presupuestos y calcular el presupuesto total de los proyectos de un cliente.

Se validan las entradas de consola y se gestionan errores como emails duplicados o intentos de eliminar clientes que tienen proyectos asociados.

El modelo original también permite gestionar empleados, equipos, tareas y estados, y obtener estadísticas en memoria. Estas funciones se muestran en `Main`; no todas están integradas en el menú JDBC ni persistidas en PostgreSQL. El proyecto utiliza una interfaz de consola, sin interfaz web.

## Tecnologías y organización

- Java 21 y Maven.
- PostgreSQL y JDBC (driver 42.7.14).
- JUnit Jupiter 5.11.4 y Maven Surefire 3.3.1.
- Programación orientada a objetos, colecciones, excepciones y transacciones.

| Archivo o carpeta | Responsabilidad |
| --- | --- |
| `src/main/java/PruebaConexion.java` | Menú de consola y presentación de resultados. |
| `src/main/java/EntradaConsola.java` | Lectura y validación de entradas. |
| `src/main/java/ClienteDAO.java`, `ProyectoDAO.java` | Consultas y modificaciones mediante SQL parametrizado. |
| `src/main/java/ClienteService.java`, `ProyectoService.java` | Coordinación de operaciones, transacciones y cálculos. |
| `src/main/java/Cliente.java`, `Proyecto.java` y demás modelos | Objetos y reglas del dominio. |
| `src/main/java/Main.java` | Demostración del modelo en memoria. |
| `src/test/java/` | Pruebas unitarias y de integración. |
| `sql/01_tablas.sql` | Tablas de la aplicación y sus restricciones. |
| `sql/02_datos.sql` | Datos de ejemplo para la aplicación. |
| `sql/03_consultas.sql` | Consultas de práctica. |
| `sql/02_preparar_tests.sql` | Tabla y permisos necesarios para las pruebas de integración. |

Los DAO reciben una conexión: la base sobre la que trabajan depende de esa conexión. La aplicación usa `empresa_pruebas` y las pruebas de integración usan `empresa_test`.

## Requisitos

- JDK 21.
- Maven instalado.
- PostgreSQL arrancado y accesible en `localhost:5432`.
- Cliente `psql` y acceso administrativo para preparar las bases.

Los comandos siguientes están pensados para Bash en Linux, desde la raíz del repositorio. Las URL y el usuario están actualmente definidos en `PruebaConexion` y `ClienteDAOTest`; la contraseña se obtiene de la variable de entorno `DB_PASSWORD`.

## Preparar la base de la aplicación

Estos pasos son para una instalación nueva. Si ya existen el usuario, las bases o las tablas, no repitas sus comandos de creación. Los scripts no eliminan ni recrean automáticamente las tablas existentes.

### 1. Crear el usuario y la base

Abre PostgreSQL como administrador:

```bash
sudo -u postgres psql
```

Dentro de `psql`, crea el usuario si todavía no existe y establece su contraseña de forma interactiva:

```sql
CREATE ROLE gestion_app LOGIN;
\password gestion_app
CREATE DATABASE empresa_pruebas;
\q
```

`gestion_app` es el usuario de la aplicación. No necesita ser superusuario.

### 2. Crear las tablas y cargar ejemplos

Desde la raíz del repositorio:

```bash
sudo -u postgres psql -v ON_ERROR_STOP=1 -d empresa_pruebas < sql/01_tablas.sql
sudo -u postgres psql -v ON_ERROR_STOP=1 -d empresa_pruebas < sql/02_datos.sql
```

La redirección permite que tu terminal lea los archivos aunque el usuario del sistema `postgres` no tenga acceso a tu carpeta personal.

Los datos de ejemplo incluyen ids explícitos. Después de cargarlos, entra en la base:

```bash
sudo -u postgres psql -d empresa_pruebas
```

Ajusta las secuencias para que las siguientes inserciones generen ids posteriores a los existentes:

```sql
SELECT setval(pg_get_serial_sequence('public.cliente', 'id'),
              COALESCE(MAX(id), 0) + 1, false) FROM public.cliente;
SELECT setval(pg_get_serial_sequence('public.empleado', 'id'),
              COALESCE(MAX(id), 0) + 1, false) FROM public.empleado;
SELECT setval(pg_get_serial_sequence('public.proyecto', 'id'),
              COALESCE(MAX(id), 0) + 1, false) FROM public.proyecto;
```

Concede los permisos que necesita el menú:

```sql
GRANT CONNECT ON DATABASE empresa_pruebas TO gestion_app;
GRANT USAGE ON SCHEMA public TO gestion_app;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLE public.cliente TO gestion_app;
GRANT SELECT, INSERT, UPDATE ON TABLE public.proyecto TO gestion_app;
GRANT USAGE ON SEQUENCE public.cliente_id_seq, public.proyecto_id_seq TO gestion_app;
\q
```

`public` es el nombre del esquema que contiene las tablas; no significa que estos permisos se concedan a cualquiera. Aquí se conceden al usuario `gestion_app`.

## Ejecutar la aplicación

En el mismo terminal desde el que ejecutarás Java o Maven, configura la contraseña del usuario `gestion_app`:

```bash
read -s -p "Contraseña de gestion_app: " DB_PASSWORD
echo
export DB_PASSWORD
```

La contraseña no se muestra al escribirla ni se guarda en el código. Si abres otro terminal, tendrás que configurar allí la variable también.

Compila y arranca el menú:

```bash
mvn compile
java -cp "target/classes:$HOME/.m2/repository/org/postgresql/postgresql/42.7.14/postgresql-42.7.14.jar" PruebaConexion
```

Para ejecutar únicamente la demostración original en memoria, sin PostgreSQL:

```bash
mvn compile
java -cp target/classes Main
```

`Main` incluye ejemplos que provocan errores intencionadamente para demostrar las validaciones del modelo.

## Pruebas automatizadas

Actualmente hay **10 pruebas**: 6 unitarias y 4 de integración.

### Pruebas unitarias: `ProyectoTest`

No necesitan PostgreSQL. Comprueban:

- Rechazo de presupuestos negativos.
- Conservación de presupuestos positivos y de valor cero.
- Suma de presupuestos enteros y decimales.
- Resultado cero para una lista vacía.

Para ejecutar solo estas pruebas:

```bash
mvn -Dtest=ProyectoTest test
```

### Preparar `empresa_test`

Las pruebas de `ClienteDAOTest` utilizan una base separada. El usuario `gestion_app` debe existir previamente, como se explica en la preparación de la aplicación.

Crea la base vacía como administrador:

```bash
sudo -u postgres psql
```

```sql
CREATE DATABASE empresa_test;
\q
```

Después, desde la raíz del repositorio:

```bash
sudo -u postgres psql -v ON_ERROR_STOP=1 -d empresa_test < sql/02_preparar_tests.sql
```

El script crea la tabla `cliente` y concede los permisos de conexión, acceso al esquema, consulta, inserción, actualización y uso de su secuencia. No crea la base ni el usuario. No cargues `sql/02_datos.sql` en esta base: las pruebas preparan sus propios clientes.

### Pruebas de integración: `ClienteDAOTest`

Necesitan PostgreSQL, `empresa_test` preparada y `DB_PASSWORD` configurada. Comprueban:

- Inserción y recuperación de un cliente con sus datos.
- Rechazo de un email duplicado y código SQL `23505`.
- Actualización del email y número de filas afectadas.
- Cero filas actualizadas cuando el cliente no existe.

Cada prueba abre su conexión, desactiva el autocommit y hace `rollback` en un `finally`. Así deshace sus cambios incluso cuando falla una comprobación; la conexión se cierra mediante `try` con recursos. Las secuencias de ids pueden avanzar aunque se deshagan las inserciones, por lo que los ids no tienen por qué ser consecutivos.

La prueba de cliente inexistente usa actualmente el id `12`: requiere que ese id no exista en `empresa_test`. Mantén esta base dedicada a las pruebas, sin introducir datos manuales.

Para ejecutar solo las pruebas del DAO:

```bash
mvn -Dtest=ClienteDAOTest test
```

Para ejecutar todas:

```bash
mvn test
```

El resultado esperado es:

```text
Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

`mvn test` ya incluye la compilación: no es necesario ejecutar antes `mvn compile`. Las pruebas no arrancan el menú de la aplicación.

## Consultar fallos

- **`Failures`**: una comprobación no obtuvo el resultado esperado.
- **`Errors`**: ocurrió un error inesperado, como un fallo de conexión o permisos insuficientes.
- **`Skipped`**: pruebas que no se ejecutaron.

La causa concreta suele aparecer antes del resumen final. Maven guarda también los informes en `target/surefire-reports/`. Por ejemplo:

```bash
cat target/surefire-reports/ClienteDAOTest.txt
```

Si aparece que no se proporcionó contraseña, configura `DB_PASSWORD` en el terminal que ejecuta Maven o Java. Si aparece `permission denied for table cliente`, comprueba los permisos en la base correspondiente: los de `empresa_pruebas` no conceden acceso a las tablas de `empresa_test`.

## Estado del proyecto

Proyecto educativo en evolución. La versión actual trabaja con consola, JDBC y pruebas JUnit. Spring Boot y una interfaz web todavía no forman parte de la implementación.

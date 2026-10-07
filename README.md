# Gestión de una empresa de desarrollo de software

Proyecto de práctica en Java para gestionar clientes, empleados,
proyectos y tareas. Incluye reglas de negocio, consultas y estadísticas.

Los datos se guardan en memoria y las pruebas se ejecutan desde Main,
sin interfaz gráfica ni base de datos.

## Funcionalidades

- Registrar y buscar clientes, empleados y proyectos por id.
- Añadir y eliminar empleados de los equipos de los proyectos.
- Crear tareas, asignar responsables y cambiar sus estados.
- Gestionar los estados de los proyectos.
- Validar reglas de negocio y gestionar errores mediante excepciones.
- Consultar proyectos por cliente y tareas por empleado o estado.
- Calcular horas trabajadas y presupuestos.
- Identificar empleados sin proyecto y empleados con más tareas completadas.

## Tecnologías

- Java 21.
- Git y GitHub para control de versiones y publicación.
- Visual Studio Code como editor.

## Conceptos aplicados

- Programación orientada a objetos: herencia, polimorfismo y composición.
- Clases abstractas e interfaces.
- Colecciones: ArrayList, HashMap y HashSet.
- Genéricos mediante la clase Registro `Registro<T>`

## Cómo ejecutarlo

Requiere JDK 21. Desde la carpeta raíz del proyecto:

```bash
mkdir -p bin
javac -encoding UTF-8 -d bin src/*.java
java -cp bin Main
```

Main muestra consultas y pruebas manuales. Los mensajes de errores
provocados intencionadamente forman parte de las pruebas.
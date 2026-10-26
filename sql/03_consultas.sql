-- Muestra los proyectos y sus empleados, incluidos los proyectos sin empleados.
SELECT empleado.nombre AS empleado, proyecto.nombre AS proyecto
FROM proyecto
LEFT JOIN proyecto_empleado ON proyecto.id = proyecto_empleado.proyecto_id
LEFT JOIN empleado ON proyecto_empleado.empleado_id = empleado.id;


-- Muestra únicamente los proyectos sin empleados asignados.
SELECT proyecto.id, proyecto.nombre,  COUNT(proyecto_empleado.proyecto_id) AS total_empleados
FROM proyecto
LEFT JOIN proyecto_empleado ON proyecto.id = proyecto_empleado.proyecto_id
GROUP BY proyecto.id, proyecto.nombre
HAVING COUNT(proyecto_empleado.proyecto_id) = 0;

-- Suma los presupuestos de cada cliente; muestra cero si no tiene proyectos.
SELECT cliente.id, cliente.nombre, COALESCE(SUM(proyecto.presupuesto), 0) AS total_presupuesto
FROM cliente
LEFT JOIN proyecto ON cliente.id = proyecto.cliente_id
GROUP BY cliente.id, cliente.nombre;

-- Cuenta los proyectos de cada empleado, incluidos los que tienen cero.
SELECT empleado.id, empleado.nombre, COUNT(proyecto_empleado.proyecto_id) AS total_proyecto
FROM empleado
LEFT JOIN proyecto_empleado ON empleado.id = proyecto_empleado.empleado_id
GROUP BY empleado.id, empleado.nombre;

-- Muestra los empleados asignados a dos o más proyectos.
SELECT empleado.id, empleado.nombre, COUNT(proyecto_empleado.proyecto_id) AS total_proyecto
FROM empleado
LEFT JOIN proyecto_empleado ON empleado.id = proyecto_empleado.empleado_id
GROUP BY empleado.id, empleado.nombre
HAVING COUNT(proyecto_empleado.proyecto_id) >= 2;
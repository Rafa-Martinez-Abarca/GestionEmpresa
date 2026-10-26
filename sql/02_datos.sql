INSERT INTO cliente (id, nombre, email) VALUES 
(1, 'Rafa', 'rafa@empresa.com'),
(2, 'Pepe', 'pepe@empresa.com'),
(3, 'Laura', 'laura@empresa.com'),
(4, 'Ana', 'ana@empresa.com');

INSERT INTO empleado (id, nombre, email, salario) VALUES 
(1, 'Carlos', 'carlos@empresa.com', 1800.56),
(2, 'Marta', 'marta@empresa.com', 2000.00),
(3, 'Elena', 'elena@empresa.com', 1900.00);

INSERT INTO proyecto (id, nombre, cliente_id, presupuesto) VALUES 
(1, 'Web de empresa', 1, 1000),
(2, 'Tienda online', 1, 2000),
(3, 'App inventario', 2, 1500),
(4, 'Portal cliente', 3, 500);

INSERT INTO proyecto_empleado (proyecto_id, empleado_id) VALUES 
(1, 1),
(1, 2),
(2, 1),
(3, 3);
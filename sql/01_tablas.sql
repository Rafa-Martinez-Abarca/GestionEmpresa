CREATE TABLE cliente (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email  VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE empleado (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    salario NUMERIC(10, 2) NOT NULL CHECK (salario >= 0)
);

CREATE TABLE proyecto (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cliente_id INTEGER NOT NULL REFERENCES cliente(id),
    presupuesto NUMERIC(10, 2) NOT NULL DEFAULT 0 CHECK (presupuesto >= 0)
);

CREATE TABLE proyecto_empleado (
    proyecto_id INTEGER REFERENCES proyecto(id),
    empleado_id INTEGER REFERENCES empleado(id),
    PRIMARY KEY (proyecto_id, empleado_id)
);
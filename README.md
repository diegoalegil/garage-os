# GarageOS

![GarageOS banner](img/banner1.webp)

GarageOS es una aplicación de escritorio para gestionar vehículos y sus mantenimientos.
Está desarrollada con JavaFX, FXML y SQLite, siguiendo una estructura por capas para separar interfaz, lógica de negocio y acceso a datos.

## Funcionalidades

- Crear, listar, actualizar y eliminar vehículos.
- Guardar vehículos en una base de datos SQLite.
- Asociar mantenimientos a cada vehículo por matrícula.
- Crear, listar, actualizar y eliminar mantenimientos.
- Validar datos de vehículos y mantenimientos antes de guardarlos.
- Ejecutar pruebas unitarias sobre la lógica de validación.

## Tecnologías

- Java 21
- JavaFX 21
- FXML y Scene Builder
- SQLite
- Maven
- JUnit 5

## Estructura del Proyecto

```txt
src/main/java/io/github/diegoalegil/garageos
├── controllers      # Controladores JavaFX
├── models           # Clases de dominio
├── repositories     # Interfaces de acceso a datos
├── repositories/sqlite
│   ├── MantenimientoSqliteRepository.java
│   ├── SQLiteConnectionManager.java
│   └── VehiculoSqliteRepository.java
├── services         # Lógica de negocio
└── utils            # Validaciones y utilidades
```

## Ejecutar la App

```bash
mvn javafx:run
```

## Ejecutar Tests

```bash
mvn test
```

## Estado Actual

El proyecto ya cuenta con CRUD completo de vehículos y mantenimientos, persistencia en SQLite y primeras pruebas unitarias.

Los próximos pasos previstos son mejorar la interfaz visual, ampliar validaciones, añadir más pruebas y preparar una presentación más pulida del flujo de uso.

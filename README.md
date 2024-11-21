
PRACTICA PARCIAL - API DE RESERVAS DE VUELOS

## Introducción

¡Bienvenido al API del Sistema de Reservas de Vuelos! Este API está diseñado para proporcionar a los desarrolladores acceso fácil a una colección completa de datos de reservas de vuelos. Ya sea que estés construyendo una herramienta de reservas de viajes, un sistema de gestión de vuelos, o cualquier otra aplicación que requiera datos de reservas de vuelos, este API será tu recurso principal.

### ¿Qué es el Sistema de Reservas de Vuelos?

El Sistema de Reservas de Vuelos es una solución integral para gestionar reservas de vuelos, incluyendo detalles sobre vuelos, aviones, pasajeros, asientos, aerolíneas, miembros de la tripulación, horarios de vuelos y tickets. Permite a los usuarios reservar vuelos, gestionar la disponibilidad de asientos, rastrear información de pasajeros y manejar operaciones complejas de vuelos.

### APIMOCK SOURCE DATA
http://localhost:3000

## Características del API

### API de Reservas de Vuelos

Construir un API de Reservas de Vuelos implica recopilar y procesar datos relacionados con vuelos, aviones, pasajeros, reservas, aerolíneas, miembros de la tripulación, horarios de vuelos y tickets.

#### 1 - Definir los Endpoints del API:

Comienza definiendo los endpoints para tu API de Reservas de Vuelos.
ENDPOINTS SOLICITADOS.
```http
/api/flights: Para listar todos los vuelos disponibles. 5P
/api/flights/{flight_id}: Para recuperar detalles de un vuelo específico. 10P
/api/bookings: Para crear una nueva reserva. 25P
/api/bookings/{booking_id}: Para recuperar o actualizar una reserva específica.5P
/api/tickets: Para listar todos los tickets. 5P
```

#### 2 - Recopilar Datos de Vuelos:

Necesitarás recopilar datos sobre los vuelos, aviones, pasajeros, reservas, aerolíneas, miembros de la tripulación, horarios de vuelos y tickets. Estos datos deben incluir detalles como números de vuelo, modelos de aviones, información de pasajeros, disponibilidad de asientos, detalles de aerolíneas, detalles de miembros de la tripulación, horarios de vuelos e información de tickets.

#### 3 - Gestionar Reservas:

Para gestionar reservas, puedes usar el siguiente enfoque:

- Crear una nueva reserva especificando los detalles del vuelo, asiento, pasajero y ticket.
- Actualizar la disponibilidad de asientos basado en el estado de la reserva.
- Recuperar detalles de la reserva para un ID de reserva específico.

#### Verificación de Concurrencia

Para asegurar la consistencia de los datos y manejar la concurrencia en las reservas, implementa las siguientes estrategias:

- **Optimistic Locking**: Usa versionado para detectar conflictos. Cada vez que se actualiza un registro, se incrementa un número de versión. Si dos transacciones intentan actualizar el mismo registro, la segunda transacción fallará si el número de versión no coincide. Esto se implementa generalmente añadiendo un campo de versión en la tabla de la base de datos. Cuando se realiza una actualización, se verifica que el valor de la versión en la base de datos coincida con el valor esperado. Si no coinciden, significa que otro proceso ha modificado el registro y la transacción se aborta.

- **Pessimistic Locking**: Bloquea registros durante actualizaciones para prevenir conflictos. Esto asegura que solo una transacción pueda modificar un registro a la vez. En este enfoque, cuando una transacción quiere actualizar un registro, primero adquiere un bloqueo en ese registro. Otros procesos que intenten adquirir el mismo bloqueo serán bloqueados hasta que el primer proceso libere el bloqueo. Esto puede ser útil en escenarios donde los conflictos son frecuentes y se desea evitar la sobrecarga de reintentos.

- **Transaction Management**: Asegura la atomicidad de las operaciones usando transacciones. Si una operación dentro de una transacción falla, todas las operaciones se revertirán. Esto se logra utilizando mecanismos de gestión de transacciones proporcionados por el framework o la base de datos. Por ejemplo, en Spring Boot, se puede usar la anotación `@Transactional` para demarcar métodos que deben ejecutarse dentro de una transacción. Si cualquier operación dentro de la transacción falla, se lanza una excepción y todas las operaciones se deshacen.

- **Retry Mechanism**: Implementa lógica de reintento para fallos transitorios. Si una operación falla debido a un conflicto de concurrencia, se puede reintentar después de un breve retraso. Esto se puede implementar utilizando bibliotecas de reintento o escribiendo lógica personalizada. Por ejemplo, se puede capturar la excepción que indica un conflicto de concurrencia y luego esperar un breve período antes de intentar la operación nuevamente. Es importante limitar el número de reintentos para evitar bucles infinitos.

### Levantar una base de datos JSON local con Node.js

Para levantar una base de datos JSON desde la consola con Node.js, puedes usar el paquete `json-server`. Aquí tienes los pasos para hacerlo:

1. **Instalar `json-server`**:
   Abre tu terminal y ejecuta el siguiente comando para instalar `json-server` globalmente:
   ```sh
   npm install -g json-server

3. **Levantar el servidor**:
   En la terminal, navega al directorio donde se encuentra tu archivo `db.json` y ejecuta el siguiente comando:
   ```sh
   json-server --watch db.json
   ```

   Esto levantará un servidor en `http://localhost:3000` que servirá los datos de tu archivo `db.json`.

4. **Acceder a la base de datos**:
   Puedes acceder a los datos a través de las rutas generadas automáticamente. Por ejemplo:
    - `http://localhost:3000/flights` para obtener todos los vuelos.
    - `http://localhost:3000/flights/1` para obtener el vuelo con `flight_id` 1.

### Ejemplo de Respuestas de Endpoints

#### Endpoint: /api/flights

```json
{
  "flights": [
    {
      "flight_id": 1,
      "flight_number": "AA123",
      "origin": "JFK",
      "destination": "LAX",
      "airline_id": 1,
      "schedule_id": 1,
      "crew_members": [
        "CAPITAN CARLOS",
        "COPILOTO GONZALO"
      ],
      "airplane": {
        "model": "LAPA",
        "seats": [
          {"seat_id": 1, "isAvailable": false, "version": 1},
          {"seat_id": 2, "isAvailable": false, "version": 1}
        ]
      }
    },
    {
      "flight_id": 2,
      "flight_number": "BA456",
      "origin": "LHR",
      "destination": "JFK",
      "airline_id": 2,
      "schedule_id": 2,
      "crew_members": [
        "CAPITAN JOSEFINA",
        "COPILOTO CLAUDIA"
      ],
      "airplane": {
        "model": "AEROLINEAS",
        "seats": [
          {"seat_id": 3, "isAvailable": false, "version": 1},
          {"seat_id": 4, "isAvailable": true, "version": 1}
        ]
      }
    }
  ]
}
```

#### Endpoint: /api/bookings

```json
{
  "bookings": [
    {
      "booking_id": 1,
      "flight": {
        "flight_id": 1,
        "flight_number": "AA123"
      },
      "seat": {
        "seat_id": 1,
        "isAvailable": false,
        "version": 1
      },
      "passenger": "MARIA",
      "ticket": {
        "ticket_id": 1,
        "price": 200.0,
        "seat_class": "Economy"
      },
      "status": "CONFIRMED"
    },
    {
      "booking_id": 2,
      "flight": {
        "flight_id": 2,
        "flight_number": "BA456"
      },
      "seat": {
        "seat_id": 3,
        "isAvailable": false,
        "version": 1
      },
      "passenger": "JUAN",
      "ticket": {
        "ticket_id": 2,
        "price": 300.0,
        "seat_class": "Business"
      },
      "status": "CONFIRMED"
    },
    {
      "booking_id": 3,
      "flight": {
        "flight_id": 1,
        "flight_number": "AA123"
      },
      "seat": {
        "seat_id": 2,
        "isAvailable": false,
        "version": 1
      },
      "passenger": "PEDRO",
      "ticket": {
        "ticket_id": 3,
        "price": 250.0,
        "seat_class": "Economy"
      },
      "status": "PENDING"
    }
  ]
}
```

#### Endpoint: /api/tickets

```json
{
  "tickets": [
    {
      "ticket_id": 1,
      "price": 200.0,
      "seat_class": "Economy",
      "status": "CONFIRMED"
    },
    {
      "ticket_id": 2,
      "price": 300.0,
      "seat_class": "Business",
      "status": "CONFIRMED"
    },
    {
      "ticket_id": 3,
      "price": 250.0,
      "seat_class": "Economy",
      "status": "PENDING"
    },
    {
      "ticket_id": 4,
      "price": 400.0,
      "seat_class": "First",
      "status": "CANCELLED"
    },
    {
      "ticket_id": 5,
      "price": 350.0,
      "seat_class": "Business",
      "status": "CONFIRMED"
    }
  ]
}

```
```
Requirements
The application must be implemented as a microservice using SpringBoot Framework.
The application must be implemented using Java 17.
The application must be implemented using Maven.
The rest clients must be implemented using Rest Template.
The Rest clients must implement the circuit breaker pattern using Resilience4j.
The application must be tested using JUnit 5 and Mockito.
The application must have 100% code coverage.
The application must be deployed using Docker.

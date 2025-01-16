# Prueba Tenpo - Servicio de Cálculo Reactivo

Este proyecto es un servicio de cálculo desarrollado con **Spring WebFlux** que permite obtener porcentajes de un servicio externo, realizar cálculos utilizando dichos porcentajes, y mantener un historial de operaciones. Además, el proyecto incluye el uso de un caché para optimizar el rendimiento y tolerar fallos en el servicio externo.

## Tabla de Contenidos
- [Descripción del Proyecto](#descripción-del-proyecto)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instrucciones de Configuración](#instrucciones-de-configuración)
    - [Requisitos Previos](#requisitos-previos)
    - [Levantar el Proyecto Localmente](#levantar-el-proyecto-localmente)


---

## Descripción del Proyecto

El servicio realiza las siguientes funciones principales:
1. **Cálculo Dinámico con Porcentajes**: Obtiene un porcentaje desde un servicio externo y lo utiliza para realizar cálculos.
2. **Historial de Operaciones**: Guarda un registro de todas las operaciones realizadas, con soporte para paginación.
3. **Manejo de Caché**: Si el servicio externo falla, utiliza valores almacenados en caché para garantizar la continuidad del servicio.

## Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.x**
- **Spring WebFlux**
- **PostgreSQL**
- **Docker y Docker Compose**
- **Caffeine Cache**
- **Reactor Core**
- **Swagger (Springdoc OpenAPI)**

---

## Instrucciones de Configuración

### Requisitos Previos

Asegúrate de tener instalados los siguientes programas:

1. **Java 21**
2. **Docker y Docker Compose**
3. **Git**

### Levantar el Proyecto Localmente

1. **Clonar el repositorio**:
   ```bash
   git clone https://github.com/alefvaras/tenpo-prueba.git
   
2 **Docker**:
   ```bash
    docker pull aleeeee/prueba-tenpo
    docker-compose up --build




# 📚 Sistema de Notas

> Sistema web para la gestión académica de un colegio: alumnos, docentes, cursos, matrículas y notas, con generación de reportes en PDF.

---

## 👥 Integrantes

| Nombre completo |
|---|
| Fred Anthoni Condori Nina |
| Anthony Alberto Viza Chuctaya |
| Josue Obed Arizapana Alejo |
| Karl Edwars Delgado Davila |

**Docente:** Mtro. (c) Bocanegra Pinchi Yan Carlos

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5 | Framework principal |
| Spring Security | 6 | Autenticación y autorización |
| Spring Data JPA + Hibernate | — | ORM y acceso a BD |
| Thymeleaf | — | Vistas HTML |
| MySQL | 8.0 | Base de datos |
| JasperReports | 6.21.3 | Generación de reportes PDF |
| Lombok | — | Reducción de código repetitivo |
| Maven | — | Gestión de dependencias |

---

## 🏗️ Arquitectura

El proyecto sigue el patrón **MVC (Model-View-Controller)** organizado en capas:

```
entity       →  Modelos JPA (tablas de la BD)
repository   →  Acceso a datos (Spring Data)
service      →  Lógica de negocio
controller   →  Rutas HTTP
security     →  Autenticación y permisos
```

---

## 🔐 Roles y permisos

| Rol | Permisos | Pantalla inicial |
|---|---|---|
| `ADMIN` | Acceso total al sistema | Dashboard `/` |
| `DOCENTE` | Gestión de notas y alumnos | `/notas` |
| `ESTUDIANTE` | Solo su perfil e historial | `/alumnos` |

---

## 🗄️ Base de datos

**Motor:** MySQL 8.0  
**Nombre de la BD:** `sistema_notas_db`

Tablas principales:

```
alumno
docente
curso
matricula
detalle_matricula   ← tabla central (alumno + curso + ciclo + nota)
usuario
rol
usuario_rol
```

---

## 📊 Reportes PDF (JasperReports)

| Reporte | Endpoint |
|---|---|
| Lista de alumnos | `GET /reportes/alumnos` |
| Detalle de alumno | `GET /reportes/alumnos/{codigo}` |
| Historial de alumno | `GET /reportes/alumnos/{codigo}/historial` |
| Matrículas | `GET /reportes/matriculas` |
| Notas | `GET /reportes/notas` |

---

## ⚙️ Configuración y ejecución

### Requisitos previos
- Java 21
- MySQL 8.0
- Maven 3.x

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/tu-usuario/sistema-notas.git
cd sistema-notas
```

**2. Crear la base de datos**
```bash
mysql -u root -p < SISTEMA_NOTAS.sql
```

**3. Configurar la conexión** en `src/main/resources/application-local.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_notas_db
spring.datasource.username=root
spring.datasource.password=tu_password
```

**4. Ejecutar el proyecto**
```bash
mvn spring-boot:run
```

**5. Abrir en el navegador**
```
http://localhost:8080
```

```
sistema-notas/
├── src/main/java/sistema_notas/
│   ├── controller/       # Controladores HTTP
│   ├── entity/           # Entidades JPA
│   ├── repository/       # Repositorios Spring Data
│   ├── service/          # Lógica de negocio
│   │   └── impl/         # Implementaciones
│   ├── security/         # Configuración de seguridad
│   ├── dto/              # DTOs (en desarrollo)
│   └── util/             # Utilidades
├── src/main/resources/
│   ├── templates/        # Vistas Thymeleaf
│   ├── reports/          # Plantillas JasperReports (.jrxml / .jasper)
│   ├── application.properties
│   ├── application-local.properties
│   └── application-prod.properties
├── pom.xml
└── Dockerfile
```

---

*Proyecto académico — 2026*
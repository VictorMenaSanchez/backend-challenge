## Documentación del Desafío Técnico

En este archivo voy a recopilar los pasos que he ido haciendo durante el desarrollo del desafío. Esto incluye instalaciones, configuraciones, creación de la base de datos, etc.

El **token de Spotify** ya lo tenía de una práctica de clase; intentaré documentar también cómo se obtiene.

---

##  Configuración Inicial del Proyecto (Spring Boot)

Con **Spring Initializr**, creé la plantilla de proyecto con la siguiente configuración:

### Proyecto

* **Project:** Gradle - Kotlin
* **Spring Boot:** 3.5.8 (Revisar esta versión, ya que es poco común para la rama 3.x)

### Metadatos del Proyecto

* **Group:** `com.victor`
* **Artifact/Name:** `backendchallenge`
* **Description:** "Demo project for Spring Boot"
* **Package name:** `com.victor.backendchallenge`
* **Packaging:** Jar
* **Configuration:** Properties

### Dependencias

* Spring Web
* Spring Data JPA
* **MySQL Driver** (Se cambió de PostgreSQL Driver a MySQL por problemas de instalación de PostgreSQL)
* Spring Boot DevTools
* Validation

Una vez creado, lo descargué, lo abrí con **IntelliJ** e instalé Gradle.

---

##  Configuración del Backend

### Conexión a Base de Datos (MySQL)

Se creó la configuración en `application.properties` para la conexión a la base de datos:

* **URL/Driver:** Definido para apuntar al puerto `3306`.
    * **Usuario:** `"root"`
    * **Contraseña:** `"root"`

#### Configuración de Hibernate

* **Dialecto:** Se especificó `MySQL8Dialect`.
* **Actualización de Esquema:** Configurado para actualizar automáticamente el esquema de la base de datos (`ddl-auto-update`).
* **Debugging SQL:** Se habilitó para mostrar el SQL que se ejecuta en la consola (`show-sql=true`).

### Puerto del Servidor

* Se configuró el puerto del servidor a **`8081`**, ya que el puerto `8080` está ocupado.

---

##  Creación de la Base de Datos

Mi modelo tiene **5 entidades principales** y una tabla de relación que maneja la cardinalidad Muchos a Muchos.

### Cardinalidad y Relación Clave

* La única relación compleja en este esquema es entre **Artistas** y **Álbumes**.
* **Relación:** `album_artists` (**Muchos a Muchos - N:M**).
* **Propósito:** Resuelve la cardinalidad N:M entre las tablas `albums` y `artists`.
    * Descripción: Un álbum puede tener múltiples artistas y un artista puede haber publicado múltiples álbumes.
* **Clave Primaria (PK):** Compuesta por ambas claves foráneas (`album_id, artist_id`).
* **Restricciones de Integridad:**
    * `fk_album` se relaciona con `albums(id)`
    * `fk_artist` se relaciona con `artists(id)` (Corregido: era `fk_artis`)
* **Comportamiento en Cascada:** `ON DELETE CASCADE` y `ON UPDATE CASCADE`. Si se elimina un artista o un álbum, la fila correspondiente en `album_artists` también se eliminará automáticamente.

### Estructura y Convenciones

El diseño utiliza un patrón común de identificadores:

| Convención | Descripción |
| :--- | :--- |
| **Identificadores (IDs)** | Las entidades principales tienen un identificador interno (`id` de tipo `CHAR(36)`) y un identificador externo único (`spotify_id` de tipo `VARCHAR(255)`). |
| **UUIDs como PKs** | Se utiliza `CHAR(36)` para las claves primarias (`id`), lo que sugiere el uso de **UUIDs** (Identificadores Únicos Universales) en lugar de IDs auto-incrementales. |
| **Spotify IDs Únicos** | El campo `spotify_id` tiene una restricción `UNIQUE`. |
| **Motor InnoDB** | Garantiza el soporte de **transacciones ACID** y la integridad referencial. |

El modelo está listo para expandirse (ej. añadiendo una relación entre `playlists` y otras entidades).









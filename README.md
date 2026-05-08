# Sistema de Biblioteca - Colegio Amigos De Don Bosco
### POO901 - Programación Orientada a Objetos | Universidad Don Bosco | 2026

Sistema de escritorio desarrollado en Java con Swing para gestionar la biblioteca del Colegio Amigos De Don Bosco. Permite administrar documentos, usuarios, préstamos y devoluciones con control de mora.

---

## Requisitos previos
Ejecuten el biblioteca_db.sql en su MySQL Workbench
Abran la carpeta en NetBeans con File → Open Project
Si su contraseña de MySQL es diferente a 1234, la cambien en Conexion.java
Le den F6 para correr

Antes de correr el proyecto asegúrate de tener instalado:

- Java JDK 17 o superior
- NetBeans IDE (recomendado: versión 29)
- MySQL Server (versión 8.0 o superior)
- MySQL Workbench

---

## Pasos para configurar el proyecto

### 1. Clonar o descargar el repositorio

Haz clic en el botón verde **Code → Download ZIP** y descomprime la carpeta, o clónalo con:

```
git clone https://github.com/tu-usuario/biblioteca-poo901.git
```

### 2. Crear la base de datos

1. Abre **MySQL Workbench** y conéctate a tu servidor local
2. Ve a **File → Open SQL Script**
3. Selecciona el archivo `biblioteca_db.sql` que está en la carpeta del proyecto
4. Ejecuta el script con el botón del rayo ⚡ o `Ctrl + Shift + Enter`
5. Verifica que todas las sentencias se ejecutaron en verde en el panel Output

### 3. Configurar la contraseña de MySQL

Abre el archivo `src/main/java/conexion/Conexion.java` y cambia la contraseña si es diferente a `1234`:

```java
private static final String PASSWORD = "1234"; // Cambia esto por tu contraseña
```

### 4. Abrir el proyecto en NetBeans

1. Abre NetBeans
2. Ve a **File → Open Project**
3. Busca y selecciona la carpeta `BibliotecaDB`
4. NetBeans descargará las dependencias automáticamente (necesitas internet la primera vez)
5. Presiona **F6** para ejecutar

---

## Credenciales por defecto

| Tipo de usuario | Email | Contraseña |
|---|---|---|
| Administrador | admin@biblioteca.edu | admin123 |

> El administrador puede crear nuevos usuarios desde la opción **Gestionar Usuarios**.

---

## Funcionalidades del sistema

- **Login** con validación de credenciales y tipo de usuario
- **Gestionar Documentos** — agregar y consultar Libros, Revistas, CDs y DVDs
- **Gestionar Usuarios** — crear usuarios, asignar privilegios y restablecer contraseñas
- **Gestionar Préstamos** — registrar préstamos y devoluciones con validaciones automáticas
- **Calcular Mora** — actualiza la mora de préstamos vencidos ($0.25 por día)

---

## Reglas del sistema

- Los **alumnos** pueden tener hasta **3 préstamos** activos con **7 días** por préstamo
- Los **profesores** pueden tener hasta **6 préstamos** activos con **15 días** por préstamo
- Los usuarios **con mora** no pueden realizar nuevos préstamos
- Los códigos de documentos se generan automáticamente: `LIB00001`, `REV00001`, `CDA00001`, `DVD00001`

---

## Ejecutar sin NetBeans

Si solo quieres correr la aplicación sin abrir NetBeans, usa el archivo `.jar`:

```
java -jar target/BibliotecaDB-1.0-SNAPSHOT.jar
```

O simplemente haz **doble clic** en el archivo `.jar` desde el explorador de Windows. Asegúrate de que MySQL esté corriendo antes de abrir la app.

---

## Tecnologías utilizadas

- Java 26
- Swing (interfaz gráfica)
- JDBC (conexión a base de datos)
- MySQL 9.6
- Maven (gestor de dependencias)

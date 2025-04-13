Este proyecto consiste en una aplicación Java que consume datos en tiempo real de la API de Valenbici (servicio público de alquiler de bicicletas de Valencia) y los almacena en una base de datos MySQL local para su análisis o consulta histórica.

Objetivo
  Obtener información sobre estaciones de Valenbici (ubicación, bicis disponibles, anclajes libres, etc.) mediante una API REST.
  Mostrar los datos al usuario en una interfaz gráfica (Swing).
  Insertar los datos en una base de datos local (valenbicibd) en la tabla historico.

Tecnologías utilizadas
  Java (NetBeans IDE) – Lógica principal de la aplicación e interfaz gráfica.
  Swing – Para construir la interfaz (JFrame, JTextArea, JButtons...).
  HTTP Client (Apache HttpClient) – Para realizar solicitudes HTTP a la API.
  JSON (org.json) – Para parsear y manipular la respuesta de la API.
  MySQL Workbench – Para crear y gestionar la base de datos.
  JDBC – Para conectarse y escribir en la base de datos desde Java.
  MySQL Connector/J – Driver JDBC para conexión MySQL.

Estructura del proyecto
DatosJSon.java
  Clase encargada de hacer la petición HTTP a la API de Valenbici, procesar la respuesta JSON y extraer los datos relevantes. También prepara los datos para su visualización y almacenamiento.
  ConexionBDD.java
  Ventana principal de la interfaz. Gestiona la interacción con el usuario: conectar con la base de datos, mostrar datos, insertar datos y cerrar conexión.
  valenbicibd (MySQL)
  Base de datos que contiene la tabla historico, donde se guardan los datos de cada estación consultada.

Estructura de la tabla historico
    sql
    Copiar
    Editar
    CREATE TABLE historico (
        id INT AUTO_INCREMENT PRIMARY KEY,
        estacion_id INT NOT NULL,
        direccion VARCHAR(255),
        bicis_disponibles INT NOT NULL,
        anclajes_libres INT NOT NULL,
        estado_operativo BOOLEAN NOT NULL,
        fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        ubicacion POINT
    );

Pasos principales realizados
  Se ha creado un JFrame que permite al usuario:
  Introducir el número de estaciones a consultar.
  Ver los datos obtenidos desde la API en un área de texto.
  Conectarse a una base de datos local MySQL.
  Insertar los datos obtenidos en la tabla historico.
  Se ha creado y probado la conexión a la API REST de Valenbici usando HttpClient.
  Se ha parseado la respuesta JSON para extraer:
  Dirección de la estación.
  Número de bicis disponibles.
  Número de anclajes libres.
  Capacidad total.
  Posición geográfica (latitud y longitud).
  Se ha creado la base de datos y tabla desde MySQL Workbench.
  Se ha implementado la inserción de datos desde Java a MySQL utilizando PreparedStatement.

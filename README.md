# Zoologico
 
Prueba del Zoologico

#Inicio del token
ruta: http://localhost:8080/login
al momento de loguearse buscar en los headers de la respuesta, ahí estará el "Bearer Token",
en Authorization elegir la funcionalidad de "Bearer Token" para cada endpoint

![image](https://github.com/rstart18/Zoologico/assets/40774070/7f0d1a73-d002-466d-9b5e-525f45bb42e6)

Se dejaron adjuntos:
Script.sql que es el script para crear las bases de datos para cualquier fallo que se presente,
ERD,jpg que es el modelo relacional de mi solución,
la documentación de PostMan.


Si se quiere usar el microservicio para enviar correos electronicos a los usuarios que se le han respondido los mensajes es necesario ejecutar el archivo microservicio.py en la raíz

NOTA:

tener en cuenta que para ejecutar microservicio.py se deben instalar las librerías de Flask y pymongo

comandos para instalar las liberías

pip install Flask
pip install pymongo



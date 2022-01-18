--Pantalla de login--
La aplicacion se inicia en la pantalla para iniciar sesión, si no se tiene ningun usuario se tiene que crear uno primero en el boton registrarse, en esta app solo pide el nombre de usuario y una clave, siendo el nombre
la clave primaria de la base de datos. Todas las excepciones estan controladas asi que aqui no deberia de haber ningun problema

--Vista principal--
Ya iniciada sesion, en la vista principal saldra una lista con juegos, en cada tarjeta se vera un boton de borrar y si se pulsa la tarjeta te llevara a otra pantalla donde te muestra más detalles del juego, las imagenes pueden
tardar en cargar por la API pero tienen una imagen de carga. En la parte superior derecha esta el menú que te permite ir a los ajustes o cerrar sesion y volver a la pantalla de inicio. Cada vez que se entre en esta pantalla se
actualizara segun los ajustes

--Ajustes--
Solo tiene la opcion de ordenar alfabeticamente la lista de juegos mediante un checkbox

-- Vista Detallada--
Aqui se muestra todos los detalles de un juego, en la imagen esta implementada la biblioteca externa:
https://github.com/florent37/ArcLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=4694
que permite poner arcos e imagenes en movimiento, la calidad de la imagen es algo mala pero eso es de la api

El codigo esta comentado explicando que hace cada cosa.

Hecho por Jorge Carlos Parra Montoya 2ºDAM
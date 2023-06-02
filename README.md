

<h2>Trabajo Fin de Grado</h2>


Desarrollo de Aplicaciones Multiplataforma \
EDIX 2022/2023

[Enlace a la Memoria](https://docs.google.com/document/d/1_9rDg1UyBEAP_avnsvsFDRjZ6dFbfLceXkFwvEo8pTs/edit?usp=sharing) 

<h1>
RestoApp</h1>


**Carlos Alonso Muñoz - Alfredo Hernández-Rovati - Iñigo López de Audicana**


1. Memoria del Proyecto
    1. Resumen

    La idea de este proyecto consiste en el desarrollo de una aplicación móvil que brinda la oportunidad a sus usuarios de poder realizar pedidos de comida  tanto a domicilio como in situ en el propio local de comidas. La idea surge en recuerdo de las restricciones que durante la pandemia COVID restringian el contacto entre personas, de aquellas dificultades crecieron oportunidades y  con el impulso  de las nuevas tecnologías surgieron infinidad de ideas y proyectos para paliar las dificultades del día a día. Esta es nuestra humilde aportación a lo que a día de hoy y ya libres de restricciones es una necesidad tecnológica que crece de manera exponencial día a día.


    La aplicación cuenta con un diseño intuitivo y fácil de usar, adaptado para su funcionamiento en dispositivos Android. 


    Algunas sus características son:


    1. Registro y autenticación multifunción: Los usuarios pueden crear una cuenta utilizando su dirección de correo electrónico o su cuenta de Google. Esto les permitirá acceder a todas las funcionalidades de la aplicación, tanto si están en una mesa del local o en su domicilio, gracias a la diferenciación de roles mediante la identificación de nombres de usuario.


    2. Acceso para varios perfiles de gestión: La aplicación también gestiona los pedidos realizados por los usuarios, de esta forma el restaurante también puede implementar la gestión de pedidos en cocina, permirtiendo una mayor integración del negocio en una misma app.


    3. Los usuarios podrán pedir los platos y escoger entre pago en efectivo o pago con tarjeta directamente desde la aplicación, ahorrando tiempo y simplificando los procesos.

2. Objetivos del Proyecto

    Los principales objetivos que se persiguieron a la hora de desarrollar la app:

1. **Mejorar  la experiencia del cliente:** La app proporciona a los clientes una forma eficaz de acceder a los menús, realizar pedidos  y pagos desde sus dispositivos móviles. Esto agiliza el proceso de pedido y mejora la satisfacción del cliente al proporcionar un servicio más rápido.
2. **Elimina errores de comunicación:** Al permitir que los clientes realicen pedidos directamente desde la aplicación, se reduce la posibilidad de errores de comunicación entre los clientes y el personal del establecimiento. Esto garantiza que los pedidos se registren correctamente .
3. **Integrar procesos de gestión:** La aplicación busca ofrecer viarias funcionalidades presentes en un restaurantes bajo la misma aplicación, diferenciando solo entre los usuarios, mostrando, por ejemplo, la carta a un usuario pero solo las comandas activas al perfil de cocina.
4. **Integración con el sistema de punto de venta:** La aplicación puede integrarse con el sistema de punto de venta del restaurante (en este caso _Stripe_), lo que garantiza una sincronización automática de los pedidos y pagos. Esto evita la necesidad de ingresar manualmente los datos.

    En resumen, RestoApp busca mejorar la experiencia tanto para los clientes como para los restaurantes, al agilizar el proceso de pedido, gestión clientes y pagos. 

2. Herramientas Utilizadas	
    8. Java

        Este lenguaje de programación es el más utilizado por aplicaciones nativas de Android, creado por Sun Microsystems en 1995, con sintaxis basada en C y C++ pero menos enfocado al bajo nivel, centrándose en la premisa _Write Once, Write Anywhere_ (Escríbelo una vez, ejecútalo en cualquier lugar). El lenguaje es independiente del entorno de ejecución (JVM) lo que permite al lenguaje ejecutarse en cualquier plataforma. Para programar aplicaciones Android es vital la instalación del SDK (Software Development Kit) de Android y de un IDE (Integrated Development App) diseñado para ello, el más popular siendo Android Studio.  	

    9. Android Studio

    El Entorno de Desarrollo Oficial de Android, desarrollado por Google específicamente para Android, basado en IntelliJ IDEA. El lenguaje oficial desde 2019 es Kotlin pero también acepta Java o C++. Incluye todas las herramientas necesarias para desarrollar aplicaciones Android como refactorización y sugerencias de código específicos , simulador de dispositivos Android o plantillas de diseño específicas para Android.



    10. Firebase

    Se trata de una plataforma de desarrollo de aplicaciones propiedad de Google, ofrece la sincronización de proyectos con la Nube, servicios como Firestore, Authentication o Analytics permiten la integración y desarrollo de aplicaciones sin necesidad de un servidor dedicado. 




    **Authentication:** Se encarga de la Autenticación de usuarios de la aplicación, permitiendo la control e integración de múltiples proveedores de acceso (Google, Apple, Facebook, etc.), gestión de correos de confirmación de email o restablecimiento de contraseña e incluso comprobación de 2FA (Two Factor Authentication) mediante SMS.


    **Firestore:** Se trata de un servicio de base de datos NoSQL que sigue una estructura de colecciones y documentos en vez de tablas como las bases de datos relacionales, tiene una alta velocidad de respuesta y permite el almacenamiento de valores de texto de distintos tipos como cadenas, números, coordenadas, mapas u objetos. También incluye reglas de seguridad editables para controlar el acceso y modificación de la base de datos según los parámetros que establezcamos.



    11. Software Ideas Modeler

    Como su nombre indica el _Software Ideas Modeler_ es un programa para diseñar de forma gráfica todas las ideas del programador, muy similar al MockFlow pero más centrado en el diseño de Esquemas, Diagramas y Flujos de datos:

    12. Github

    Se trata de la herramienta más popular y extendida de hosting de repositorios, sirve para llevar el control de versiones de Git a internet, de forma que grupos o equipos de desarrollo pueden obtener y compartir sus cambios en el código del proyecto de forma sencilla y segura. Mediante creación de ramas y comandos como **commit, pull o push** se puede: guardar los cambios, extraer y actualizar el repositorio actual con el de la nube o publicar los cambios al repositorio remoto.


    13. MockFlow

    Es una plataforma de diseño de maquetas o esquemas de diseño para aplicaciones multiplataforma, cuenta con una amplia variedad de diseños y plantillas para diseñar las estructuras y vistas de una aplicación, se pueden añadir maquetas de distintos modelos de smartphone, imágenes, texto, botones, y objetos como _sliders_, barras de navegación, flechas, etc. Sirve para plasmar la idea de la aplicación en un diseño rápido y como guía a la hora de programar el _front-end_.

5. Fases Del Proyecto
    14. Idea

    Partiendo de la necesidad de muchos restaurantes de organizar y digitalizar su operación de pedidos surge la idea de crear una aplicación que aúne todas las funcionalidades básicas que puede necesitar un restaurante para gestionar pedidos y que puede ser más complicado para un pequeño empresario.

    15. Casos de uso

Aquí podemos ver uno de los principales casos de uso, un usuario quiere realizar un pedido, primero deberá iniciar sesión o registrarse, cuando lo consiga la aplicación lo llevará a la Carta, en la que se encuentran una serie de categorías de comida que puede elegir al pulsar en cualquiera de ellas. A continuación se cargarán desde BBDD el listado correspondiente de la comida (CartaListar), se le presenta al usuario el nombre del plato, una breve descripción y el precio, para añadir el producto puede utilizar los botones (- +) que aparecen al lado de cada plato de comida y que ofrecen la posibilidad de añadir varios platos del mismo tipo. En la parte de abajo siempre está presente el botón de Añadir al carrito que añadirá al carrito la cantidad de elementos previamente seleccionados, a continuación devolverá al usuario a la pantalla de Carta donde este podrá seguir añadiendo platos o acceder al Carrito mediante el botón de la barra superior. 



**Pedidos:** Por cada pedido se crea un documento nuevo, el cual contiene la dirección de correo electrónico, el estado actual del pedido, el precio total, y por cada producto se guarda su nombre, el precio, la cantidad y el precio total de ese producto. 
**Usuarios:** En esta colección se crea un documento por cada usuario, donde se guarda su dirección, dirección de correo electrónico, nombre y número de teléfono.



    19. Android Java

    El proyecto entero está hecho en Java, se ha creado la estructura, las clases, se han gestionado eventos, manipular las vistas, comunicarse con las distintas APIS, gestionar los datos. 

        1. Diferenciación Usuarios

        Hay 3 tipos distintos de usuarios: 
1- Usuario normal \
2- Usuario mesa \
3- Usuario cocina \
 \
El primer y el segundo usuario tienen la misma interfaz, se realiza la distinción en el correo electrónico, al realizar pedidos en la cocina se puede ver la dirección, así sabes si es un pedido a domicilio o un pedido a mesa. \


 \
Desde los usuarios del restaurante no se puede cerrar sesión sin conocer la contraseña.  \

 \
Los usuarios de la cocina tendrán una interfaz totalmente diferente, la distinción se realiza en el login, cuando se pone un correo de la lista de usuarios cocina te llevará a otra ventana. \


        2. Método de pago

        Se ha implementado mediante las librerías de stripe y de volley. \
**Volley** \
Las librerías de volley sirven para realizar solicitudes HTTP y comunicarse con servicios WEB. Mediante esta librería se realizan las peticiones a la API de Stripe.


        **Stripe**


        Stripe es una plataforma de pagos en línea que permite a las empresas y negocios aceptar pagos por Internet. Proporciona una API (Interfaz de Programación de Aplicaciones) fácil de integrar en sitios web y aplicaciones móviles para procesar transacciones con tarjetas de crédito, débito y otros métodos de pago online. \


        3. Cocina

        Para acceder a la cocina tienes que usar un usuario especial en la ventana de login 
Esta es la primera ventana que se ve en la cocina, donde ves los pedidos que están pendientes. Se puede gestionar el estado de los pedidos. 
Al darle al primer botón del menú de arriba podemos cambiar a la ventana de los pedidos completados, aquí lo único que se puede hacer es ver la información de un pedido antiguo.



        Si le damos al único botón del menú volvemos a la pantalla  principal de la cocina. 
SI le damos al segundo botón del menú principal nos lleva a una ventana para obtener información sobre los usuarios. Al poner una dirección de correo electrónico nos da su nombre, su teléfono y su dirección.  
 
El último botón es para cerrar sesión en la cocina.

        4. Pedidos

        Los pedidos funcionan mediante documentos en firebase, para crear un pedido se crea un documento.  
Desde la cocina se ve la lista de pedidos. 


En esta ventana aparecen todos los pedidos en el que su estado sea “En espera” o “Cocinando”. \
Por defecto al crear un pedido éste se encontrará en estado “En espera”, al darle al botón “Hecho” el estado del pedido cambiará a “Completado” y desaparecerá de la ventana, al darle a “Cocinando” el estado cambiará a “Cocinando”. 
Desde esta ventana se ven los pedidos en estado “Completado” \
Al pinchar sobre cualquier pedido veremos la información de ese pedido, en cualquiera de las 2 ventanas.  \



 \
Toda la información se recoge de los documentos de FireStore. \
Hay otra ventana para los usuarios, donde pueden ver sus pedidos, solo verán los que están en estado “En espera” y “Completado”, no pueden realizar ninguna acción con los pedidos, solo ver su información.   \


7. Bibliografía

    Además de todo el material puesto a disposición de los alumnos por parte del [Instituto Tecnológico EDIX](https://www.edix.com/es/fp/) y el conocimiento adquirido durante el Grado se han utilizado distintas fuentes, aquí se citan las más importantes:


“Accept a payment”. Stripe.com, [https://stripe.com/docs/payments/accept-a-payment?platform=android&ui=payment-sheet](https://stripe.com/docs/payments/accept-a-payment?platform=android&ui=payment-sheet). Consultado junio de 2023.

“Descripción general de Volley”. Android Developers, [https://developer.android.com/training/volley?hl=es-419](https://developer.android.com/training/volley?hl=es-419). Consultado junio de 2023.

“Firebase Documentation”. Firebase, [https://firebase.google.com/docs](https://firebase.google.com/docs). Consultado junio de 2023.

“Stack Overflow - Where Developers Learn, Share, & Build Careers”. Stack Overflow, [https://stackoverflow.com/](https://stackoverflow.com/). Consultado junio de 2023.

Wikipedia contributors. “Java”. Wikipedia, The Free Encyclopedia, [https://es.wikipedia.org/w/index.php?title=Java](https://es.wikipedia.org/w/index.php?title=Java_(lenguaje_de_programaci%C3%B3n)&oldid=151115733.) . Consultado junio de 2023.

Oracle.com, [https://blogs.oracle.com/java/post/the-arrival-of-java-20](https://blogs.oracle.com/java/post/the-arrival-of-java-20). Consultado junio de 2023.

**[Imágenes y gráficos]**

Google images. (s/f). Google.com. Recuperado junio de 2023, de [https://images.google.com/](https://images.google.com/)

Diseño de logotipos. (s/f). Freepik. Recuperado junio de 2023, de [https://www.freepik.es/logos](https://www.freepik.es/logos)



8. Anexos

    Todo el código de la aplicación RestoApp, así como su documentación relacionada está alojada en un repositorio de Github, al que se puede acceder mediante [este enlace](https://github.com/cAlonso10/TFG).

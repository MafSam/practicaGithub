<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <?php
        session_start();
        $mensaje=$_SESSION['mensaje'];
        echo "el mensaje es:".$mensaje;
        echo "<br/>";
        echo "el promedio es:<h1>".$_SESSION['promedio']."</h1>";
        ?>
    </body>
</html>

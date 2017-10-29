<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Actualizar Notas</title>
    </head>
    <body>
        <?php
        include '../model/Notas.php';
        session_start();
        $notas = $_SESSION['notas'];
        ?>
        <form action="../controller/Controller.php">
            <input type="hidden" value="actualizar" name="opcion">
            <input type="hidden" value="<?php echo $notas->getCedula() ?>" name="cedula">
            Cedula:<b><?php echo $notas->getCedula() ?></b><br>
            Nombre:<input type="text" name="nombre" value="<?php echo $notas->getNombre() ?>"><br>
            Nota 1:<input type="text" name="nota1" value="<?php echo $notas->getNota1() ?>"><br>
            Nota 2:<input type="text" name="nota2" value="<?php echo $notas->getNota2() ?>"><br>
            <input type="submit" value="Actualizar"><a href="../index.php" target="_self"><input type="button" name="boton" value="Cancelar" /></a>
        </form>
    </body>
</html>
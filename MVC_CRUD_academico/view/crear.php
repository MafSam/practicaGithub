<!DOCTYPE html>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Crear Notas</title>
    </head>
    <body>
        <form action="../controller/Controller.php">
            <input type="hidden" value="guardar" name="opcion">
            Cedula:<input type="text" name="cedula"><br>
            Nombre:<input type="text" name="nombre"><br>
            Nota1:<input type="text" name="nota1"><br>
            Nota2:<input type="text" name="nota2"><br>
            <input type="submit" value="Crear"><a href="../index.php" target="_self"><input type="button" name="boton" value="Cancelar" /></a>
        </form>
    </body>  
</html>

<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sistema Academico</title>
        <script type="text/javascript" language="javascript">
            function confirmar() {
                return confirm("Esta seguro de eliminar este registro?");
            }
        </script>
    </head>
    <body>
        <table>
            <tr><td>
                    <form action="controller/Controller.php">
                        <input type="hidden" value="listar" name="opcion">
                        <input type="submit" value="Consultar listado">
                    </form>
                </td>
                <td>
                    <form action="controller/Controller.php">
                        <input type="hidden" value="listar_desc" name="opcion">
                        <input type="submit" value="Consultar listado descendiente">
                    </form>
                </td>
                <td>
                    <form action="controller/Controller.php">
                        <input type="hidden" value="crear" name="opcion">
                        <input type="submit" value="Crear Notas">
                    </form>
                </td>
                <td>
                    <form action="controller/Controller.php">
                        <input type="hidden" value="notasAcero" name="opcion">
                        <input type="submit" value="Borrar Notas">
                    </form>
                </td></tr>
        </table>
        <table border="2">
            <tr>
                <th>CEDULA</th>
                <th>NOMBRES</th>
                <th>NOTA 1</th>
                <th>NOTA 2</th>
                <th>PROMEDIO</th>
                <th>ELIMINAR</th>
                <th>ACTUALIZAR</th>
            </tr>
            <?php
            session_start();
            include 'model/Notas.php';
            if (isset($_SESSION['listado'])) {
                $listado = unserialize($_SESSION['listado']);
                foreach ($listado as $nota) {
                    echo "<tr>";
                    echo "<td>" . $nota->getCedula() . "</td>";
                    echo "<td>" . $nota->getNombre() . "</td>";
                    echo "<td>" . $nota->getNota1() . "</td>";
                    echo "<td>" . $nota->getNota2() . "</td>";
                    echo "<td>" . $nota->getPromedio() . "</td>";
                    echo "<td><a href='controller/Controller.php?opcion=eliminar&cedula=" . $nota->getCedula() . "' onclick='return confirmar()'>eliminar</a></td>";
                    echo "<td><a href='controller/Controller.php?opcion=cargar&cedula=" . $nota->getCedula() . "'>actualizar</a></td>";
                    echo "</tr>";
                }
            } else {
                echo "No existen datos.";
            }
            ?>
        </table>
        <?php
        if (isset($_SESSION['promedioNotas'])) {
            echo "El promedio del curso es: <b>" . $_SESSION['promedioNotas'] . "</b>";
        }
        if (isset($_SESSION['mensaje'])) {
            echo "<br>Mensaje del Sistema: <font color='red'>" . $_SESSION['mensaje'] . "</font><br>";
        }
        ?>
    </body>
</html>
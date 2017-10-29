<?php
include './Academico.php';
//inicio de sesion
session_start();
    // recibir parametros
$nota1=$_GET['nota1'];
$nota2=$_GET['nota2'];
//invocamos al modelo
$acad=new Academico();
$acad->setNota1($nota1);
$acad->setNota2($nota2);
//obtenemos el resultado
$acad ->procesar();
$mensaje=$acad->getMensaje();
//utilizo sesion
$_SESSION['mensaje']=$mensaje;
$_SESSION['promedio']=$acad->getPromedio();
//redirigir navegacion
header('location:'."resultado.php");
?>

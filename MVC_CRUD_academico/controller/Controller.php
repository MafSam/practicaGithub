<?php

require_once '../model/NotasModel.php';
session_start();
$notasModel = new NotasModel();
$opcion = $_REQUEST['opcion'];
unset($_SESSION['mensaje']);
switch($opcion){
    case "listar":
        $listado = $notasModel->getAcademico(true);
        $_SESSION['listado'] = serialize($listado);
        $_SESSION['promedioNotas'] = $notasModel->getPromedioNotas();
        header('Location: ../index.php');
        break;
    case "listar_desc":
        $listado = $notasModel->getAcademico(false);
        $_SESSION['listado'] = serialize($listado);
        $_SESSION['promedioNotas'] = $notasModel->getPromedioNotas();
        header('Location: ../index.php');
        break;
    case "crear":
        header('Location: ../view/crear.php');
        break;
    case "guardar":
        $cedula = $_REQUEST['cedula'];
        $nombre=$_REQUEST['nombre'];
        $nota1=$_REQUEST['nota1'];
        $nota2=$_REQUEST['nota2'];
        try {
            $notasModel->crearNotas($cedula, $nombre, $nota1, $nota2);
        } catch (Exception $ex) {
            $_SESSION['mensaje']=$ex->getMessage();
        }
        $listado = $notasModel->getAcademico();
        $_SESSION['listado'] = serialize($listado);
        header('Location: ../index.php');
        break;
    case "eliminar":
        $cedula = $_REQUEST['cedula'];
        $notasModel->eliminarNotas($cedula);
        $listado = $notasModel->getAcademico();
        $_SESSION['listado'] = serialize($listado);
        header('Location: ../index.php');
        break;
    case "cargar":
        $cedula = $_REQUEST['cedula'];
        $notas=$notasModel->getNotas($cedula);
        $_SESSION['notas']=$notas;
        header('Location: ../view/actualizar.php');
        break;
    case "actualizar":
        $cedula = $_REQUEST['cedula'];
        $nombre=$_REQUEST['nombre'];
        $nota1=$_REQUEST['nota1'];
        $nota2=$_REQUEST['nota2'];
        $notasModel->actualizarNotas($cedula, $nombre, $nota1, $nota2);
        $listado = $notasModel->getAcademico();
        $_SESSION['listado'] = serialize($listado);
        header('Location: ../index.php');
        break;
    case "notasAcero":
        $notasModel->NotasAcero();
        $listado = $notasModel->getAcademico();
        $_SESSION['listado'] = serialize($listado);
        header('Location: ../index.php');
        break;
    default:
        header('Location: ../index.php');
}
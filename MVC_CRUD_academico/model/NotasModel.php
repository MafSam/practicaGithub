<?php

include 'Database.php';
include 'Notas.php';

class NotasModel {
    
    public function getPromedioNotas(){
        $listado =  $this->getAcademico();
        $prom = 0;
        $c=0;
        foreach ($listado as $not){
            $prom += ($not->getNota1()+$not->getNota2())/2;
            $c++;
        }
        return $prom/$c;
    }

    public function getAcademico($orden) {
        $pdo = Database::connect();
        if ($orden == true) {
            $sql = "select * from notas order by nombre";
        } else {
            $sql = "select * from notas order by nombre desc";
        }
        $resultado = $pdo->query($sql);
        $listado = array();
        foreach ($resultado as $res) {
            $notas = new Notas();
            $notas->setCedula($res['cedula']);
            $notas->setNombre($res['nombre']);
            $notas->setNota1($res['nota1']);
            $notas->setNota2($res['nota2']);
            $notas->setPromedio($res['promedio']);
            array_push($listado, $notas);
        }
        Database::disconnect();
        return $listado;
    }

    public function getNotas($cedula) {
        $pdo = Database::connect();
        $sql = "select * from notas where cedula=?";
        $consulta = $pdo->prepare($sql);
        $consulta->execute(array($cedula));
        $dato = $consulta->fetch(PDO::FETCH_ASSOC);
        $notas = new Notas();
        $notas->setCedula($dato['cedula']);
        $notas->setNombre($dato['nombre']);
        $notas->setNota1($dato['nota1']);
        $notas->setNota2($dato['nota2']);
        $notas->setPromedio($dato['promedio']);
        Database::disconnect();
        return $notas;
    }

    public function crearNotas($cedula, $nombre, $nota1, $nota2) {
        $promedio = ($nota1 + $nota2) / 2;
        $pdo = Database::connect();
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = "insert into notas (cedula,nombre,nota1,nota2,promedio) values(?,?,?,?,?)";
        $consulta = $pdo->prepare($sql);
        try {
            $consulta->execute(array($cedula, $nombre, $nota1, $nota2, $promedio));
        } catch (PDOException $ex) {
            Database::disconnect();
            throw new Exception($ex->getMessage());
        }
        Database::disconnect();
    }

    public function eliminarNotas($cedula) {
        $pdo = Database::connect();
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = "delete from notas where cedula=?";
        $consulta = $pdo->prepare($sql);
        $consulta->execute(array($cedula));
        Database::disconnect();
    }

    public function actualizarNotas($cedula, $nombre, $nota1, $nota2) {
        $promedio = ($nota1 + $nota2) / 2;
        $pdo = Database::connect();
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = "update notas set nombre=?, nota1=?, nota2=?, promedio=? where cedula=?";
        $consulta = $pdo->prepare($sql);
        $consulta->execute(array($nombre, $nota1, $nota2, $promedio, $cedula));
        Database::disconnect();
    }

    public function NotasAcero() {
        $no=0;
        $pdo = Database::connect();
        $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sql = "update notas set nota1=?, nota2=?, promedio=?";
        $consulta = $pdo->prepare($sql);
        $consulta->execute(array($no, $no, $no));
        Database::disconnect();
    }
    
}

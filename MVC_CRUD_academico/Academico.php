<?php

/**
 * Description of Academico
 *
 * @author User
 */
class Academico {
    private $nota1;
    private $nota2;
    private $mensaje;
    private $promedio;
    
            
    function procesar() {
        $this->promedio=(($this->nota1 + $this->nota2)/2);
        if($this->nota1<0){
            $this->mensaje="valor no permitido";
            return;
        }
        if(($this->nota1 + $this->nota2)>=14){
            $this->mensaje="felisitaciones"; 
            return;
        }else{
            $this->mensaje="No aprobo"; 
            return;
        }
    }
            
    function getNota1() {
        return $this->nota1;
    }

    function getNota2() {
        return $this->nota2;
    }

    function getMensaje() {
        return $this->mensaje;
    }

    function setNota1($nota1) {
        $this->nota1 = $nota1;
    }

    function setNota2($nota2) {
        $this->nota2 = $nota2;
    }

    function setMensaje($mensaje) {
        $this->mensaje = $mensaje;
    }
    function getPromedio() {
        return $this->promedio;
    }

    function setPromedio($promedio) {
        $this->promedio = $promedio;
    }



}

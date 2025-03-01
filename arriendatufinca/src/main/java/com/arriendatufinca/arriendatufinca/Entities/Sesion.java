package com.arriendatufinca.arriendatufinca.Entities;

import lombok.Getter;
import lombok.Setter;

@Setter 
@Getter
/**
 * Clase que representa la sesión de un usuario, se implementa como un Singleton, lo que significa que solo puede haber una instancia de esta clase en todo el programa.
 */
public class Sesion {

    private static Sesion sesion = new Sesion();//Instancia de la clase
    private Usuario usuario;//Usuario que ha iniciado sesión

    /**
     * Constructor privado para evitar que se puedan crear más instancias de esta clase.
     */
    private Sesion() {
    }


}

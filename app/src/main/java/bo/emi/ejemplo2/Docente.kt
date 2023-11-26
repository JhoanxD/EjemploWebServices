package bo.emi.ejemplo2

import java.util.Date

data class Docente(
    val id: Long, val nombre:String, val apellido:String, val fecha:Date, val antiguedad:Int
    , val ciudad:Int, val estado:String, val direccion:String)

package bo.emi.ejemplo2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat

class DocenteAdapter (context: Context, docentes: List<Docente>):
        ArrayAdapter<Docente>(context,0,docentes)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val docente: Docente? = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.docente_item, parent, false)
        }

        //val imgEstudiante = convertView!!.findViewById<View>(R.id.imgEstudiante) as ImageView
        val txtNombre = convertView!!.findViewById<View>(R.id.txtItemNombreDocente) as TextView
        val txtApellido = convertView!!.findViewById<View>(R.id.txtItemApellidoDocente) as TextView
        val txtFechana = convertView!!.findViewById<View>(R.id.txtItemFechaNacimientoDocente) as TextView
        val txtAntiguiedad = convertView!!.findViewById<View>(R.id.txtItemAntiguedadDocente) as TextView
        val txtCiudad = convertView!!.findViewById<View>(R.id.txtItemCiudadDocente) as TextView
        val txtEstado= convertView!!.findViewById<View>(R.id.txtItemEstadoDocente) as TextView
        val txtDireccion = convertView!!.findViewById<View>(R.id.txtItemDireccionDocente) as TextView

        //imgEstudiante.setImageResource(R.mipmap.ic_launcher_round)

        txtNombre.setText(docente?.nombre)
        txtApellido.setText(docente?.apellido)
        txtFechana.setText(docente?.fecha.toString())
        txtAntiguiedad.setText(docente?.antiguedad.toString())
        txtCiudad.setText(docente?.ciudad.toString())
        txtEstado.setText(docente?.direccion)



        return convertView
    }

}
package bo.emi.ejemplo2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONTokener
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    private lateinit var btnNuevoEstudiante: Button
    private lateinit var lstListado: ListView
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNuevoEstudiante = findViewById(R.id.btnNuevoEstudiante)
        lstListado = findViewById(R.id.lstListado)

        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            run(getString(R.string.base_url) + "/estudiantes")
        }
        btnNuevoEstudiante.setOnClickListener {
            val intent = Intent(applicationContext, NuevoEstudiante::class.java)
            result.launch(intent)
        }

        run(getString(R.string.base_url) + "/estudiantes")
    }

    fun run(url: String) {
        GlobalScope.launch {
            val request = Request.Builder().url(url).get().build()
            val response = client.newCall(request).execute()

            val estudiantes = JSONTokener(response.body!!.string()).nextValue() as JSONArray
            val listaEstudiantes = arrayListOf<Estudiante>()

            for (i in 0 until estudiantes.length()) {
                val estudianteJson = estudiantes.getJSONObject(i)
                val formatter = SimpleDateFormat("yyyy-MM-dd")

                val estudiante = Estudiante(
                    estudianteJson.getLong("id"),
                    estudianteJson.getString("nombre"),
                    estudianteJson.getString("apellido"),
                    formatter.parse(estudianteJson.getString("fechaNacimiento")),
                    estudianteJson.getInt("semestre"),
                    estudianteJson.getInt(("idCiudad")),
                    estudianteJson.getString("ciudad"),
                    estudianteJson.getString("direccion")
                )

                listaEstudiantes.add(estudiante)
            }

            runOnUiThread {
                lstListado.adapter = EstudianteAdapter(applicationContext, listaEstudiantes)
            }
        }

    }
}
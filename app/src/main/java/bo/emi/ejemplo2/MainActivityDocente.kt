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

class MainActivityDocente: AppCompatActivity() {

    private lateinit var btnNuevoDocente: Button
    private lateinit var lstListado: ListView
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_docente)

        btnNuevoDocente = findViewById(R.id.btnNuevoDocente)
        lstListado = findViewById(R.id.lstListado)

        val result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            run(getString(R.string.base_url) + "/docente")
        }
        btnNuevoDocente.setOnClickListener {
            val intent = Intent(applicationContext, NuevoEstudiante::class.java)
            result.launch(intent)
        }

        run(getString(R.string.base_url) + "/docente")
    }
    fun run(url: String) {
        GlobalScope.launch {
            val request = Request.Builder().url(url).get().build()
            val response = client.newCall(request).execute()

            val docentes = JSONTokener(response.body!!.string()).nextValue() as JSONArray
            val listaDocentes = arrayListOf<Estudiante>()

            for (i in 0 until docentes.length()) {
                val docentesJson = docentes.getJSONObject(i)
                val formatter = SimpleDateFormat("yyyy-MM-dd")

                val estudiante = Estudiante(
                    docentesJson.getLong("id"),
                    docentesJson.getString("nombre"),
                    docentesJson.getString("apellido"),
                    formatter.parse(docentesJson.getString("fechaNacimiento")),
                    docentesJson.getInt("antiguiedada"),
                    docentesJson.getInt(("idCiudad")),
                    docentesJson.getString("estado"),
                    docentesJson.getString("direccion")
                )

                listaDocentes.add(estudiante)
            }

            runOnUiThread {
                lstListado.adapter = EstudianteAdapter(applicationContext, listaDocentes)
            }
        }

    }
}
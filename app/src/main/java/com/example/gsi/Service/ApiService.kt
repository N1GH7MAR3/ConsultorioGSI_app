package com.example.gsi.Service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gsi.*
import com.example.gsi.Adapter.EspecialidadAdapter
import com.example.gsi.Adapter.EspecialidadAdminAdapter
import com.example.gsi.Adapter.MedicoAdminAdapter
import com.example.gsi.Constans.Constant
import com.example.gsi.Entity.*
import com.example.gsi.databinding.ActivityEspecialidadesAdminBinding
import com.example.gsi.databinding.ActivityEspecialidadesPacienteBinding
import com.example.gsi.databinding.ActivityMedicosAdminBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(DelicateCoroutinesApi::class)
open class ApiService {
    private var c1 = GlobalScope.launch { }
    private var c2 = GlobalScope.launch { }
    private var c3 = GlobalScope.launch { }
    private var c4 = GlobalScope.launch { }
    private var c5 = GlobalScope.launch { }
    private var c6 = GlobalScope.launch { }
    private var c7 = GlobalScope.launch { }
    private var c8=GlobalScope.launch {  }

    fun verifyUser(
        activity: Activity,
        usuario: String,
        contraseña: String,
        username: EditText,
        password: EditText
    ) {
        c1 = GlobalScope.launch(Dispatchers.Main) {
            val user = ValidateUsuario(usuario, contraseña)
            val call = Constant.retrofit.verifyUser(user).enqueue(
                object : Callback<Usuario> {
                    override fun onResponse(
                        call: Call<Usuario>,
                        response: Response<Usuario>
                    ) {
                        activity.runOnUiThread {
                            if (response.code() == 404) {
                                Toast.makeText(activity, "Usuario no Registrado", Toast.LENGTH_LONG)
                                    .show()
                                call.cancel()
                            } else if (response.body()?.contraseña.toString() != contraseña) {
                                Toast.makeText(
                                    activity,
                                    "Contraseña Incorrecta",
                                    Toast.LENGTH_LONG
                                ).show()
                                call.cancel()
                            } else if (response.body()?.rol?.nombre.toString() == "admin") {
                                val intent = Intent(activity, DashboardAdminActivity::class.java)
                                intent.putExtra("nombre", response.body()?.usuario)
                                activity.startActivity(intent)
                                //activity.finish()
                                call.cancel()
                                c1.cancel()
                            } else {
                                username.setText("")
                                password.setText("")
                                searchPaciente(activity, usuario)
                                call.cancel()
                                c1.cancel()
                            }
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(activity, Constant.NoInternet, Toast.LENGTH_LONG).show()
                    }
                }
            )
        }
    }

    fun searchPaciente(activity: Activity, usuario: String) {
        c2 = GlobalScope.launch(Dispatchers.Main) {
            val pac = SearchUsuario(usuario)
            val call = Constant.retrofit.searchPaciente(pac).enqueue(
                object : Callback<Paciente> {
                    override fun onResponse(call: Call<Paciente>, response: Response<Paciente>) {
                        activity.runOnUiThread {
                            if (response.isSuccessful) {

                                val intent = Intent(activity, DashboardPacienteActivity::class.java)
                                intent.putExtra("nombre", response.body()?.nombre)
                                activity.startActivity(intent)
                                //activity.finish()
                                call.cancel()
                                c2.cancel()

                            }
                        }
                    }

                    override fun onFailure(call: Call<Paciente>, t: Throwable) {
                        Toast.makeText(activity, Constant.NoInternet, Toast.LENGTH_LONG).show()
                    }
                }
            )
        }

    }

    fun getEspecilidadesPaciente(
        activity: EspecialidadesPacienteActivity,
        binding: ActivityEspecialidadesPacienteBinding
    ) {
        fun iniRecyclerView(list: List<Especialidad>) {
            binding.rvEspecialidades.layoutManager =
                LinearLayoutManager(activity.applicationContext)
            binding.rvEspecialidades.adapter = EspecialidadAdapter(list)
        }
        c3 = GlobalScope.launch(Dispatchers.Main) {
            val call = Constant.retrofit.getAllEspecialidades()
                .enqueue(object : Callback<List<Especialidad>> {
                    override fun onResponse(
                        call: Call<List<Especialidad>>,
                        response: Response<List<Especialidad>>
                    ) {
                        activity.runOnUiThread {
                            val list: List<Especialidad> = response.body()!!
                            iniRecyclerView(list)
                            c3.cancel()
                            call.cancel()
                        }
                    }
                    override fun onFailure(call: Call<List<Especialidad>>, t: Throwable) {
                        Toast.makeText(activity, Constant.NoInternet, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
    fun getAllMedico(activity: MedicosAdminActivity,
                      binding: ActivityMedicosAdminBinding){
        fun iniRecyclerView(list: List<Medico>) {
            binding.rvMedico.layoutManager =
                LinearLayoutManager(activity.applicationContext)
            binding.rvMedico.adapter = MedicoAdminAdapter(list)
        }
        c8 = GlobalScope.launch(Dispatchers.Main) {
            val call=Constant.retrofit.getAllMedico().enqueue(object :Callback<List<Medico>>{
                override fun onResponse(
                    call: Call<List<Medico>>,
                    response: Response<List<Medico>>
                ) {
                    activity.runOnUiThread {
                        val list: List<Medico> = response.body()!!
                        iniRecyclerView(list)
                        c3.cancel()
                        call.cancel()
                    }
                }
                override fun onFailure(call: Call<List<Medico>>, t: Throwable) {
                    Log.e("hola",t.toString())
                }
            })
        }
    }

    fun getEspecilidadesAdmin(
        activity: EspecialidadesAdminActivity,
        binding: ActivityEspecialidadesAdminBinding
    ) {
        fun iniRecyclerView(list: List<Especialidad>) {
            binding.rvEspecialidades.layoutManager =
                LinearLayoutManager(activity.applicationContext)
            binding.rvEspecialidades.adapter = EspecialidadAdminAdapter(list)
        }
        c4 = GlobalScope.launch(Dispatchers.Main) {
            val call = Constant.retrofit.getAllEspecialidades()
                .enqueue(object : Callback<List<Especialidad>> {
                    override fun onResponse(
                        call: Call<List<Especialidad>>,
                        response: Response<List<Especialidad>>
                    ) {
                        activity.runOnUiThread {
                            val list: List<Especialidad> = response.body()!!
                            iniRecyclerView(list)
                            call.cancel()
                            c4.cancel()

                        }
                    }

                    override fun onFailure(call: Call<List<Especialidad>>, t: Throwable) {
                        Toast.makeText(activity, Constant.NoInternet, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }
    fun createEspecialidad(especialidad: createEspecialidad, context: Context) {
        c7 = GlobalScope.launch(Dispatchers.Main) {
            val call = Constant.retrofit.createEspecialidad(especialidad)
                .enqueue(object : Callback<createEspecialidad> {
                    override fun onResponse(
                        call: Call<createEspecialidad>,
                        response: Response<createEspecialidad>
                    ) {
                        (context as Activity).runOnUiThread {
                            val intent =
                                Intent(context, EspecialidadesAdminActivity::class.java)
                            Thread.sleep(3000)
                            context.startActivity(intent)
                            context.finish()
                            call.cancel()
                        }
                    }

                    override fun onFailure(call: Call<createEspecialidad>, t: Throwable) {
                        Toast.makeText(context, Constant.NoInternet, Toast.LENGTH_LONG).show()

                    }

                })
        }
    }

    fun updateEspecialidad(id: Long, especialidad: Especialidad, activity: Activity) {
        c5 = GlobalScope.launch(Dispatchers.Main) {
            val call = Constant.retrofit.updateEspecialidad(id, especialidad)
                .enqueue(object : Callback<Especialidad> {
                    override fun onResponse(
                        call: Call<Especialidad>,
                        response: Response<Especialidad>
                    ) {
                        activity.runOnUiThread {
                            Toast.makeText(
                                activity,
                                "Se ha Editado la Especialidad",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent =
                                Intent(activity, EspecialidadesAdminActivity::class.java)
                            activity.startActivity(intent)
                            activity.finish()
                        }
                    }

                    override fun onFailure(call: Call<Especialidad>, t: Throwable) {
                        Toast.makeText(activity, Constant.NoInternet, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }

    fun deleteEspecialidad(id: Long, activity: Activity) {
        c6 = GlobalScope.launch(Dispatchers.Main) {
            val call =
                Constant.retrofit.deleteEspecialidad(id).enqueue(object : Callback<Especialidad> {
                    override fun onResponse(
                        call: Call<Especialidad>,
                        response: Response<Especialidad>
                    ) {
                        activity.runOnUiThread {
                            Toast.makeText(
                                activity,
                                "Se ha eliminado la Especialidad",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            val intent =
                                Intent(
                                    activity.applicationContext,
                                    EspecialidadesAdminActivity::class.java
                                )
                            Thread.sleep(3000)
                            activity.finish()
                            activity.startActivity(intent)
                            call.cancel()
                        }
                    }
                    override fun onFailure(call: Call<Especialidad>, t: Throwable) {
                        Toast.makeText(activity, Constant.NoInternet, Toast.LENGTH_LONG).show()
                    }
                })
        }
    }




}






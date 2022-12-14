package com.example.gsi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gsi.Constans.Constant
import com.example.gsi.Entity.Cita
import com.example.gsi.databinding.ActivityDashboardPacienteBinding

class DashboardPacienteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardPacienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cardReservaCitas.isEnabled=false
        val usuario = intent.getStringExtra("usuario")

        val nombreu=intent.getStringExtra("nombreu").toString()

            Constant.api.searchPaciente(binding, usuario!!,nombreu)



        binding.cardEspecialidades.setOnClickListener {
            val intent = Intent(this@DashboardPacienteActivity, EspecialidadesPacienteActivity::class.java)
            startActivity(intent)
        }

        binding.cardCerrarSesion.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cardAcercaNosotros.setOnClickListener {
            val intent=Intent(this,AcercaNosotrosActivity::class.java)
            startActivity(intent)
        }
        binding.cardReservaCitas.setOnClickListener {
            val intent=Intent(this,ReservaCitaPacienteActivity::class.java)
            startActivity(intent)
        }
        binding.cardUbi.setOnClickListener {
            val intent= Intent(this,UbicacionActivity::class.java)
            startActivity(intent)

        }


    }
}
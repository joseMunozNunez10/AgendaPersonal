package com.jmdevs.agendapersonal

import android.Manifest
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.jmdevs.agendapersonal.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // Lanzador para solicitar el permiso estándar de notificaciones (para Android 13+)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // El permiso fue concedido por el usuario.
            // No es necesario hacer nada más aquí, la siguiente comprobación se hará igualmente.
        } else {
            // El usuario denegó el permiso. Mostramos un mensaje informativo.
            Toast.makeText(this, "El permiso para enviar notificaciones ha sido denegado.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- GESTIÓN DE PERMISOS ---
        // 1. Pedimos el permiso de notificaciones (POST_NOTIFICATIONS) si es necesario.
        askNotificationPermission()
        // 2. Comprobamos el permiso de alarmas exactas (SCHEDULE_EXACT_ALARM).
        checkAndRequestExactAlarmPermission()
        // -------------------------

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            if (NavigationUI.onNavDestinationSelected(item, navController)) {
                true
            } else {
                when (item.itemId) {
                    R.id.action_create_event -> {
                        try {
                            navController.navigate(R.id.eventEditFragment)
                        } catch (e: Exception) {
                            Toast.makeText(this, "Error al navegar a Crear Evento: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    /**
     * Inicia el proceso para solicitar el permiso de notificaciones (POST_NOTIFICATIONS).
     * Este es obligatorio a partir de Android 13 (TIRAMISU).
     */
    private fun askNotificationPermission() {
        // Solo es necesario en Android 13 o superior.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Comprobamos si el permiso NO ha sido concedido todavía.
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED) {
                // Si no lo tenemos, lanzamos el diálogo de solicitud del sistema.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
     * Comprueba si la app tiene permiso para programar alarmas exactas.
     * Si no lo tiene, redirige al usuario a los ajustes del sistema para que lo conceda.
     * Este permiso es crucial y el usuario puede revocarlo en cualquier momento.
     */
    private fun checkAndRequestExactAlarmPermission() {
        // Esta comprobación solo es relevante a partir de Android 12 (S),
        // que es cuando se introdujo este permiso gestionable por el usuario.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            // `canScheduleExactAlarms()` es la forma correcta de saber si tenemos el permiso.
            if (!alarmManager.canScheduleExactAlarms()) {
                // El permiso no está concedido. No podemos pedirlo con un diálogo normal.
                // Debemos enviar al usuario a la pantalla de ajustes específica.
                Intent(
                    Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                    // Apuntamos a los ajustes de nuestra app en concreto.
                    Uri.fromParts("package", packageName, null)
                ).also { intent ->
                    // Mostramos un Toast para explicar al usuario por qué le llevamos a ajustes.
                    Toast.makeText(this, "Se necesita permiso para programar alarmas. Actívalo, por favor.", Toast.LENGTH_LONG).show()
                    startActivity(intent)
                }
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null) || super.onSupportNavigateUp()
    }
}

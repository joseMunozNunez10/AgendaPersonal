package com.jmdevs.agendapersonal

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.jmdevs.agendapersonal.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(navController, null) || super.onSupportNavigateUp()
    }
}

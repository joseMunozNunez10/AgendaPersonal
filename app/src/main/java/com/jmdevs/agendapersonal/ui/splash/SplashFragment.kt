package com.jmdevs.agendapersonal.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmdevs.agendapersonal.R

class SplashFragment : Fragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Simula carga de recursos o verificación de PIN/Biometría
        view.postDelayed({
            val hasPin = checkPinSetup()
            if (hasPin) {
                findNavController().navigate(R.id.action_splash_to_login)
            } else {
                findNavController().navigate(R.id.action_splash_to_main)
            }
        }, 1000) // 1 segundo
    }

    private fun checkPinSetup(): Boolean {

        return false
    }
}

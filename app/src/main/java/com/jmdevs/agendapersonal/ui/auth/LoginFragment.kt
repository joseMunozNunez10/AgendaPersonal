package com.jmdevs.agendapersonal.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jmdevs.agendapersonal.R

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: agregar BiometricPrompt y PIN
        // Simulación de login exitoso
        view.postDelayed({
            findNavController().navigate(R.id.action_login_to_main)
        }, 500)
    }
}

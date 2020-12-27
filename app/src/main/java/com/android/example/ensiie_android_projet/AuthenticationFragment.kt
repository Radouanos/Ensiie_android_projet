package com.android.example.ensiie_android_projet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class AuthenticationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_authentication,container,false)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val login : Button =view.findViewById(R.id.login)
        val signup : Button = view.findViewById(R.id.signup)
        login.setOnClickListener {
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }
        signup.setOnClickListener{
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }
}
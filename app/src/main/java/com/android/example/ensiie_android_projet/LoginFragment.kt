package com.android.example.ensiie_android_projet

import android.os.Bundle
import android.provider.Settings.Global.putString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.android.example.ensiie_android_projet.network.Api
import com.android.example.ensiie_android_projet.network.UserService
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AuthenticationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    var mailValue:String?=null
    var passValue:String?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_login,container,false)
        return rootView
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val log = view.findViewById<Button>(R.id.login_login)
        val mail = view.findViewById<Button>(R.id.login_mail)
        val password = view.findViewById<Button>(R.id.login_password)
        log.setOnClickListener {
            mailValue = mail.text.toString()
            passValue = password.text.toString()

            if(mailValue.isNullOrEmpty() || passValue.isNullOrEmpty())
            {
                Toast.makeText(context, "Les valeus du mail ou/et mot de passe sont vides", Toast.LENGTH_LONG).show()
            } else  lifecycleScope.launch{
                    val logForm:LoginForm = LoginForm(mail=mailValue!!,password = passValue!!)
                    val response = Api.userService.login(logForm)
                    if(!response.isSuccessful) Toast.makeText(context,response.message(),Toast.LENGTH_LONG)
                    else {
                        PreferenceManager.getDefaultSharedPreferences(context).edit {
                            putString(SHARED_PREF_TOKEN_KEY, response.body()!!.token)
                        }
                    }
                }
        }
    }
}
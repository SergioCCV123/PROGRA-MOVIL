package com.lugares

import android.app.Notification.Action
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.lugares.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //Auth object
    private lateinit var auth : FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        binding.loginBtn.setOnClickListener { login() }
        binding.registerBtn.setOnClickListener { register() }
    }

     fun register(){
         var email = binding.emailTF.text.toString()
         var pwd = binding.pdwTF.text.toString()
         binding.root.isClickable = false
         auth.createUserWithEmailAndPassword(email,pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.root.isClickable = true
                    binding.loadingSpinner.visibility = View.GONE
                    Log.d("creando usuario", "Registrado")
                    actualiza(auth.currentUser)
                } else {
                    Log.d("creando usuario", "Error en registro")
                    Toast.makeText(baseContext, "Fallo", Toast.LENGTH_LONG).show()
                    binding.loadingSpinner.visibility = View.GONE
                    binding.root.isClickable = true
                }
            }
    }

     fun login(){
         var email = binding.emailTF.text.toString()
         var pwd = binding.pdwTF.text.toString()
         binding.loadingSpinner.visibility = View.VISIBLE
         binding.root.isClickable = false
         auth.signInWithEmailAndPassword(email,pwd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.root.isClickable = true
                    binding.loadingSpinner.visibility = View.GONE
                    Log.d("Autenticando", "Autenticado")
                    actualiza(auth.currentUser)
                } else {
                    Log.d("Autenticando", "Error en autenticacion")
                    Toast.makeText(baseContext, "Fallo", Toast.LENGTH_LONG).show()
                    binding.loadingSpinner.visibility = View.GONE
                    binding.root.isClickable = true
                }
            }
    }

     fun actualiza(user: FirebaseUser?) {
        if(user!=null){
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart(){
        super.onStart()
        actualiza(auth.currentUser)
    }
}
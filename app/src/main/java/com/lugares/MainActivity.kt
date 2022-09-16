package com.lugares

import android.app.Notification.Action
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
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
    }

    //Probando
    fun login(){

        var email = binding.emailTF.text
        var pwd = binding.pdwTF.text

        var resp = auth.createUserWithEmailAndPassword(email.toString(),pwd.toString())
        if(resp.isSuccessful){
            val mySnackbar = Snackbar.make(binding.root, "Usuario Creado", 700)
            mySnackbar.show()
        }else if(resp.isCanceled){
            val mySnackbar = Snackbar.make(binding.root, resp.result.toString(), 5)
            mySnackbar.show()
        }else{
            val mySnackbar = Snackbar.make(binding.root, "error", 700)
            mySnackbar.show()
        }
    }
}
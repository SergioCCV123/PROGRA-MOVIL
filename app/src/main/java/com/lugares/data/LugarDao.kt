package com.lugares.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import com.lugares.model.Lugar

class LugarDao {
    //Funciones de bajo nivel para un CRUD

    private val coleccion1 = "lugaresApp"
    private val usuario = Firebase.auth.currentUser?.email.toString()
    private val coleccion2 = "misLugares"

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    suspend fun saveLugar(lugar: Lugar) {
        val doc : DocumentReference

        if (lugar.id.isEmpty()){
            doc = firestore.collection(coleccion1).document(usuario).collection(coleccion2).document()
            lugar.id = doc.id
        }else{
            doc = firestore.collection(coleccion1).document(usuario).collection(coleccion2).document(lugar.id)
        }
        doc.set(lugar)
            .addOnSuccessListener { Log.d("saveLugar", "Lugar creado/actualizado") }
            .addOnCanceledListener { Log.d("saveLugar", "Lugar NO creado/actualizado") }
    }


    suspend fun deleteLugar(lugar: Lugar) {
        if (lugar.id.isNotEmpty()){
            firestore.collection(coleccion1).document(usuario).collection(coleccion2).document(lugar.id).delete()
                .addOnSuccessListener { Log.d("saveLugar", "Lugar eliminado") }
                .addOnCanceledListener { Log.d("saveLugar", "Lugar NO eliminado") }
        }
    }

    fun getLugares() : MutableLiveData<List<Lugar>> {
        val listaLugares = MutableLiveData<List<Lugar>>()

        firestore.collection(coleccion1).document(usuario).collection(coleccion2)
            .addSnapshotListener { instant, e ->
                if(e != null) { //error
                    return@addSnapshotListener
                }
                if(instant != null){
                    val lista = ArrayList<Lugar>()

                    instant.documents.forEach {
                        val lugar = it.toObject(Lugar::class.java)
                        if(lugar != null){
                            lista.add(lugar)
                        }
                    }

                    listaLugares.value = lista
                }
            }


        return listaLugares
    }

}
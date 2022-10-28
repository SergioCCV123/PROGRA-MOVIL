package com.lugares.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.lugares.data.LugarDatabase
import com.lugares.model.Lugar
import com.lugares.repository.LugarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application) {

    private val lugarRepository : LugarRepository
    val getLugares : LiveData<List<Lugar>>

    init{
        val lugarDao = LugarDatabase.getDatabase(application).lugarDao()
        lugarRepository = LugarRepository(lugarDao)
        getLugares = lugarRepository.getLugares
    }

    fun saveLugar (lugar: Lugar){
        viewModelScope.launch(Dispatchers.IO) {
            lugarRepository.saveLugar(lugar)
        }
    }

    fun deleteLugar (lugar: Lugar){
        viewModelScope.launch(Dispatchers.IO) {
            lugarRepository.deleteLugar(lugar)
        }
    }

}
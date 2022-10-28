package com.lugares.ui.lugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.lugares.R
import com.lugares.databinding.FragmentAddLugarBinding
import com.lugares.databinding.FragmentLugarBinding
import com.lugares.model.Lugar
import com.lugares.viewmodel.LugarViewModel

class AddLugarFragment : Fragment() {


    private var _binding: FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lugarViewModel =
            ViewModelProvider(this).get(LugarViewModel::class.java)

        _binding = FragmentAddLugarBinding.inflate(inflater, container, false)

        binding.btAdd.setOnClickListener{ addLugar()}

        return binding.root
    }

    private fun addLugar() {
        val nombre = binding.etNombre.text.toString()
        if(nombre.isNotEmpty()){
            val correo = binding.etCorreo.text.toString()
            val telefono = binding.etTelefono.text.toString()
            val sitio = binding.etSitio.text.toString()
            val lugar = Lugar(0, nombre,correo,telefono,sitio,0.0,0.0,0.0,"","")

            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(), getString(R.string.msg_lugar_Added), Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_addLugarFragment_to_nav_lugar)
        }else{
            Toast.makeText(requireContext(), getString(R.string.msg_data), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
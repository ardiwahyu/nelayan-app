package com.github.nelayanapp.ui.daftar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.nelayanapp.R
import com.github.nelayanapp.databinding.ActivityDaftarBinding
import com.github.nelayanapp.ui.verifikasi.VerifikasiNomorActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jetbrains.anko.intentFor

@AndroidEntryPoint
class DaftarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDaftarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        binding.btnLanjut.setOnClickListener {
            startActivity(intentFor<VerifikasiNomorActivity>())
        }
    }
}
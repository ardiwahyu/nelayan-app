package com.github.nelayanapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestOptions
import com.github.nelayanapp.R
import com.github.nelayanapp.databinding.ActivityLoginBinding
import com.github.nelayanapp.ui.daftar.DaftarActivity
import com.github.nelayanapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.intentFor

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUi()
    }

    private fun initUi() {
        Glide.with(this)
            .load(ContextCompat.getDrawable(this, R.mipmap.bg))
            .centerInside()
            .transform(MultiTransformation(
                CenterCrop(),
                GranularRoundedCorners(0f,0f,100f,100f)
                )
            ).into(ivBg)

        binding.btnLogin.setOnClickListener {
            startActivity(intentFor<MainActivity>())
        }

        binding.tvDaftar.setOnClickListener {
            startActivity(intentFor<DaftarActivity>())
        }
    }
}
package com.example.swedishkeyboard

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.swedishkeyboard.classes.Misc
import com.example.swedishkeyboard.databinding.ActivityAllDoneBinding

class AllDoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllDoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllDoneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLetsGo.setOnClickListener {
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }

        Handler().postDelayed({
            Misc.zoomInView(binding.textView3, this, 250)
            Handler().postDelayed({
                Misc.zoomInView(binding.tvKbSelected, this, 250)
                Handler().postDelayed({
                    Misc.zoomInView(binding.tvThemes, this, 250)
                    Handler().postDelayed({
                        Misc.zoomInView(binding.btnLetsGo, this, 250)
                    }, 500)
                }, 500)
            }, 500)
        }, 500)

    }
}
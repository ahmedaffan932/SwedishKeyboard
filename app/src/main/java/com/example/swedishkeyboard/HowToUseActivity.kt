package com.example.swedishkeyboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.swedishkeyboard.classes.Misc
import com.example.swedishkeyboard.databinding.ActivityHowToUseBinding

class HowToUseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHowToUseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHowToUseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Misc.InitTopBar(this, "How to use")
    }
}
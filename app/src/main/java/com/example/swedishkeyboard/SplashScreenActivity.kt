package com.example.swedishkeyboard

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.swedishkeyboard.databinding.ActivitySplashScreenBinding
import com.example.swedishkeyboard.services.CustomInputMethodService
import com.example.swedishkeyboard.MainActivity
import com.example.swedishkeyboard.classes.Misc

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras?.get(Misc.logKey) != null){
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Misc.logKey, Misc.logKey)
            startActivity(intent)
            finish()
        }

        binding.btnStart.setOnClickListener {
            if(isInputMethodSelected()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, EnableKeyboardActivity::class.java))
                finish()
            }
        }
    }

    private fun isInputMethodSelected(): Boolean {
        val id: String = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )
        val defaultInputMethod = ComponentName.unflattenFromString(id)
        val myInputMethod = ComponentName(this, CustomInputMethodService::class.java)
        return myInputMethod == defaultInputMethod
    }

}
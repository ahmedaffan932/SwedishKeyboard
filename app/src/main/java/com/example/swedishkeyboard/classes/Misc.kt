package com.example.swedishkeyboard.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.swedishkeyboard.BuildConfig
import com.example.swedishkeyboard.R
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class Misc {
    companion object {

        const val suggestions = "suggestions"
        const val sound = "sounds"
        const val vibrate = "vibration"
        private const val keyboardSize = "keyboardSize"
        const val themeFromGallery: String = "themeFromGallery"
        const val data: String = "data"
        const val logKey = "logKey"
        private const val themeKey = "themeKey"
        private const val selectedKeyboard = "selectedKeyboard"
        private const val kyeBackground = "kyeBackground"
        const val appUrl: String =
            "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"

        fun getTheme(context: Context): Int {
            val sharedPreferences =
                context.getSharedPreferences(themeKey, Context.MODE_PRIVATE)
            return sharedPreferences.getInt(themeKey, 27)
        }


        fun zoomInView(view: View, activity: Activity, duration: Int) {
            val a: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.zoom_in)
            a.duration = duration.toLong()
            view.startAnimation(a)
        }

        fun setTheme(context: Context) {
            val sharedPreferences =
                context.getSharedPreferences(themeKey, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            if (getTheme(context) > 20) {
                editor.putInt(themeKey, 0)
            } else {
                editor.putInt(themeKey, getTheme(context) + 1)
            }
            editor.apply()
        }

        fun setTheme(context: Context, themeId: Int) {
            val sharedPreferences =
                context.getSharedPreferences(themeKey, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(themeKey, themeId)
            editor.apply()
        }

        fun setIsSwedish(context: Context, isSwedish: Boolean) {
            val sharedPreferences =
                context.getSharedPreferences(selectedKeyboard, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(selectedKeyboard, isSwedish)
            editor.apply()
        }


        fun getIsSwedish(context: Context): Boolean {
            val sharedPreferences =
                context.getSharedPreferences(selectedKeyboard, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(selectedKeyboard, false)

        }

        fun isKeyBackgroundEnable(context: Context): Boolean {
            val sharedPreferences =
                context.getSharedPreferences(kyeBackground, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(kyeBackground, true)
        }

        fun setIsKeyBackgroundEnable(context: Context, isKeyBackground: Boolean) {
            val sharedPreferences =
                context.getSharedPreferences(kyeBackground, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(kyeBackground, isKeyBackground)
            editor.apply()
        }
        @SuppressLint("MissingPermission")
        fun checkInternetConnection(context: Context): Boolean {
            //Check internet connection:
            val connectivityManager: ConnectivityManager? =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            //Means that we are connected to a network (mobile or wi-fi)
            return connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state === NetworkInfo.State.CONNECTED ||
                    connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state === NetworkInfo.State.CONNECTED
        }


        fun InitTopBar(activity: Activity, screenName: String){
            activity.findViewById<TextView>(R.id.tvScreenName).text = screenName
            activity.findViewById<ImageView>(R.id.btnBack).setOnClickListener {
                activity.onBackPressed()
            }
        }

        fun setIsSettingEnable(context: Context, isSettingEnabled: Boolean, settingName: String) {
            val sharedPreferences =
                context.getSharedPreferences(settingName, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(settingName, isSettingEnabled)
            editor.apply()
        }

        fun setIsKeyboardSizeLarge(context: Context, bool: Boolean) {
            val sharedPreferences =
                context.getSharedPreferences(keyboardSize, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(keyboardSize, bool)
            editor.apply()
        }

        fun getIsKeyboardSizeLarge(context: Context):Boolean {
            val sharedPreferences =
                context.getSharedPreferences(keyboardSize, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(keyboardSize, false)
        }

        fun getIsSettingEnable(context: Context, settingName: String): Boolean {
            val sharedPreferences =
                context.getSharedPreferences(settingName, Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean(settingName, true)
        }

        fun downloadTranslationModel(context: Context){
            if (checkInternetConnection(context)){

                val options = TranslatorOptions.Builder()
                    .setSourceLanguage(TranslateLanguage.ENGLISH)
                    .setTargetLanguage(TranslateLanguage.SWEDISH)
                    .build()
                val englishSpanishTranslator = Translation.getClient(options)

                englishSpanishTranslator.downloadModelIfNeeded()
                    .addOnSuccessListener {
                        setAToBDownloadStatus(context, true)
                        val optionsNew = TranslatorOptions.Builder()
                            .setSourceLanguage(TranslateLanguage.SWEDISH)
                            .setTargetLanguage(TranslateLanguage.ENGLISH)
                            .build()
                        val spanishToEnglishTranslator = Translation.getClient(optionsNew)
                        spanishToEnglishTranslator.downloadModelIfNeeded()
                            .addOnSuccessListener {
                                setBToADownloadStatus(context, true)
                                Log.d(logKey, "spanishToEnglishTranslator")
                            }
                            .addOnFailureListener { exception ->
                                exception.printStackTrace()
                            }

                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }

            }else{
                if(!isAToBDownloaded(context) || !isBToADownloaded(context)){
                    Toast.makeText(context, "Please check your internet.", Toast.LENGTH_SHORT).show()
                }
            }
        }


        fun isBToADownloaded(context: Context):Boolean{
            val sharedPreferences =
                context.getSharedPreferences("bToA", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("bToA", false)
        }
        fun isAToBDownloaded(context: Context):Boolean{
            val sharedPreferences =
                context.getSharedPreferences("aToB", Context.MODE_PRIVATE)
            return sharedPreferences.getBoolean("aToB", false)
        }
        fun setAToBDownloadStatus(context: Context, isDownloaded: Boolean){
            val sharedPreferences =
                context.getSharedPreferences("aToB", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("aToB", isDownloaded)
            editor.apply()
        }
        fun setBToADownloadStatus(context: Context, isDownloaded: Boolean){
            val sharedPreferences =
                context.getSharedPreferences("bToA", AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("bToA", isDownloaded)
            editor.apply()
        }
    }
}
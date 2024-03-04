package com.example.tourmanage.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.tourmanage.R
import com.example.tourmanage.common.data.IntentData
import com.example.tourmanage.common.di.FireBaseModule
import com.example.tourmanage.common.util.UiController
import com.example.tourmanage.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Inject lateinit var database: FireBaseModule
    @Inject lateinit var client: OkHttpClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        Timber.d("onCreate()")
        moveToActivity()
    }


    private fun moveToActivity() {
        UiController.addActivity(this, MainActivity2::class, IntentData(
            mapOf("key1" to "value1", "key2" to "value2")))
    }

    private fun testFirebase() {
        val ref = database.getReference("Test")
        ref.setValue("12345")
    }

}
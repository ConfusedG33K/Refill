package com.example.refill

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import com.example.refill.data.AppDatabase
import com.example.refill.data.RefillRepository
import com.example.refill.ui.HomeScreen
import com.example.refill.ui.RefillViewModel
import com.example.refill.ui.RefillViewModelFactory
import com.example.refill.ui.theme.RefillTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = RefillRepository(database.refillDao())
        val factory = RefillViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[RefillViewModel::class.java]

        setContent {
            RefillTheme {
                HomeScreen(viewModel = viewModel)
            }
        }
    }
}

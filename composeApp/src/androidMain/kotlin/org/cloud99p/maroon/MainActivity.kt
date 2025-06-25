package org.cloud99p.maroon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dbBuilder = DatabaseFactory(applicationContext).createDatabaseBuilder()
        val preferences = createDataStore(applicationContext)
        ViewModel.initial(dbBuilder, preferences)

        setContent {
            App()
        }
    }
}

package org.cloud99p.maroon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.cloud99p.maroon.data.local.DatabaseFactory
import org.cloud99p.maroon.data.local.createDataStore
import org.cloud99p.maroon.data.local.createDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val dbBuilder = DatabaseFactory(applicationContext).createDatabaseBuilder()
            val db = createDatabase(dbBuilder)
            App(
                db = db,
                prefs = remember {
                    createDataStore(applicationContext)
                }
            )
        }
    }
}

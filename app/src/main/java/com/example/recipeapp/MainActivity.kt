package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column (
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Hey look some text")
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Button(onClick = {}) {
                    Text(text = "A Button")
                }
            }
        }
    }
}
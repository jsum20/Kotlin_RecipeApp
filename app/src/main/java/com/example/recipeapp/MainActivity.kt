package com.example.recipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        data class ItemData(val name: String, val calories: String, val price: String)

        setContent {
            val items = listOf(ItemData("Happy meal", "800 kcal", "£1.99"))

            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFFF2F2F2))
            ) {
                items(items) { item ->
                    Image(
                        painter = painterResource(id = R.drawable.happy_meal_small),
                        contentDescription = "content description",
                        modifier = Modifier.height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = item.name,
                            style = TextStyle(fontSize = 26.sp)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = item.calories,
                            style = TextStyle(fontSize = 17.sp)
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Text(
                            text = item.price,
                            style = TextStyle(color = Color.Green, fontSize = 16.sp)
                        )
                    }
                }
            }
        }
    }
}
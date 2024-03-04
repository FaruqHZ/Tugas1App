package com.example.tugas1app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas1app.ui.theme.Tugas1AppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Tugas1AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "calculator") {
                        composable("calculator") {
                            Calculator(navController = navController)
                        }
                        composable("result/{panjang}/{lebar}/{luas}/{keliling}") { backStackEntry ->
                            val panjang =
                                backStackEntry.arguments?.getString("panjang")?.toFloatOrNull()
                                    ?: 0f
                            val lebar =
                                backStackEntry.arguments?.getString("lebar")?.toFloatOrNull() ?: 0f
                            val luas =
                                backStackEntry.arguments?.getString("luas")?.toFloatOrNull() ?: 0f
                            val keliling =
                                backStackEntry.arguments?.getString("keliling")?.toFloatOrNull()
                                    ?: 0f
                            ResultPage(panjang, lebar, luas, keliling)
                        }
                    }
                }
            }
        }
    }
}

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val panjang = intent.getFloatExtra("panjang", 0f)
            val lebar = intent.getFloatExtra("lebar", 0f)
            val luas = intent.getFloatExtra("luas", 0f)
            val keliling = intent.getFloatExtra("keliling", 0f)

            ResultPage(panjang, lebar, luas, keliling)
        }
    }
}

@Composable
fun ResultPage(panjang: Float, lebar: Float, luas: Float, keliling: Float) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Panjang: $panjang M", modifier = Modifier.padding(bottom = 8.dp))
        Text("Lebar: $lebar M", modifier = Modifier.padding(bottom = 8.dp))
        Text("Luasnya adalah: $luas M", modifier = Modifier.padding(bottom = 8.dp))
        Text("Kelilingnya adalah: $keliling M")
    }
}

@Composable
fun Calculator(navController: NavController) {
    var panjang by remember { mutableStateOf("") }
    var lebar by remember { mutableStateOf("") }

    var luas by remember { mutableFloatStateOf(0f) }
    var keliling by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = panjang,
            onValueChange = { panjang = it },
            label = { Text("Panjang") },
            modifier = Modifier.padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = lebar,
            onValueChange = { lebar = it },
            label = { Text("Lebar") },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (panjang.isNotEmpty() && lebar.isNotEmpty()) {
                    val l = panjang.toFloat()
                    val w = lebar.toFloat()

                    luas = l * w
                    keliling = 2 * (l + w)

                    navController.navigate("result/$l/$w/$luas/$keliling")
                }
            },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Hitung")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Tugas1AppTheme {
        val navController = rememberNavController()
        Calculator(navController)
    }
}
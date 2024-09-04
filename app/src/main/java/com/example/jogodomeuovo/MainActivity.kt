package com.example.jogodomeuovo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jogodomeuovo.ui.theme.JogodomeuovoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JogodomeuovoTheme {
                var cliquesNecessarios by remember { mutableDoubleStateOf(50.0) }
                var cliquesDados by remember { mutableDoubleStateOf(0.0) }
                var mensagem by remember { mutableStateOf("Início") }
                var desistiu by remember { mutableStateOf(false) }

                val imagemFundo: Painter = when {
                    desistiu -> painterResource(id = R.drawable.imagem_desistencia)
                    cliquesDados <= (cliquesNecessarios * 0.33) -> painterResource(id = R.drawable.imagem_inicial)
                    cliquesDados > (cliquesNecessarios * 0.33) && cliquesDados <= (cliquesNecessarios * 0.66) -> painterResource(id = R.drawable.imagem_mediana)
                    cliquesDados > (cliquesNecessarios * 0.66) && cliquesDados <= (cliquesNecessarios * 0.99) -> painterResource(id = R.drawable.imagem_final)
                    else -> painterResource(id = R.drawable.imagem_conquista)
                }

                if (desistiu) {
                    mensagem = "Você desistiu e não alcançou o objetivo"
                } else if (cliquesDados <= (cliquesNecessarios * 0.33)) {
                    mensagem = "Início"
                } else if (cliquesDados > (cliquesNecessarios * 0.33) && cliquesDados <= (cliquesNecessarios * 0.66)) {
                    mensagem = "Meio"
                } else if (cliquesDados > (cliquesNecessarios * 0.66) && cliquesDados <= (cliquesNecessarios * 0.99)) {
                    mensagem = "Proximo do objetivo"
                } else if (cliquesDados > (cliquesNecessarios * 0.99)) {
                    mensagem = "Objetivo Alcançado"
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Saudacao(
                        nome = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
                    ) {
                        Image(
                            painter = imagemFundo,
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Texto Jornada
                        Box(modifier = Modifier.size(640.dp), contentAlignment = Alignment.TopCenter) {
                            Text(
                                text = mensagem,
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                textAlign = TextAlign.Center,
                            )
                        }

                        // Texto Necessários
                        Box(modifier = Modifier.size(width = 330.dp, height = 540.dp)) {
                            Text(
                                text = "Clique $cliquesNecessarios vezes para alcançar o objetivo",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                textAlign = TextAlign.Center,
                            )
                        }

                        // Texto Cliques
                        Box(modifier = Modifier.size(460.dp), contentAlignment = Alignment.Center) {
                            Text(
                                text = "Seus Cliques: $cliquesDados",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                            )
                        }

                        // Botão Cliques
                        Box(modifier = Modifier.size(170.dp), contentAlignment = Alignment.Center) {
                            Button(
                                onClick = {
                                    if (!desistiu) {
                                        cliquesDados++
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Green,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.size(width = 250.dp, height = 70.dp)
                            ) {
                                Text(
                                    text = "Clique",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                )
                            }
                        }

                        // Botão Desistir
                        Box(modifier = Modifier.size(width = 250.dp, height = 330.dp), contentAlignment = Alignment.Center) {
                            Button(
                                onClick = {
                                    desistiu = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.size(width = 200.dp, height = 60.dp)
                            ) {
                                Text(
                                    text = "Desistir",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 25.sp,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun Saudacao(nome: String, modifier: Modifier = Modifier) {
        Text(
            text = ""
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun SaudacaoPreview() {
        JogodomeuovoTheme {
            Saudacao("Android")
        }
    }
}

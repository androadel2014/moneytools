package com.andrewmetry.moneytools
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
val Navy = Color(0xFF0E1B2C)
val CardNavy = Color(0xFF16283D)
val MoneyGreen = Color(0xFF16B364)
val MoneyGold = Color(0xFFF5B83D)
val WhiteText = Color.White
val SoftText = Color(0xFFC7D3E0)
@Composable fun MoneyToolsTheme(content: @Composable () -> Unit) { MaterialTheme(colorScheme = darkColorScheme(background = Navy, surface = CardNavy, primary = MoneyGreen, secondary = MoneyGold, onBackground = WhiteText, onSurface = WhiteText), content = content) }
@Composable fun ToolCard(title: String, subtitle: String, accent: Color, onClick: () -> Unit) { Card(colors = CardDefaults.cardColors(containerColor = CardNavy), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp).clickable { onClick() }) { Column(Modifier.padding(20.dp)) { Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = WhiteText); Text(subtitle, fontSize = 15.sp, color = SoftText); Text("Open", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = accent, modifier = Modifier.padding(top = 8.dp)) } } }
@Composable fun GreenButton(label: String, onClick: () -> Unit) { Button(onClick = onClick, colors = ButtonDefaults.buttonColors(containerColor = MoneyGreen, contentColor = Color(0xFF0E1B2C)), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) { Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 4.dp)) } }
@Composable fun ResultCard(headlineLabel: String, headlineValue: String, lines: List<Pair<String, String>>) { Card(colors = CardDefaults.cardColors(containerColor = CardNavy), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) { Column(Modifier.padding(20.dp)) { Text(headlineLabel, fontSize = 15.sp, color = SoftText); Text(headlineValue, fontSize = 38.sp, fontWeight = FontWeight.Bold, color = MoneyGold); for (pair in lines) { Text(pair.first + ": " + pair.second, fontSize = 17.sp, color = WhiteText, modifier = Modifier.padding(top = 6.dp)) } } } }
@Composable fun ScreenShell(title: String, content: @Composable () -> Unit) { Column(Modifier.background(Navy).padding(20.dp)) { Text(title, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = WhiteText, modifier = Modifier.padding(bottom = 12.dp)); content() } }

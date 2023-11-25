package gustavo.projects.moviemanager

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun MainApp() {
    MaterialTheme {
        Text(
            text = "Blank screen",
            color = Color.Black,
            fontSize = 25.sp
        )
    }
}
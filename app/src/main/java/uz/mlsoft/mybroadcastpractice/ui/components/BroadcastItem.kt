package uz.mlsoft.mybroadcastpractice.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.mlsoft.mybroadcastpractice.R
import uz.mlsoft.mybroadcastpractice.ui.theme.MyBroadcastPracticeTheme

@Composable
fun BroadcastItem(
    name: String,
    isChecked: Boolean,
    image: Int,
    checkingReceiver: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(90.dp)
            .background(color = Color.LightGray)
            .clip(RoundedCornerShape(15.dp))
    ) {
        Row(modifier = Modifier.fillMaxHeight()) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 15.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(CenterVertically), contentScale = ContentScale.Crop
            )

            Text(
                text = name,
                fontSize = 22.sp,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .align(CenterVertically), fontWeight = FontWeight.SemiBold
            )
        }
        Checkbox(
            checked = isChecked,
            onCheckedChange = checkingReceiver,
            modifier = Modifier
                .padding(end = 10.dp)
                .align(CenterEnd),
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Cyan,
                uncheckedColor = Color.Magenta,
                checkmarkColor = Color.Black,
                disabledUncheckedColor = Color.Red
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BroadcastPrev() {
    MyBroadcastPracticeTheme() {
        BroadcastItem(
            name = "Wi-fi cheking",
            isChecked = true,
            image = R.drawable.internet,
            checkingReceiver = {}
        )
    }
}
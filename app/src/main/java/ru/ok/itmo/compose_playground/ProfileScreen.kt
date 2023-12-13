package ru.ok.itmo.compose_playground

import ProfileViewModel
import UserVO
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.ok.itmo.compose_playground.ui.theme.AppTextStyle
import ru.ok.itmo.compose_playground.ui.theme.Compose_playgroundTheme
import ru.ok.itmo.compose_playground.ui.theme.Purple80
import ru.ok.itmo.compose_playground.ui.theme.colorDisabled

val paddingModifier = Modifier.padding(horizontal = 16.dp)
val avatarOffset = 20.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onSectionClicked: (UserVO.Section) -> Unit,
) {
    val user by viewModel.userFlow.collectAsState() // State<User>
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Purple80)
                .statusBarsPadding()
                .offset(y = avatarOffset)
        ) {
            TopAppBar(title = { /*TODO*/ }, navigationIcon = {
                IconButton(
                    onClick = { onBack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(y = -avatarOffset)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = Color.White
                    )
                }
            }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent))
            AsyncImage(
                model = user.avatar,
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = avatarOffset)
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .background(colorDisabled)
                    .size(112.dp)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(avatarOffset))
            ProfileInfo(user.name, user.phone)
            ProfileSections(user.sections, onSectionClicked)
        }
    }
}

@Composable
private fun ProfileSections(
    sections: List<UserVO.Section>,
    onSectionClicked: (UserVO.Section) -> Unit
) {
    sections.forEach { section ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSectionClicked(section) }
                .padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = section.icon,
                contentDescription = section.text,
                modifier = Modifier.padding(vertical = 12.dp),
                tint = Color.Black,
            )
            Text(
                text = section.text,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
            )
        }
    }
}

@Composable
private fun ProfileInfo(name: String, phone: String) {
    Text(
        text = name,
        style = AppTextStyle.title17Medium,
        textAlign = TextAlign.Center,
        modifier = paddingModifier.padding(top = 12.dp)
    )
    Text(
        text = phone,
        style = AppTextStyle.text15,
        textAlign = TextAlign.Center,
        modifier = paddingModifier.padding(top = 4.dp)
    )
    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    Compose_playgroundTheme {
        ProfileScreen(viewModel = ProfileViewModel(), {}, {})
    }
}
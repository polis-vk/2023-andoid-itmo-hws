import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val user = UserVO(
        "Варя Попова",
        "+7 (999) 888 7766",
        "https://images.unsplash.com/photo-1563987219716-dac41f2d0b3a?q=80&w=2677&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        sections = (1..5).map {
            UserVO.Section(Icons.Outlined.Notifications, "Раздел $it")
        }
    )
    val userFlow: StateFlow<UserVO> = MutableStateFlow(user)
}

data class UserVO(
    val name: String,
    val phone: String,
    val avatar: String,
    val sections: List<Section>,
) {
    data class Section(
        val icon: ImageVector,
        val text: String,
    )
}
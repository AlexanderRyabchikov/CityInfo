package cityinfo.io

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import cityinfo.io.init.AppContainer
import cityinfo.io.init.AppMainContent

class StartActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContainer {
                AppMainContent()
            }
        }
    }
}
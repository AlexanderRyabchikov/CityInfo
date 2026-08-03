import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import settings.androidApplicationSetup
import settings.androidLibrarySetup
import settings.composeLibrarySetup
import settings.featureApiSetup
import settings.featureUiSetup

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.application.get().pluginId)
            apply(libs.plugins.compose.compiler.get().pluginId)
            apply(libs.plugins.kotlin.serialx.get().pluginId)
        }
        extensions.configure<ApplicationExtension>(::androidApplicationSetup)
    }
}

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.android.library.get().pluginId)
        }
        extensions.configure<LibraryExtension>(::androidLibrarySetup)
    }
}

class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.setup.android.library.get().pluginId)
            apply(libs.plugins.compose.compiler.get().pluginId)
        }
        extensions.configure<LibraryExtension>(::composeLibrarySetup)
    }
}

class FeatureApiPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.setup.android.library.get().pluginId)
            apply(libs.plugins.kotlin.serialx.get().pluginId)
        }
        extensions.configure<LibraryExtension>(::featureApiSetup)
    }
}

class FeatureUiPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(pluginManager) {
            apply(libs.plugins.setup.compose.library.get().pluginId)
            apply(libs.plugins.kotlin.serialx.get().pluginId)
        }
        extensions.configure<LibraryExtension>(::featureUiSetup)
    }
}
package cityinfo.io.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

private val ktorClient: Module = module {
    single {
        HttpClient(OkHttp.create{}) {
            expectSuccess = true
            installJson()
            installDefaultRequest(baseUrl = BuildConfig.API_BASE_URL)
            installTimeOut()
            installLogger()
        }
    }
}

val networkModule = module {
    includes(ktorClient)
}
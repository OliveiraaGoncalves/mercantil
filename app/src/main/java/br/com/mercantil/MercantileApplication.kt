package br.com.mercantil

import android.app.Application
import br.com.mercantil.core.di.StorageModule
import br.com.mercantil.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MercantileApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MercantileApplication)
            modules(StorageModule.instance, AppModule.instance)
        }
    }
}
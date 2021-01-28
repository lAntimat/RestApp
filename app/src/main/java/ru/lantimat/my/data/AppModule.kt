package ru.lantimat.my.data

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.lantimat.my.presentation.menulist.MenuListViewModel

val appModule = module {

    //single { FirebaseAnalytics.getInstance(androidContext()) }

    //region Interactor

//    factory { DownloadObserver(get(), get()) }

    viewModel { MenuListViewModel(get()) }
}
package ru.lantimat.my.data

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.lantimat.my.data.local.DishesDatabase


private const val BOOK_CHUCK_INTERCEPTOR = "BOOK_CHUCK"

private const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"

private const val HEADERS_INTERCEPTOR = "HEADERS_INTERCEPTOR"

private const val BOOK_AUTH_INTERCEPTOR = "BOOK_AUTH"

private const val BOOK_API_URL = "BOOK_API_URL"

private const val BOOK_HTTP_CLIENT = "BOOK_HTTP_CLIENT"

private const val BOOK_RETROFIT = "BOOK_RETROFIT"

private const val HTTP_CONNECT_TIMEOUT_SECONDS = 5L

private const val HTTP_READ_TIMEOUT_SECONDS = 10L

private const val HEADER_APPLICATION_PLATFORM = "Application-Platform"


val dataModule = module {

    //region Cache

    single { ResponseHandler(get<Context>().resources) }

    single { DataSource(androidContext()) }

    //endregion

//    single {
//        val booksFolder = File(androidContext().filesDir, "books")
//        BookFileStorage(booksFolder)
//    }

    //region Room database

    single {
        val dbClass = DishesDatabase::class.java
        val dbName = "appDb.db"

        Room.databaseBuilder(androidContext(), dbClass, dbName)
//          .addMigrations(MIGRATION_1_2) // add migrations here when schema changes
            .fallbackToDestructiveMigrationOnDowngrade()
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<DishesDatabase>().basketDishDao }
    single { get<DishesDatabase>().menuDao }

    //endregion

    //region REST API

    single {
        Gson()
    }

//    single<Interceptor>(named(BOOK_AUTH_INTERCEPTOR)) {
//        AuthTokenInterceptor(get())
//    }

    single<Interceptor>(named(LOGGING_INTERCEPTOR)) {
        HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
    single<Interceptor>(named(HEADERS_INTERCEPTOR)) {
        Interceptor.invoke { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                //.header(HEADER_APPLICATION_PLATFORM, APPLICATION_PLATFORM_ANDROID)
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }
    }

//    single(named(BOOK_HTTP_CLIENT)) {
//        val builder = OkHttpClient.Builder()
//            .connectTimeout(HTTP_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//            .readTimeout(HTTP_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
//            .addInterceptor(get<Interceptor>(named(BOOK_CHUCK_INTERCEPTOR)))
//            .addInterceptor(get<Interceptor>(named(HEADERS_INTERCEPTOR)))
////            .addInterceptor(get<Interceptor>(named(BOOK_AUTH_INTERCEPTOR)))
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(get<Interceptor>(named(LOGGING_INTERCEPTOR)))
//        }
//        builder.build()
//    }
//
//    single(named(BOOK_API_URL)) {
//        BuildConfig.BOOK_API_URL
//    }
//
//    single(named(BOOK_RETROFIT)) {
//        Retrofit.Builder()
//            .baseUrl(get<String>(named(BOOK_API_URL)))
//            .client(get(named(BOOK_HTTP_CLIENT)))
//            .addConverterFactory(GsonConverterFactory.create(get()))
//            .build()
//    }
//
//    single<BookService> {
//        get<Retrofit>(named(BOOK_RETROFIT)).create(BookService::class.java)
//    }
//
//    //endregion
//
//    //region Repository
//
//    single { BookRepository(get(), get(), get(), get(), get(), get(), get()) }
//
//    single { ChapterRepository(get(), get(), get(), get()) }
//
//    single { PageRepository(get(), get(), get()) }
//
//    single { AnchorRepository(get(), get()) }
//
//    single { ContentRepository(get(), get()) }
//
//    single { DownloadRepository(get()) }
//
//    //endregion
//
//    //region Mock
//
//    //endregion
//
//    single<AuthTokenProvider> { AuthTokenProviderStub() }
}
package io.snabble.pay.app.domain.home.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.database.AccountsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProductionModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AccountsDatabase {
        return Room
            .databaseBuilder(appContext, AccountsDatabase::class.java, AccountsDatabase.DB_NAME)
            .build()
    }
}

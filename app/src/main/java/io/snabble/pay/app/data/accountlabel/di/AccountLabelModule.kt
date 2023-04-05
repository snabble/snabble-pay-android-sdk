package io.snabble.pay.app.data.accountlabel.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.snabble.pay.app.data.accountlabel.AccountLabelDao
import io.snabble.pay.app.data.accountlabel.AccountLabelDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AccountLabelModule {

    @Singleton
    @Provides
    fun provideAccountLabelDao(database: AccountLabelDatabase): AccountLabelDao =
        database.accountLabelDao()

    @Singleton
    @Provides
    fun provideAccountLabelDatabase(@ApplicationContext appContext: Context): AccountLabelDatabase =
        Room
            .databaseBuilder(
                context = appContext,
                klass = AccountLabelDatabase::class.java,
                name = AccountLabelDatabase.DB_NAME
            )
            .build()
}

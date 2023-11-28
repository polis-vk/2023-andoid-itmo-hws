package ru.ok.itmo.tuttut

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.ok.itmo.tuttut.messenger.domain.ChatsDAO

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideDataStore(app: Application): DataStore<Preferences> = app.dataStore

    @Provides
    fun provideChatsDAO(database: AppDatabase): ChatsDAO = database.chatsDAO()

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "passports").build()
}
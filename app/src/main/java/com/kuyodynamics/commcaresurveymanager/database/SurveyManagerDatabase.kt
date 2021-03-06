package com.kuyodynamics.commcaresurveymanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kuyodynamics.commcaresurveymanager.database.dao.*
import com.kuyodynamics.commcaresurveymanager.database.entities.*

@Database(
    entities = [
        CommCareUser::class,
        CommCareProject::class,
        CommCareApp::class,
        CommCareModule::class,
        CommCareForm::class,
        CommCareQuestion::class,
        SurveyManagerTable::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SurveyManagerDatabase : RoomDatabase() {

    // refs to DAOs
    abstract val commCareAppDAO: CommCareAppDAO
    abstract val commCareModuleDAO: CommCareModuleDAO
    abstract val commCareFormDAO: CommCareFormDAO
    abstract val commCareQuestionDAO: CommCareQuestionDAO
    abstract val commCareUserDAO: CommCareUserDAO
    abstract val surveyManagerTableDAO: SurveyManagerTableDAO
    abstract val commCareProjectDAO: CommCareProjectDAO

    // The companion object allows clients to access the methods for creating or getting the database without instantiating the class.
    companion object {

        @Volatile
        private var INSTANCE: SurveyManagerDatabase? = null

        fun getInstance(context: Context): SurveyManagerDatabase {
            synchronized(this) {
                // Kotlin smart cast feature
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SurveyManagerDatabase::class.java,
                        "module_manager_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
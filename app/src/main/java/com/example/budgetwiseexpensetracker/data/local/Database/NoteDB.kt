package com.example.myroomdatabase.Database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//// Ensure both MovieEntity and CastEntity are properly defined
//@Database(entities = [MovieEntity::class, CastEntity::class], version = 2, exportSchema = false)
//abstract class MovieDatabase : RoomDatabase() {
//
//    // Define abstract functions to get DAOs
//    abstract fun movieDao(): MovieDao
//    abstract fun movieCastDao(): MovieCastDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MovieDatabase? = null
//
//        // Function to get the singleton instance of the database
//        fun getDatabase(context: Context): MovieDatabase {
//            return INSTANCE ?: synchronized(this) {
//                // Build the database instance
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MovieDatabase::class.java,
//                    "movie_database"
//                )
//                    .fallbackToDestructiveMigration()  // Drops the database and recreates it on version mismatch
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}

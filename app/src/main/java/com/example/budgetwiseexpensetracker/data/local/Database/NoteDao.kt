package com.example.myroomdatabase.Database
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface MovieDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(movies: List<MovieEntity>)
//
//    @Query("SELECT * FROM movies")
//    fun getAllMovies(): Flow<List<MovieEntity>>
//
//    @Query("DELETE FROM movies")
//    suspend fun clearMovies()
//}
//@Dao
//interface MovieCastDao{
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(movies: List<CastEntity>)
//
//    @Query("SELECT * FROM `cast`")
//    fun getAllMovies(): Flow<List<CastEntity>>
//
//    @Query("DELETE FROM `cast`")
//    suspend fun clearMovies()
//}
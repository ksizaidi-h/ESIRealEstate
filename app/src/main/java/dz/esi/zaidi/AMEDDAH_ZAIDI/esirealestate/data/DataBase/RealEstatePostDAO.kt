package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost

@Dao
interface RealEstatePostDAO {

    @Insert
    fun insertRealEstatePost(post : RealEstatePost)

    @Delete
    fun deleteRealEstatePost(post : RealEstatePost)

    @Query("SELECT * FROM real_estate_posts WHERE category = :category")
    fun getPostsByCategory(category : String) : LiveData<List<RealEstatePost>>

    @Query("SELECT * FROM real_estate_posts WHERE link = :link")
    fun getPostByLink(link : String) : RealEstatePost?

}
package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import androidx.room.*
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.DataBase.Converters
import java.net.URL
@Entity(tableName = "real_estate_posts")
@TypeConverters(Converters::class)
data class RealEstatePost(

    var description : String?,
    @Embedded
    var localisation: Localisation,
    @Embedded
    val poster : RealEstatePoster,
    @TypeConverters(Converters::class)
        val pictures : List<String>,
    val link : String?
){
    @Ignore
    var wilaya = localisation.wilaya
        private set
    @Ignore
    var commune = localisation.commune
        private set
    @Ignore
    var address = localisation.adresse
        private set

    @Ignore
    var phone = poster.phoneNumber
        private set

    @PrimaryKey(autoGenerate = true)
    var postId : Int = 0

    var category : String? = null
    var type : String? = null
    var surface : String? = null
    var price : String? = null

    override fun toString(): String {
        return "$postId : $localisation"
    }
}
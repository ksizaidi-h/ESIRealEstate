package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

import java.net.URL

data class RealEstatePost(

    var description : String?,
    var localisation: Localisation,
    val poster : RealEstatePoster,
    val pictures : List<String>,
    val link : String?
){
    var postId : Int = 0
    var category : String? = null
    var type : String? = null
    var surface : String? = null
    var price : String? = null

}
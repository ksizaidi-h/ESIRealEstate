package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data

data class Localisation(
    var wilaya : String,
    var commune : String?
){
    var localisationId : Int = 0
    var adresse : String? = null
}
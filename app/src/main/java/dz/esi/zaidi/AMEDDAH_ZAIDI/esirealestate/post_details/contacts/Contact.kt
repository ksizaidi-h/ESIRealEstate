package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

data class Contact(val id : Int, val phone : String, val displayName : String, val email : String?) {

    override fun equals(other: Any?): Boolean {
        return id == (other as Contact).id
    }

    override fun hashCode(): Int {
        return id
    }

    override fun toString(): String {
        return displayName
    }
}
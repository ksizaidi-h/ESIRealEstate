package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources

import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost

interface RealEstatePostsConsumer {

    fun addPost(newPost: RealEstatePost)

}
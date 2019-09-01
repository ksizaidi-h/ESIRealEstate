package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources

import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data.RealEstatePost

interface RealEstatePostsConsumer {

    fun makeNotifications(newPosts : List<RealEstatePost>)
}
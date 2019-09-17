package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources

interface BookmarkedRealEstatePostsProvider {

    fun getBookmarkedPosts(consumer: RealEstatePostsConsumer)
}
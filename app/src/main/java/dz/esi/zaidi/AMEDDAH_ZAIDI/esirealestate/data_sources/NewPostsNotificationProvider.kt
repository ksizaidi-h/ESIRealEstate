package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.data_sources

import android.content.Context

interface NewPostsNotificationProvider {
    fun getNewPosts(consumer: NotificationConsumer, context: Context)
}
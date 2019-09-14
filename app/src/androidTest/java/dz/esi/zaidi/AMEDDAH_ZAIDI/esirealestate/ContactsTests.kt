package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate

import android.content.Context

import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts.ContactsResolver
import org.junit.Test

import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ContactsTests {
    @get:Rule val permission = GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS)

    val context = ApplicationProvider.getApplicationContext<Context>()
    init {
        GrantPermissionRule.grant(android.Manifest.permission.READ_CONTACTS)
    }

    @Test
    fun should_display_contact_name(){

        val resolver = ContactsResolver(context)
        val contacts = resolver.getContacts()

        assert(contacts.filter { it.email != null }.size == 1)
    }
}
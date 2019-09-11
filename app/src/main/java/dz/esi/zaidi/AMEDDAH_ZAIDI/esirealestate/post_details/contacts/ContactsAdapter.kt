package dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.post_details.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dz.esi.zaidi.AMEDDAH_ZAIDI.esirealestate.R
import kotlinx.android.synthetic.main.contacts_choose_item.view.*

class ContactsAdapter : ListAdapter<Contact, ContactsAdapter.ContactsViewHolder>(DIFF_CALLBACK){

    companion object{
        private val DIFF_CALLBACK  = object : DiffUtil.ItemCallback<Contact>(){
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id  == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
       return  ContactsViewHolder(
           LayoutInflater.from(parent.context).inflate(R.layout.contacts_choose_item, parent,false)
           ,onAddButtonClickListener)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    lateinit var onAddButtonClickListener: OnAddButtonClickListener

    class ContactsViewHolder(itemView: View,val onAddButtonClickListener: OnAddButtonClickListener) : RecyclerView.ViewHolder(itemView){

        fun bind(item : Contact) = with(itemView){
            itemView.tv_contact_item.text = item.displayName
            itemView.btn_add_contact.setOnClickListener {
                if(itemView.btn_add_contact.isChecked){
                    onAddButtonClickListener.addContactToList(item)
                }else{
                    onAddButtonClickListener.removeContactFromList(item)
                }
            }
        }
    }

    interface OnAddButtonClickListener{
        fun addContactToList(contact : Contact)
        fun removeContactFromList(contact: Contact)
    }
}
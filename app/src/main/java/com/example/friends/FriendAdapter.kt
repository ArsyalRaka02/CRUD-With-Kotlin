package com.example.friends

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.content_items.*


class FriendAdapter(private val context: Context, private val items: List<Friend>) :
    RecyclerView.Adapter<FriendAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        context, LayoutInflater.from(context).inflate(
            R.layout.content_items, parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items.get(position))

    }

    override fun getItemCount(): Int = items.size
    class ViewHolder(val context: Context, override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bindItem(item: Friend) {
            tv_name.text = item.nama
            tv_email.text = item.email
            tv_telpon.text = item.telp
            Glide.with(context).load(item.image).into(tv_img_profile)
//            ll_btn.setOnLongClickListener {
//                detail(item)
//            }
            ll_btn.setOnClickListener {
                listener(item)
            }
        }

//        private fun detail(item: Friend){
//
//        }

        private fun listener(item: Friend) {
            val dialogTest = AlertDialog.Builder(context!!)
            dialogTest.setTitle("silahkan pilih")
            val dialogItems = arrayOf("edit", "delete")
            dialogTest.setItems(dialogItems) { dialog, which ->
                when (which) {
                    0 -> changeEdit(item)
                    1 -> changeDelete()
                }
            }
            dialogTest.show()
        }

//        private var listFriend: List<Friend>? = null
        private var temanDao: FriendDao? = null
        private var db: AppDatabase? = null

        private fun changeEdit(item: Friend) {
//            val dialogTitle = AlertDialog.Builder(context!!)
//            dialogTitle.setTitle("Edit Friend")
//            val dialogFrom =
//
//            tv_name.text = item.nama
//            tv_email.text = item.email
//            tv_telpon.text = item.telp
//            validasiUpdate(item)
            (context!! as MainActivity).editListFriends()

//            val mDialogView =
//                LayoutInflater.from(context!!).inflate(R.layout.from_edit_dialog, null)
//            val mBuilder = AlertDialog.Builder(context!!).setView(mDialogView).setTitle("From Edit")
//            val mAlertDialog = mBuilder.show()
//
//
//            mDialogView.btn_update.setOnClickListener {
//                mAlertDialog.dismiss()
//
//            }
        }
//        private fun validasiUpdate(friend: Friend){
////            db.temanDao?.updateTeman(item)
//
//            db = AppDatabase.getAppDataBase(context!!)
//            temanDao = db?.friendDao()
//            temanDao?.updateTeman(friend)
//        }

        private fun changeDelete() {
            val dialogShow = AlertDialog.Builder(context!!)
            dialogShow.setTitle("Apakah Anda Yakin")
            val dialogArray = arrayOf("Ya", "Tidak")
            dialogShow.setItems(dialogArray){
                dialog, which ->
                when (which) {
                    0 -> suksesDelete()
                    1 -> cancelDelete()
                }
            }
            dialogShow.show()
        }

        private fun suksesDelete(){
            (context!! as MainActivity).viewListFriends()
        }

        private fun cancelDelete(){
            (context!! as MainActivity).viewListFriends()
        }

    }
}
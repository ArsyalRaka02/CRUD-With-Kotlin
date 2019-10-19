package com.example.friends

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.content_items.*
import kotlinx.android.synthetic.main.fragment_list_friends.*
import kotlinx.android.synthetic.main.menu_dialog.view.*

class FragmentListFriends: Fragment(){

    private var listFriend: List<Friend>? = null
    private var temanDao: FriendDao? = null
    private var db: AppDatabase? = null

    companion object {
        fun newInstance(): FragmentListFriends {
            return FragmentListFriends()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLocalDB()
        initView()
//        initButton()
    }

    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        temanDao = db?.friendDao()

    }

//    private fun initButton(){
//        btnEdit.setOnClickListener {
//            (activity as MainActivity).editListFriends()
//        }
//        btnHapus.setOnClickListener {
//            dialogOnClick()
//        }
//    }

    private fun initView() {
        fab.setOnClickListener {
            (activity as MainActivity).addListFriends()
        }
        ambilDataTeman()
//        setLayoutManager()
//        btnEdit.setOnClickListener {
//            (activity as MainActivity).editListFriends()
//        }
//        btnEdit.setOnClickListener {
//            dialogOnClick()
//        }
    }

//    private fun dialogOnClick(){
//        val dialogChange = AlertDialog.Builder(activity!!)
//        dialogChange.setTitle("Monggo dipilih")
//        val dialogItems = arrayOf("edit","delete")
//        dialogChange.setItems(dialogItems){
//            dialog, which ->
//            when (which){
//                0 -> changeEdit()
//                1 -> changeDelete()
//            }
//        }
//        dialogChange.show()
//    }

//    private fun changeEdit(){
//
//    }
//
//    private fun changeDelete(){
//
//    }

    private fun ambilDataTeman() {
        listFriend = ArrayList()
        temanDao ?. ambilSemuaTeman ()?.observe(this, Observer { r ->
            listFriend = r as ArrayList<Friend>?
            when {
                listFriend?.size == 0 -> tampilToast("Belum ada data teman")
                else -> {
                    tampilTeman()
                }
            }
        })
    }

//    private fun delete(){
//
//    }


    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

    private fun tampilTeman() {
        list_friend_items.layoutManager = LinearLayoutManager(activity)
        list_friend_items.adapter = FriendAdapter(activity!!, (listFriend as ArrayList<Friend>?)!!)
//        ll_btn.setOnClickListener {
//            dialogOnClick()
//        }
//        btnEdit.setOnClickListener {
//            (activity as MainActivity).editListFriends()
//        }
    }

}
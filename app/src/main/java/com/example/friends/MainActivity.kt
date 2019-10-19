package com.example.friends

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowId
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_list_friends.*
import kotlinx.android.synthetic.main.menu_dialog.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewListFriends()
    }

    private fun changeListFriend(fragmentManager: FragmentManager, fragment: Fragment, frameId: Int) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()
    }


    fun viewListFriends(){
        changeListFriend(supportFragmentManager, FragmentListFriends.newInstance(), R.id.contentFrame)

    }
    fun addListFriends(){
        changeListFriend(supportFragmentManager, FragmentAddFriends.newInstance(), R.id.contentFrame)
    }

    fun editListFriends(){
        changeListFriend(supportFragmentManager, FragmentEditFriends.newInstance(), R.id.contentFrame)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppDatabase.destroyDatabase()
    }
}

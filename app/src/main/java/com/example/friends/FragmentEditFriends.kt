package com.example.friends

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.fondesa.kpermissions.extension.permissionsBuilder
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_friends.*
import kotlinx.android.synthetic.main.fragment_fragment_edit_friends.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.content_items.*
import java.io.ByteArrayOutputStream
import java.io.IOException

class FragmentEditFriends : Fragment() {

    private val GALLERY = 1
    private val CAMERA = 2

    private var namaInput: String = ""
    private var emailInput: String = ""
    private var telpInput: String = ""
    private var alamatInput: String = ""
    private var genderInput: String = ""
    private var imageByte: ByteArray? = null

//    private var friendDao: FriendDao? = null
//    private var db: AppDatabase? = null

    private var listFriend: List<Friend>? = null
    private var temanDao: FriendDao? = null
    private var db: AppDatabase? = null

    companion object {
        fun newInstance(): FragmentEditFriends {
            return FragmentEditFriends()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_edit_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initDB()
    }

    private fun initDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        temanDao = db?.friendDao()
    }

    private fun initView() {

        btn_update.setOnClickListener {
//            validasiUpdate()
            ambilDataEdit()
            (activity as MainActivity).viewListFriends()
        }
//        setDataSpinnerGender()
        btn_cancel.setOnClickListener {
            (activity as MainActivity).viewListFriends()
        }
    }

    private fun ambilDataEdit(){
        temanDao?.updateTeman(Friend(null, namaInput, emailInput, telpInput, alamatInput, genderInput, imageByte))
    }

//    private fun tampilTeman(friend: Friend?){
//        "temanId = ${friend?.temanId}"
//        tv_name.text = "nama = ${friend?.nama}"
//        tv_email.text = "email = ${friend?.email}"
//        tv_telpon.text = "telpon = ${friend?.telp}"
//    }


//    private fun validasiUpdate(){
//        temanDao?.updateTeman(Friend(null, namaInput, genderInput, emailInput, telpInput, alamatInput, null))
//    }

//    private fun getDataTeman(){
//        listFriend = ArrayList()
//        temanDao ?. editDataTeman (0)?.observe(this, Observer { r ->
//            listFriend = r as ArrayList<Friend>?
//        })
//    }

//    private fun setDataSpinnerGender() {
//        val adapter = ArrayAdapter.createFromResource(
//            activity!!,
//            R.array.gender_list, android.R.layout.simple_spinner_item
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        spinnerGender.adapter = adapter
//    }

//    private fun validasiUpdate(){
//        namaInput = et_Name.text.toString()
//        emailInput = et_Email.text.toString()
//        telpInput = et_Telp.text.toString()
//        alamatInput = et_Address.text.toString()
//        genderInput = spinner_Gender.selectedItem.toString()
//
//        when {
//            namaInput.isEmpty() -> et_Name.error = "Nama tidak boleh kosong"
//            genderInput.equals("pilih jenis kelamin") -> tampilToast("Kelamin harus dipilih")
//            emailInput.isEmpty() -> et_Email.error = "Email tidak boleh kosong"
//            telpInput.isEmpty() -> et_Telp.error = "Telp tidak boleh kosong"
//            alamatInput.isEmpty() -> et_Address.error = "Alamat tidak boleh kosong"
//
//            else -> {
//                val temanId = Friend(
//                    temanId = null,
//                    nama = namaInput,
//                    jenisKelamin  = genderInput,
//                    email = emailInput,
//                    telp = telpInput,
//                    alamat = alamatInput,
//                    image = imageByte)
//                updateTeman(temanId)
//            }
//        }
//
//    }

//    private fun updateTeman(teman: Friend?): Job {
//        return GlobalScope.launch {
//            temanDao?.editDataTeman(0)
//            (activity as MainActivity).viewListFriends()
//        }
//    }

//    override fun onPermissionsAccepted(permissions: Array<out String>) {
//        tampilkanDialogGambar()
//    }

//    private fun tampilkanDialogGambar() {
//        val dialogGambar = AlertDialog.Builder(activity!!)
//        dialogGambar.setTitle("Silahkan Pilih")
//        val dialogGambarItems = arrayOf("Ambil foto dari galeri", "Ambil foto dari kamera")
//        dialogGambar.setItems(dialogGambarItems){
//                dialog, which ->
//            when(which){
//                0 -> ambilGambarDariGallery()
//                1 -> ambilGambarDenganKamera()
//            }
//        }
//        dialogGambar.show()
//    }

//    private fun ambilGambarDenganKamera() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(intent, CAMERA)
//    }
//
//    private fun ambilGambarDariGallery() {
//        val galleryIntent = Intent(
//            Intent.ACTION_PICK,
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        )
//        startActivityForResult(galleryIntent, GALLERY)
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == GALLERY){
//            if (data  != null){
//                val contentURI = data.data
//                try {
//                    val bitmap = MediaStore.Images.Media.getBitmap(
//                        activity!!.contentResolver, contentURI
//                    )
//                }catch (e: IOException){
//                    tampilToast("Gagal dong. anda tidak punya gambar")
//                }
//            }
//        }
//        else if (requestCode == CAMERA){
//            val thumbnail = data!!.extras!!.get("data") as Bitmap
//            setImageProfile(thumbnail)
//
//        }
//    }

//    private fun CekVersiAndroid(){
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
//            requestPermission()
//            tampilkanDialogGambar()
//        } else {
//            tampilkanDialogGambar()
//        }
//    }

//    private fun setImageProfile(thumbnail: Bitmap) {
//        val stream = ByteArrayOutputStream()
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, stream)
//
//        imgProfile.setImageBitmap(
//            BitmapFactory.decodeByteArray(
//                stream.toByteArray(),
//                0,
//                stream.toByteArray().size
//            )
//        )
//        imageByte = stream.toByteArray()
//    }

//    override fun onPermissionsDenied(permissions: Array<out String>) {
//        requestPermission()
//    }
//
//    private fun requestPermission() {
//        val request = permissionsBuilder(
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE
//        ).build()
//
//        request.acceptedListener(this)
//        request.deniedListener(this)
//        request.send()
//    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}

package com.example.friends

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_add_friends.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException

class FragmentAddFriends : Fragment(), PermissionRequest.AcceptedListener, PermissionRequest.DeniedListener{

    private val GALLERY = 1
    private val CAMERA = 2

    private var namaInput: String = ""
    private var emailInput: String = ""
    private var telpInput: String = ""
    private var alamatInput: String = ""
    private var genderInput: String = ""
    private var imageByte: ByteArray? = null

    private var friendDao: FriendDao? = null
    private var db: AppDatabase? = null

    companion object {
        fun newInstance(): FragmentAddFriends {
            return FragmentAddFriends()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initLocalDB()
    }
    private fun initLocalDB() {
        db = AppDatabase.getAppDataBase(activity!!)
        friendDao = db?.friendDao()
    }

    private fun initView() {
        btnSave.setOnClickListener {
            //(activity as MainActivity).tampilkanListTeman()
            validasiInput()
        }
        setDataSpinnerGender()
        imgProfile.setOnClickListener{
            CekVersiAndorid()
        }
    }

    private fun setDataSpinnerGender() {
        val adapter = ArrayAdapter.createFromResource(
            activity!!,
            R.array.gender_list, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGender.adapter = adapter
    }

    private fun validasiInput() {
        namaInput = edtName.text.toString()
        emailInput = edtEmail.text.toString()
        telpInput = edtTelp.text.toString()
        alamatInput = edtAddress.text.toString()
        genderInput = spinnerGender.selectedItem.toString()

        when {
            namaInput.isEmpty() -> edtName.error = "Nama tidak boleh kosong"
            genderInput.equals("pilih jenis kelamin") -> tampilToast("Kelamin harus dipilih")
            emailInput.isEmpty() -> edtEmail.error = "Email tidak boleh kosong"
            telpInput.isEmpty() -> edtTelp.error = "Telp tidak boleh kosong"
            alamatInput.isEmpty() -> edtAddress.error = "Alamat tidak boleh kosong"

            else -> {
                val teman = Friend(
                    nama = namaInput,
                    jenisKelamin  = genderInput,
                    email = emailInput,
                    telp = telpInput,
                    alamat = alamatInput,
                    image = imageByte)
                tambahDataTeman(teman)
            }
        }
    }

    private fun tambahDataTeman(teman: Friend): Job {
        return GlobalScope.launch {
            friendDao?.tambahTeman(teman)
            (activity as MainActivity).viewListFriends()
        }
    }

    //AKSES

    override fun onPermissionsAccepted(permissions: Array<out String>) {
        tampilkanDialogGambar()
    }

    private fun tampilkanDialogGambar() {
        val dialogGambar = AlertDialog.Builder(activity!!)
        dialogGambar.setTitle("Silahkan Pilih")
        val dialogGambarItems = arrayOf("Ambil foto dari galeri", "Ambil foto dari kamera")
        dialogGambar.setItems(dialogGambarItems){
                dialog, which ->
            when(which){
                0 -> ambilGambarDariGallery()
                1 -> ambilGambarDenganKamera()
            }
        }
        dialogGambar.show()
    }

    private fun ambilGambarDenganKamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    private fun ambilGambarDariGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY){
            if (data  != null){
                val contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity!!.contentResolver, contentURI
                    )
                }catch (e: IOException){
                    tampilToast("Gagal dong. anda tidak punya gambar")
                }
            }
        }
        else if (requestCode == CAMERA){
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            setImageProfile(thumbnail)

        }
    }

    private fun CekVersiAndorid(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermission()
            tampilkanDialogGambar()
        } else {
            tampilkanDialogGambar()
        }
    }

    private fun setImageProfile(thumbnail: Bitmap) {
        val stream = ByteArrayOutputStream()
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, stream)

        imgProfile.setImageBitmap(
            BitmapFactory.decodeByteArray(
                stream.toByteArray(),
                0,
                stream.toByteArray().size
            )
        )
        imageByte = stream.toByteArray()
    }

    override fun onPermissionsDenied(permissions: Array<out String>) {
        requestPermission()
    }

    private fun requestPermission() {
        val request = permissionsBuilder(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).build()

        request.acceptedListener(this)
        request.deniedListener(this)
        request.send()
    }

    private fun tampilToast(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }

}
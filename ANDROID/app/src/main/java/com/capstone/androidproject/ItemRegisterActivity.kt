package com.capstone.androidproject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import kotlinx.android.synthetic.main.activity_item_register_photo.*
import kotlinx.android.synthetic.main.activity_item_register_photo.view.*
import kotlinx.android.synthetic.main.activity_signup.btnNext
import kotlinx.android.synthetic.main.activity_signup.textId
import kotlinx.android.synthetic.main.activity_signup.textPassword
import org.jetbrains.anko.imageBitmap

import java.lang.Exception


class ItemRegisterActivity : AppCompatActivity() {

    val OPEN_GALLERY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_register_photo)

        btnNext.setOnClickListener{

            //var photo = 이미지 보내기 변수 추가
            val nextIntent = Intent(this@ItemRegisterActivity, SignupDetailActivity::class.java)

            //다음 activity로 img전송필요

            startActivity(nextIntent)
        }


        btnSelectPhoto.setOnClickListener {
            openGallery()
        }
    }
    fun openGallery(){
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == OPEN_GALLERY) {
                var currentImageURL: Uri? = data?.data

                try{
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImageURL)
                    imgPhoto.setImageBitmap(bitmap)
                }
                catch(e:Exception){
                    e.printStackTrace()
                }
            }
        }
        //필요시 else로 로그보는 코드 추가
    }
}

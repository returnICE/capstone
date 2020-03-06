package com.capstone.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.capstone.androidproject.Response.LoginResponse
import com.capstone.androidproject.Response.SignupResponse
import com.capstone.androidproject.Response.Success
import com.capstone.androidproject.ServerConfig.ServerConnect
import com.capstone.androidproject.SharedPreferenceConfig.App
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*

import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnRegister.setOnClickListener() {
            var id = textId.text.toString()
            var pw = textPassword.text.toString()
            var name= textName.text.toString()
            var birth = textBirth.text.toString()
            var address = textAddress.text.toString()
            var phone = textPhone.text.toString()
            signup(id, pw, name, birth, address, phone)

        }

        btnCheckId.setOnClickListener(){
            var id = textId.text.toString()
            checkId(id)
        }


    }
    fun signup(id: String, pw: String, name: String, birth: String, address: String, phone: String) {
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()

        server.postSignupRequest(id, pw, name, birth, address, phone).enqueue(object:

            Callback<SignupResponse>{
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "회원가입 실패1", Toast.LENGTH_SHORT).show()
                println(t?.message.toString())
            }

            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                val success = response?.body()?.success
                val user = response?.body()?.user
                println(user?.ID)

                if (success == false) {
                    Toast.makeText(this@SignupActivity, "회원가입 실패2", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignupActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    App.prefs.name = user?.name.toString() // 로그인 성공하면 shared_Preference에 유저정보 저장
                }
            }
        })
    }

    fun checkId(id: String){
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()
        if(id == ""){
            Toast.makeText(this@SignupActivity, "id를 입력해 주세요", Toast.LENGTH_SHORT).show()
        }
        else {
            server.getCheckRequest(id).enqueue(object
                : Callback<Success> {
                override fun onFailure(call: Call<Success>, t: Throwable) {
                    Toast.makeText(this@SignupActivity, "중복체크 실패1", Toast.LENGTH_SHORT).show()
                    println(t?.message.toString())
                }


                override fun onResponse(call: Call<Success>, response: Response<Success>) {
                    val succ = response?.body()

                    if (succ?.success == false) {
                        Toast.makeText(this@SignupActivity, "중복입니다", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@SignupActivity, "사용 가능한 ID입니다", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

            })
        }
    }
}

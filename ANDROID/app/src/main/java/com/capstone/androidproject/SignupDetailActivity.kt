package com.capstone.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.androidproject.Response.SignupResponse
import com.capstone.androidproject.Response.Success
import com.capstone.androidproject.ServerConfig.ServerConnect
import com.capstone.androidproject.SharedPreferenceConfig.App
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.textId
import kotlinx.android.synthetic.main.activity_signup.textPassword
import kotlinx.android.synthetic.main.activity_signup_detail.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignupDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_detail)



        btnRegister.setOnClickListener() {

            //SignupActivity에서 id,pw 가져오기
            var id = intent.getStringExtra("id")
            var pw = intent.getStringExtra("pw")

            var name= textName.text.toString()
            var birth = textBirth.text.toString()
            var address = textAddress.text.toString()
            var phone = textPhone.text.toString()

            //Toast.makeText(this@SignupDetailActivity, id + pw + name + birth + address + phone, Toast.LENGTH_SHORT).show()
            signup(id, pw, name, birth, address, phone)
        }

    }

    fun signup(id: String, pw: String, name: String, birth: String, address: String, phone: String) {
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()

        server.postSignupRequest(id, pw, name, birth, address, phone).enqueue(object:

            Callback<SignupResponse> {
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupDetailActivity, "회원가입 실패1", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@SignupDetailActivity, "회원가입 실패2", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SignupDetailActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    App.prefs.name = user?.name.toString() // 로그인 성공하면 shared_Preference에 유저정보 저장
                    val nextIntent = Intent(this@SignupDetailActivity, LoginActivity::class.java)
                    startActivity(nextIntent)
                }
            }
        })
    }

}

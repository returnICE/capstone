package com.capstone.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.capstone.androidproject.Response.LoginResponse
import com.capstone.androidproject.ServerConfig.ServerConnect
import com.capstone.androidproject.SharedPreferenceConfig.App
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val go_intent = findViewById(R.id.btnSignup) as Button
            go_intent.setOnClickListener{
                val intent = Intent(this@MainActivity, SignupActivity::class.java)
                startActivity(intent)
            }


        if (App.prefs.ID != "") {
            login(App.prefs.ID, App.prefs.PW)
        }//로그인 돼있으면 바로 로그인
        //shared preference에 정보 저장돼있으면 로그인 돼있는걸로 판단함

        btnLogin.setOnClickListener {
            var id = textId.text.toString()
            var pw = textPassword.text.toString()
            login(id, pw)
        }

    }

    fun login(id: String, pw: String) {
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()

        server.postLoginRequest(id, pw).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "로그인 실패1", Toast.LENGTH_SHORT).show()
                println(t?.message.toString())
            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                val success = response?.body()?.success
                val user = response?.body()?.user
                println(user?.ID)

                if (success == false) {
                    Toast.makeText(this@MainActivity, "로그인 실패2", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                    App.prefs.ID = user?.ID.toString()
                    App.prefs.PW = pw
                    App.prefs.name = user?.name.toString()
                    App.prefs.birth = user?.birth.toString()
                    App.prefs.address = user?.address.toString()
                    App.prefs.phone = user?.phone.toString() // 로그인 성공하면 shared_Preference에 유저정보 저장

                    startActivity<ExampleActivity>()
                    finish()
                }
            }
        })
    }

}


// 토큰인증방식은 아직 안함. 어렵더라ㅅㅂ
// https://kimch3617.tistory.com/entry/Retrofit%EC%9D%84-%EC%9D%B4%EC%9A%A9%ED%95%9C-%ED%86%A0%ED%81%B0-%EC%9D%B8%EC%A6%9D-%EB%B0%A9%EC%8B%9D-%EA%B5%AC%ED%98%84

// 이건 쿠키/세션 유지
// https://gun0912.tistory.com/50

// 이 사이트 앞으로도 도움 좀 될듯
// http://blog.yena.io/studynote/
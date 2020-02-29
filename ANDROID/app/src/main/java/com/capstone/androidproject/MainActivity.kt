package com.capstone.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.capstone.androidproject.Response.LoginResponse
import com.capstone.androidproject.Response.Success
import com.capstone.androidproject.Response.UserData
import com.capstone.androidproject.ServerConfig.ServerConnect
import com.capstone.androidproject.SharedPreferenceConfig.App
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //회원가입 페이지 이동


        isLogin()

        imgLogin.setOnClickListener{
            startActivity<LoginActivity>()

            finishAffinity()
        }
        imgLogout.setOnClickListener{
            App.prefs.clear()
            logout()
            isLogin()
        }

        getUser.setOnClickListener {
            getUserInfo()
        }
    }
    fun logout() {
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()

        server.getLogoutRequest().enqueue(object : Callback<Success> {
            override fun onFailure(call: Call<Success>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                println(t?.message.toString())
            }

            override fun onResponse(call: Call<Success>?, response: Response<Success>?) {
                val succ = response?.body()

                if (succ?.success == false) {
                    Toast.makeText(this@MainActivity, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun isLogin(){
        if (App.prefs.token != "") {
            imgLogin.setVisibility(View.GONE)
            imgLogout.setVisibility(View.VISIBLE)
            println("로그인됨")
        }
        else{
            imgLogout.setVisibility(View.GONE)
            imgLogin.setVisibility(View.VISIBLE)
            println("로그아웃됨")
        }
    }

    fun getUserInfo(){
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()

        val token=App.prefs.token

        server.getGetUserRequest(token).enqueue(object : Callback<LoginResponse> {

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "로그인 실패2", Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val user = response?.body()?.user

                App.prefs.name = user?.name.toString()

                userInfo.setText(user?.ID.toString())
                userInfo.setText(user?.name.toString())
            }

        })
    }
}


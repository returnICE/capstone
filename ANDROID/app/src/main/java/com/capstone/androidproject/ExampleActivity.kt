package com.capstone.androidproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.androidproject.Response.Success
import com.capstone.androidproject.ServerConfig.ServerConnect
import com.capstone.androidproject.SharedPreferenceConfig.App
import kotlinx.android.synthetic.main.activity_example.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        helloText.setText(App.prefs.name + " 님이 로그인 하셧습니다.")

        logoutBtn.setOnClickListener{
            App.prefs.clear()
            logout()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    fun logout() {
        val serverConnect = ServerConnect(this)
        val server = serverConnect.conn()

        server.getLogoutRequest().enqueue(object : Callback<Success> {
            override fun onFailure(call: Call<Success>?, t: Throwable?) {
                Toast.makeText(this@ExampleActivity, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                println(t?.message.toString())
            }

            override fun onResponse(call: Call<Success>?, response: Response<Success>?) {
                val succ = response?.body()

                if (succ?.success == false) {
                    Toast.makeText(this@ExampleActivity, "로그아웃 실패", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@ExampleActivity, "로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}

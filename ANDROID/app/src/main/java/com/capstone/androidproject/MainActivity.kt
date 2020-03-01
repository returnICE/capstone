package com.capstone.androidproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.capstone.androidproject.Fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val frag1: HomeFragment =
        HomeFragment()
    private val frag2: CategoryFragment =
        CategoryFragment()
    private val frag3: SearchFragment =
        SearchFragment()
    private val frag4: AlertFragment =
        AlertFragment()
    private val frag5: MypageFragment =
        MypageFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavi.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.getItemId()) {
                    R.id.action_home -> setFrag(0)
                    R.id.action_category -> setFrag(1)
                    R.id.action_search -> setFrag(2)
                    R.id.action_alert -> setFrag(3)
                    R.id.action_mypage -> setFrag(4)
                }
                return true
            }
        })

        setFrag(0) // 첫 프래그먼트 화면 지정
    }

    private fun setFrag(n: Int) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        when (n) {
            0 -> {
                ft.replace(R.id.Main_Frame, frag1)
                ft.commit()
            }

            1 -> {
                ft.replace(R.id.Main_Frame, frag2)
                ft.commit()
            }

            2 -> {
                ft.replace(R.id.Main_Frame, frag3)
                ft.commit()
            }
            3 -> {
                ft.replace(R.id.Main_Frame, frag4)
                ft.commit()
            }
            4 -> {
                ft.replace(R.id.Main_Frame, frag5)
                ft.commit()
            }
        }
    }
}
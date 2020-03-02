package com.capstone.androidproject.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.capstone.androidproject.SharedPreferenceConfig.App
import com.google.android.material.tabs.TabLayout
import com.capstone.androidproject.HomeTab.ContentsPagerAdapter
import com.capstone.androidproject.R
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v:View = inflater.inflate(R.layout.fragment_home, container, false)

        setActionBar()
        setMyInfo(v)
        setContent(v)

        return v
    }

    fun setActionBar(){// 액션 바 설정
        activity!!.titleText.setText("주소")
        activity!!.locationIcon.visibility = View.VISIBLE
    }

    fun setMyInfo(v:View){ // 이름, 보유 포인트
        val point = 1000
        val username:TextView = v.findViewById(R.id.userName)
        val userpoint:TextView = v.findViewById(R.id.userPoint)
        username.setText(App.prefs.name)
        userpoint.setText(point.toString())
    }

    fun setContent(v:View){ // 상품 정보들
        val mTabLayout:TabLayout = v.findViewById(R.id.layout_tab)
        mTabLayout.addTab(mTabLayout.newTab().setText("인기"))
        mTabLayout.addTab(mTabLayout.newTab().setText("최신"))
        mTabLayout.addTab(mTabLayout.newTab().setText("마감 임박"))

        val mViewPager : ViewPager = v.findViewById(R.id.pager_content)
        val mContentsPagerAdapter = ContentsPagerAdapter(
            childFragmentManager!!, mTabLayout.tabCount
        )
        mViewPager.setAdapter(mContentsPagerAdapter)

        mViewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(mTabLayout)
        )

        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                mViewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        // 탭레이아웃(TabLayout), 뷰페이저(ViewPager) 사용
        // https://re-build.tistory.com/25
        // https://recipes4dev.tistory.com/147

    }

}

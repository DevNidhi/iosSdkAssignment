package com.gamechange.assignment.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.gamechange.assignment.R
import com.gamechange.assignment.common.Constants
import com.gamechange.assignment.ui.record.RecordListingFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setToolBarColor()

        supportFragmentManager.addOnBackStackChangedListener {
            setToolBarColor()
            if(supportFragmentManager.backStackEntryCount==0)
              toolbar.setTitle(Constants.TAG_ISSUES)
        }

       addFragment()

    }

    fun setToolBarColor() = toolbar.setTitleTextColor(ContextCompat.getColor(applicationContext,R.color.white))

    fun AppCompatActivity.addFragment(){
        supportFragmentManager.beginTransaction().add(
            R.id.homeFrag, RecordListingFragment()
        ).commit()
    }


}



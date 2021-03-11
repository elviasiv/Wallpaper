package com.elviva.wallpaperboi.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elviva.wallpaperboi.MainActivity
import com.elviva.wallpaperboi.R
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    protected lateinit var navigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        navigation = BottomNavigationView(this)
        navigation.findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(this)

    }

    override fun onStart() {
        super.onStart()
        updateNavigationBarState()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0,0)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigation.postDelayed({
            when(item.itemId){
                R.id.wallpapers -> startActivity(Intent(this, MainActivity::class.java))
                R.id.favorites -> startActivity(Intent(this, FavoritesActivity::class.java))
            }
        }, 300)
        return true
    }

    private fun updateNavigationBarState(){
        val actionId = getBottomNavigationMenuItemId()
        selectBottomNavigationBarItem(actionId)
    }

    fun selectBottomNavigationBarItem(itemId: Int) {
        val menu: Menu = navigation.menu
        val item: MenuItem = menu.findItem(itemId)
        item.isChecked = true
    }

    abstract fun getLayoutId() : Int
    abstract fun getBottomNavigationMenuItemId() : Int
}
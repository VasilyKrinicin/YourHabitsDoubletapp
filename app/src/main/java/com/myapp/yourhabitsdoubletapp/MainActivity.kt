package com.myapp.yourhabitsdoubletapp

import android.net.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.myapp.yourhabitsdoubletapp.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val job = SupervisorJob()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val host =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment? ?: return
        navController = host.navController
        binding.navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_item_about -> {
                    navController.navigate(R.id.aboutFragment)
                }
                R.id.menu_item_home -> {
                    navController.navigate(R.id.viewPagerFragment)
                }
            }
            true
        }
        val header: View = binding.navView.getHeaderView(0)
        val imageView = header.findViewById<ImageView>(R.id.imageView)
        Glide.with(this)
            .load("https://funart.pro/uploads/posts/2021-04/thumbs/1618132672_3-p-truslivii-zayats-zhivotnie-krasivo-foto-3.jpg")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.ic_baseline_co_present_24)
            .error(R.drawable.glide_error)
            .circleCrop()
            .into(imageView)
    }

    companion object {
        const val TAG = "NetworConection"
    }


}

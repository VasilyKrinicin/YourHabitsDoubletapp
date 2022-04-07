package com.myapp.yourhabitsdoubletapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.myapp.yourhabitsdoubletapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
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

    }
}

package dev.seabat.android.kotlinwebsocket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.seabat.android.kotlinwebsocket.R
import dev.seabat.android.kotlinwebsocket.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav = binding.bottomNav
        val fragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // NOTE: val navController = findNavController(R.id.nav_host_fragment) はダメ

        setupWithNavController(bottomNav, fragment.navController)
        // NOTE: setOnItemSelectedListener の上書きは BottomNavigationView#setupWithNavController
        //       の後に実行する。
        bottomNav.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, fragment.navController)
            true
        }

        fragment.navController.addOnDestinationChangedListener { _, _, argument ->
            argument?.getBoolean("inVisibleBottomNav").let {
                if (it == true) {
                    bottomNav.visibility = View.GONE
                } else {
                    bottomNav.visibility = View.VISIBLE
                }
            }
        }
    }
}
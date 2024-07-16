package com.example.emarket

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.emarket.databinding.ActivityMainBinding
import com.example.emarket.ui.cart.viewModel.CartVM
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //region variables
    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val cartViewModel: CartVM by viewModels()
    //endregion

    //region lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        createBottomNavigation()
    }
    //endregion

    //region init
    private fun init() {
        val badge = binding.bottomNav.getOrCreateBadge(R.id.nav_cart)
        badge.isVisible = false
    }
    //endregion

    //region tools
    fun updateCartBadge() {
        val cartCount = cartViewModel.products.value?.sumOf { it.quantity } ?: 0
        val badge = binding.bottomNav.getOrCreateBadge(R.id.nav_cart)

        badge.isVisible = cartCount > 0
        badge.number = cartCount
    }

    private fun createBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_main -> {
                    navController.navigate(R.id.nav_main)
                    true
                }

                R.id.nav_cart -> {
                    navController.navigate(R.id.nav_cart)
                    true
                }

                R.id.nav_favorite -> {
                    navController.navigate(R.id.nav_favorite)
                    true
                }

                else -> false
            }
        }
    }
    //endregion
}


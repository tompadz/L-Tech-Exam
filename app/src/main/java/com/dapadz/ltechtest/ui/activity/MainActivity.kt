package com.dapadz.ltechtest.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.dapadz.ltechtest.R
import com.dapadz.ltechtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController
    private lateinit var navGraph : NavGraph

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavContainer()
        checkAuth()
    }

    private fun checkAuth() {
        viewModel.checkAuthorization(
            isAuth = { setStartDestination(R.id.homeFragment) },
            isNotAuth = { setStartDestination(R.id.authorizationFragment) }
        )
    }

    private fun setStartDestination(id:Int) {
        navGraph.setStartDestination(id)
        navController.graph = navGraph
        binding.progressBar.isVisible = false
    }

    private fun initNavContainer() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        navGraph = navController.navInflater.inflate(R.navigation.main)
    }

    override fun onSupportNavigateUp() : Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
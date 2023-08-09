package com.example.myfirstapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.myfirstapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val scoreViewModel = ViewModelProvider(this)[ScoreViewModel::class.java]

        //load score
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = 0
        val score = sharedPref.getInt(getString(R.string.score_pref_tag), defaultValue)
        scoreViewModel.setScore(score)
        Log.w("debug", "onCreate Loaded score: $score")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()


        //save score
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        if (sharedPref != null) {
            val scoreViewModel = ViewModelProvider(this)[ScoreViewModel::class.java]
            val score = scoreViewModel.getScore().value ?: 0
            with (sharedPref.edit()) {
                putInt(getString(R.string.score_pref_tag), score)
                apply()
            }
            Log.w("debug", "onStop saved $score to sharedPref")
        }
        else {
            Log.d("debug", "onStop sharedPref is null")
        }
    }
}
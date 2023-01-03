package com.cetiti.amapdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.commonlibrary.units.AssetsFileUtils

class GDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gd)
        AssetsFileUtils.getInstance(this).copyAssetsToSD("tiles_xy", "tiles_xy")

    }
}
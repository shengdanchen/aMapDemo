package com.cetiti.amapdemo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.mapbox.geojson.Point
import com.mapbox.maps.*
import com.mapbox.maps.plugin.MapProjection
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager


class MainActivity2 : AppCompatActivity() {
    var mMapView: MapView? = null
    var mMapboxMap: MapboxMap? = null
    private val DEFAULT_LOCATION_LATITUDE = 30.772389001386095 // 初始地图显示坐标（深圳）
    private val DEFAULT_LOCATION_LONGITUDE = 120.68156035497184
//    private val DEFAULT_LOCATION_LATITUDE = 30.77347790731735 // 初始地图显示坐标（深圳）
//    private val DEFAULT_LOCATION_LONGITUDE = 120.68347644410971
    private val DEFAULT_ZOOM_VALUE = 14.0 // 初始地图缩放大小
    val TAG:String = "dddddd"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStateBar()
        setContentView(R.layout.activity_main)
        mMapView = findViewById(R.id.mapview)
        Log.d(TAG, "onCreate: "+resources.displayMetrics.density)

        mMapboxMap = mMapView?.getMapboxMap()
        mMapboxMap!!.setCamera(
            CameraOptions.Builder()
                .center(
                    Point.fromLngLat(
                        DEFAULT_LOCATION_LONGITUDE,
                        DEFAULT_LOCATION_LATITUDE
                    )
                )
                .bearing(45.0)
                .zoom(DEFAULT_ZOOM_VALUE)
                .build()
        )
        mMapView?.getMapboxMap()?.loadStyleUri(
            Style.MAPBOX_STREETS,
            object : Style.OnStyleLoaded {
                override fun onStyleLoaded(style: Style) {
                    addAnnotationToMap(120.68156035497184, 30.772389001386095)
                    addAnnotationToMap(120.68347644410971, 30.77347790731735)
                }
            }
        )


    }

    /**
     * 沉浸式状态栏
     */
    private fun initStateBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int) =
        convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId))

    private fun addAnnotationToMap(lng:Double,lat:Double) {
// Create an instance of the Annotation API and get the PointAnnotationManager.
        bitmapFromDrawableRes(
            this,
            R.drawable.red_marker
        )?.let {
            Log.d("asdasd", "addAnnotationToMap: "+it.width)
            val annotationApi = mMapView?.annotations
            val pointAnnotationManager = annotationApi?.createPointAnnotationManager(mMapView!!)
// Set options for the resulting symbol layer.
            val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
// Define a geographic coordinate.
                .withPoint(Point.fromLngLat(lng, lat))
// Specify the bitmap you assigned to the point annotation
// The bitmap will be added to map style automatically.
                .withIconImage(it)
// Add the resulting pointAnnotation to the map.
            pointAnnotationManager?.create(pointAnnotationOptions)
        }
    }

    private fun convertDrawableToBitmap(sourceDrawable: Drawable?): Bitmap? {
        if (sourceDrawable == null) {
            return null
        }
        return if (sourceDrawable is BitmapDrawable) {
            sourceDrawable.bitmap
        } else {
// copying drawable object to not manipulate on the same reference
            val constantState = sourceDrawable.constantState ?: return null
            val drawable = constantState.newDrawable().mutate()
            val bitmap: Bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            bitmap
        }
    }
}
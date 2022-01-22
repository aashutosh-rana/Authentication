package com.bcebhagalpur.CheAshu.activity

import android.app.Application
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.bcebhagalpur.CheAshu.R
import com.bcebhagalpur.CheAshu.activity.VideoActivity.MyApp.Companion.simpleCache
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.Extractor
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelection
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.upstream.cache.*
import com.google.android.exoplayer2.util.Util
import com.google.android.gms.common.internal.Constants
import java.io.File


class VideoActivity : AppCompatActivity(),Player.EventListener {

    private lateinit var exoplayerView: com.google.android.exoplayer2.ui.PlayerView
    private lateinit var progressBar: ProgressBar

    private lateinit var simpleExoplayer: SimpleExoPlayer
    private var playbackPosition: Long = 0
//    private val mp4Url = "https://html5demos.com/assets/dizzy.mp4"
//    private val dashUrl = "https://storage.googleapis.com/wvmedia/clear/vp9/tears/tears_uhd.mpd"
//    private val urlList = listOf(mp4Url to "default", dashUrl to "dash")

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(this, "exoplayer-sample")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_video)

        exoplayerView = findViewById(R.id.videoView)
        progressBar = findViewById(R.id.progressBar)

    }

    class VideoPreLoadingService :
        IntentService(VideoPreLoadingService::class.java.simpleName) {
        private lateinit var mContext: Context
        private var simpleCache: SimpleCache? = null
        private var cachingJob: Job? = null
        private var videosList: ArrayList<String>? = null

        override fun onHandleIntent(intent: Intent?) {
            mContext = applicationContext
            simpleCache = MyApp.simpleCache

            if (intent != null) {
                val extras = intent.extras
                videosList = extras?.getStringArrayList(Constants.VIDEO_LIST)

                if (!videosList.isNullOrEmpty()) {
                    preCacheVideo(videosList)
                }
            }
        }

        private fun preCacheVideo(videosList: ArrayList<String>?) {
            var videoUrl: String? = null
            if (!videosList.isNullOrEmpty()) {
                videoUrl = videosList[0]
                videosList.removeAt(0)
            } else {
                stopSelf()
            }
            if (!videoUrl.isNullOrBlank()) {
                val videoUri = Uri.parse(videoUrl)
                val dataSpec = DataSpec(videoUri)
                val defaultCacheKeyFactory = CacheUtil.DEFAULT_CACHE_KEY_FACTORY
                val progressListener =
                    CacheUtil.ProgressListener { requestLength, bytesCached, newBytesCached ->
                        val downloadPercentage: Double = (bytesCached * 100.0
                                / requestLength)
                    }
                val dataSource: DataSource =
                    DefaultDataSourceFactory(
                        mContext,
                        Util.getUserAgent(this, getString(R.string.app_name))).createDataSource()

                cachingJob = GlobalScope.launch(Dispatchers.IO) {
                    cacheVideo(dataSpec, defaultCacheKeyFactory, dataSource, progressListener)
                    preCacheVideo(videosList)
                }
            }
        }

        private fun cacheVideo(
            dataSpec: DataSpec,
            defaultCacheKeyFactory: CacheKeyFactory?,
            dataSource: DataSource,
            progressListener: CacheUtil.ProgressListener
        ) {
            CacheUtil.cache(
                dataSpec,
                simpleCache,
                defaultCacheKeyFactory,
                dataSource,
                progressListener,
                null
            )
        }

        override fun onDestroy() {
            super.onDestroy()
            cachingJob?.cancel()
        }
    }

    class MyApp : Application() {

        companion object {
            var simpleCache: SimpleCache? = null
            var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor? = null
            var exoDatabaseProvider: ExoDatabaseProvider? = null
            var exoPlayerCacheSize: Long = 90 * 1024 * 1024
        }

        override fun onCreate() {
            super.onCreate()
            if (leastRecentlyUsedCacheEvictor == null) {
                leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
            }

            if (exoDatabaseProvider != null) {
                exoDatabaseProvider = ExoDatabaseProvider(this)
            }

            if (simpleCache == null) {
                simpleCache = SimpleCache(
                    cacheDir,
                    leastRecentlyUsedCacheEvictor!!, exoDatabaseProvider!!
                )
            }
        }
    }

}


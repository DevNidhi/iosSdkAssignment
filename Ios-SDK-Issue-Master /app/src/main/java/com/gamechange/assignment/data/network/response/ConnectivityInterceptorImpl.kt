package learnkotlin.com.mvvmweatherforecast.data.network.response

import android.content.Context
import android.net.ConnectivityManager
import com.gamechange.assignment.data.network.internal.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptorImpl(mContext: Context) : ConnectivityInterceptor {
        private val appContext = mContext.applicationContext
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!isOnline())
            throw NetworkException()
        return  chain.proceed(chain.request())

    }

    private fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
        as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
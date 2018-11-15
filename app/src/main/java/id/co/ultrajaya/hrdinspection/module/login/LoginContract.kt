package id.co.ultrajaya.hrdinspection.module.login

import android.content.Context

interface LoginContract {
    interface LoginView {
        fun showProgress()
        fun hideProgress()
        fun setUsername()
        fun setPassword()
        fun setVersi()
        fun getContext() : Context
        fun getVibrate()
        fun alertDialog(imsg : String, itipe : Int)
        fun getToast(imsg : String)
    }

    interface Presenter {
        fun onLoginClick(iUsername : String, iPassword : String)
        fun onDestroy()
    }

    interface GetDataLogin {
        interface OnFinishedListener {
            fun onFinished()
        }

        fun getLogin(onFinishedListener: OnFinishedListener)
    }
}

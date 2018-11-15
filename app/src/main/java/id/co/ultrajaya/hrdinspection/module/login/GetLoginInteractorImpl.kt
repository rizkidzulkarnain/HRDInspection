package id.co.ultrajaya.hrdinspection.module.login

import android.os.Handler

class GetLoginInteractorImpl : LoginContract.GetDataLogin {
    override fun getLogin(listener: LoginContract.GetDataLogin.OnFinishedListener) {
        Handler().postDelayed({
            listener.onFinished()
        }, 1200)
    }
}
package com.example.tvapp.Repository

enum class Status{
    Running,
    Succeed,
    Failed
}

class NetworkState(val status:Status,val msg:String) {
    companion object{
        val Loaded:NetworkState
        val Loading: NetworkState
        val Error:NetworkState
        val ENDOFLIST:NetworkState

        init {
            Loaded= NetworkState(status = Status.Succeed,"Success")
            Loading= NetworkState(status = Status.Running,"Running")
            Error= NetworkState(status = Status.Failed,"Something went wrong!")
            ENDOFLIST=NetworkState(Status.Failed,"Reaching TO  The End !")
        }
    }

}
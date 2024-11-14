package com.example.budgetwiseexpensetracker.base
open class ViewState<T>(val data:T?=null) {
    class Loaded<T>(data: T) : ViewState<T>(data)
    class Error<T>() :ViewState<T>()
    class Empty<T>:ViewState<T>()
}
//val error:BaseException?=null
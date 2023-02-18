package com.dapadz.ltechtest.utils

class HttpException(var code : Int, message : String) : Exception() {
    override var message : String? = message
}
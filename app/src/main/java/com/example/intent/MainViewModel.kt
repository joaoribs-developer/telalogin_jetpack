package com.example.intent

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private var _text1: String by mutableStateOf("")
    val text1: String get() = _text1

    private var _text2: String by mutableStateOf("")
    val text2:String get() = _text2

    fun changeText1(newValue: String){
        _text1 = newValue
    }
    fun changeText2(newValue: String){
        _text2 = newValue
    }
    fun validateLogin(): Boolean{
        return _text1.isNotEmpty() && _text2.isNotEmpty()
    }

    private var _expand: Boolean by mutableStateOf(false)
    val expand: Boolean get() = _expand
    fun focusFalse(){
        _expand = false
    }
    fun focusTrue(){
        _expand = true
    }
    private var _focus: Boolean by mutableStateOf(false)
    val focus: Boolean get() = _focus
    fun focusChange(value: Boolean){
        _focus = value
    }
    private var _focus1: Boolean by mutableStateOf(false)
    val focus1: Boolean get() = _focus1
    fun focusChange1(value: Boolean){
        _focus1 = value
    }





}
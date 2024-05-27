package com.example.telinhas.app

import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Binding(val bindType:KClass<out ViewBinding>)
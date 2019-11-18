package com.cexmobility.corekotlin.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class BaseViewModelFactory @Inject
constructor(private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        when (creator) {
            null -> loop@ for ((key, value) in creators) {
                when {
                    modelClass.isAssignableFrom(key) -> {
                        creator = value
                        break@loop
                    }
                }
            }
        }
        when (creator) {
            null -> throw IllegalArgumentException("unknown viewmodel class $modelClass")
            else -> return try {
                creator.get() as T
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    }

}

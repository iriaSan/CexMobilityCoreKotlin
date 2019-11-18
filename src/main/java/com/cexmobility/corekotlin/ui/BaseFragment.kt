package com.cexmobility.corekotlin.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : Fragment() {

    protected abstract val layoutResource: Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(layoutResource, container, false)
    }

    override fun onAttach(context: Context) {
        if (hasInjection())
            AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeView()
    }

    protected abstract fun initializeView()

    protected abstract fun hasInjection(): Boolean

    fun showSnackBar(message: String): Snackbar {
        return (activity as BaseActivity).showSnackBar(message)
    }


}
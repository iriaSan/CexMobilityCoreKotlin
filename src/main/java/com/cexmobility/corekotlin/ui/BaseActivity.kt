package com.cexmobility.corekotlin.ui

import android.content.DialogInterface
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cexmobility.corekotlin.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    protected abstract val layoutResource: Int


    override fun onCreate(savedInstanceState: Bundle?) {

        if (hasInjection())
            AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(layoutResource)

        initializeView()
    }

    protected abstract fun initializeView()

    protected abstract fun hasInjection(): Boolean

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? = dispatchingAndroidInjector

    protected fun showMessage(
        @StringRes title: Int, @StringRes message: Int,
        @StringRes positiveButton: Int, @StringRes negativeButton: Int,
        onClickPositive: DialogInterface.OnClickListener,
        onClickNegative: DialogInterface.OnClickListener
    ) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButton, onClickPositive)
        if (negativeButton != 0)
            builder.setNegativeButton(negativeButton, onClickNegative)
        builder.show()
    }


    fun showSnackBar(message: String): Snackbar {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(ContextCompat.getColor(this, R.color.black))
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_dark_more))
        return snackbar
    }


}

package me.androidbox.customedittext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import arrow.core.Either
import io.reactivex.rxjava3.disposables.CompositeDisposable
import me.androidbox.customedittext.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customPostcode.initializeValidation { postcode ->
            when(postcode) {
                is Either.Right -> {
                    println("postcode is ${postcode.b}")
                }
                is Either.Left -> {
                    println("error is ${postcode.a}")
                }
            }
        }

        binding.customPostcode.setImageListener {
            println("Fetch delivery details from location")
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}
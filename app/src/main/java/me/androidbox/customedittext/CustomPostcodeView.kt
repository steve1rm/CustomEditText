package me.androidbox.customedittext

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import arrow.core.Either
import arrow.core.EitherOf
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import ru.whalemare.rxvalidator.RxCombineValidator
import ru.whalemare.rxvalidator.RxValidator

class CustomPostcodeView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0)
    : TextInputLayout(context, attributeSet, defStyleAttr) {

    private lateinit var editTextPostcode: TextInputEditText
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var imageViewArrow: ImageView

    init {
        if(!isInEditMode) {
            val view = View.inflate(context, R.layout.custom_postcode, this)

            editTextPostcode = view.findViewById(R.id.editTextPostcode)
            textInputLayout = view.findViewById(R.id.textInputLayoutPostcode)
            imageViewArrow = view.findViewById(R.id.imageViewArrow)
            imageViewArrow.visibility = View.VISIBLE

            editTextPostcode.setText(context.getString(R.string.empty))
            textInputLayout.error = null

            imageViewArrow.setOnClickListener {
                println("Image has been pressed")
            }
        }
    }

    fun initializeValidation(@NonNull compositeDisposable: CompositeDisposable, result: (Either<String, String>) -> Unit) {
        initValidation()
            .subscribeBy {
                if(it) {
                    println("The validation worked")
                    result(Either.right(editTextPostcode.text.toString()))
                    imageViewArrow.visibility = View.VISIBLE
                }
                else {
                    result(Either.left("Failed to get postcode"))
                    imageViewArrow.visibility = View.GONE

                }
            }.addTo(compositeDisposable)
    }

    private fun initValidation(): Observable<Boolean> {
        val postcodeValidator = RxValidator(textInputLayout).apply {
            add(NotEmptyRule())
            add(ExactLengthRule())
        }.asObservable().startWith(false)

        return RxCombineValidator(postcodeValidator)
            .asObservable().`as`(RxJavaBridge.toV3Observable())
    }

}

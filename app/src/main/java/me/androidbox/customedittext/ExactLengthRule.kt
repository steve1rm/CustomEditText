package me.androidbox.customedittext

import ru.whalemare.rxvalidator.ValidateRule

class ExactLengthRule : ValidateRule {
    override fun errorMessage(): String {
        return "Should be 5 characters"
    }

    override fun validate(data: String?): Boolean {
        return data?.count() == 5
    }
}

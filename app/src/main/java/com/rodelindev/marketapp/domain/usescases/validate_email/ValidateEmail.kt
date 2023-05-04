package com.rodelindev.marketapp.domain.usescases.validate_email

import android.util.Patterns
import com.rodelindev.marketapp.domain.usescases.ValidateResult

class ValidateEmail {

    operator fun invoke(email: String): ValidateResult {
        if (email.isEmpty()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Correo no valido"
            )
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidateResult(
                successful = false,
                errorMessage = "Correo no valido"
            )
        }

        return ValidateResult(
            successful = true
        )
    }
}
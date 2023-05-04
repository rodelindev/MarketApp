package com.rodelindev.marketapp.domain.usescases.validate_password

import com.rodelindev.marketapp.domain.usescases.ValidateResult

class ValidatePassword {

    operator fun invoke(password: String): ValidateResult {

        if (password.isBlank()) {
            return ValidateResult(
                successful = false,
                errorMessage = "El password no debe estar vacio"
            )
        }

        return ValidateResult(
            successful = true
        )

    }

}

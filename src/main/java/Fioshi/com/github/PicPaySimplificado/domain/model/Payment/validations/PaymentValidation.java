package Fioshi.com.github.PicPaySimplificado.domain.model.Payment.validations;

import Fioshi.com.github.PicPaySimplificado.domain.model.Account.Account;


public interface PaymentValidation {

    void validation(Double value, Account payee, Account payer);

}

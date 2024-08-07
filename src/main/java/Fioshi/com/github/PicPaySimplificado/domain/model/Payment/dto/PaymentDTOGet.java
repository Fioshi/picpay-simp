package Fioshi.com.github.PicPaySimplificado.domain.model.Payment.dto;

import Fioshi.com.github.PicPaySimplificado.domain.model.Payment.Payment;
import Fioshi.com.github.PicPaySimplificado.domain.model.User.dto.UserDtoGet;

public record PaymentDTOGet(

        Long id,

        Double value,

        UserDtoGet payer,

        UserDtoGet payee
) {
    public PaymentDTOGet(Payment payment){
        this(payment.getId(), payment.getValue(), new UserDtoGet(payment.getPayer().getUser()), new UserDtoGet(payment.getPayee().getUser()));
    }
}

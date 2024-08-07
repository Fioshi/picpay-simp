package Fioshi.com.github.PicPaySimplificado.domain.service;

import Fioshi.com.github.PicPaySimplificado.domain.model.Payment.Payment;
import Fioshi.com.github.PicPaySimplificado.domain.model.Payment.dto.PaymentDTO;
import Fioshi.com.github.PicPaySimplificado.domain.model.Payment.dto.PaymentDTOGet;
import Fioshi.com.github.PicPaySimplificado.domain.model.validations.payment.PaymentValidation;
import Fioshi.com.github.PicPaySimplificado.domain.repository.AccountRepository;
import Fioshi.com.github.PicPaySimplificado.domain.repository.PaymentRepository;
import Fioshi.com.github.PicPaySimplificado.domain.repository.UserRepository;
import Fioshi.com.github.PicPaySimplificado.infra.Security.AuthenticateFacade;
import Fioshi.com.github.PicPaySimplificado.infra.exception.AuthorizationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private AuthenticateFacade facade;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private List<PaymentValidation> validations;

    @Async("asyncTransaction")
    @Transactional(rollbackOn = AuthorizationException.class)
    public CompletableFuture<String> payment(PaymentDTO dto) {

        var payee = accountRepository.getReferenceById(dto.payee());
        var payer = accountRepository.getReferenceById(dto.payer());
        var payment = new Payment();

        payment.dtoToEntity(dto.value(), payee, payer);

        validations.forEach(v -> v.validation(payment.getValue(), payee, payer));

        paymentRepository.save(payment);

        accountService.updateValues(payment.getValue(), payee, payer);

        authorizationService.authorization();

        return CompletableFuture.completedFuture("Pagamento realizado");
    }

    public List<PaymentDTOGet> getPayments() {
        var user = userRepository.findByEmail(facade.getCurrentUser());
        return paymentRepository.findAllByPayerId(user.getAccount().getId()).stream().map(PaymentDTOGet::new).toList();
    }
}

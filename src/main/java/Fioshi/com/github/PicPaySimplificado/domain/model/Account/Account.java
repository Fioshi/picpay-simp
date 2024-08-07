package Fioshi.com.github.PicPaySimplificado.domain.model.Account;

import Fioshi.com.github.PicPaySimplificado.domain.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_account")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Version
    private Integer version;

    public Account(User user) {
        this.user = user;
        this.balance = new BigDecimal("100");
    }

    public void transfer(BigDecimal value){
         this.balance = this.balance.subtract(value);
    }

    public void receive(BigDecimal value){
        this.balance = this.balance.add(value);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public Integer getVersion() {
        return version;
    }
}

package kr.co._29cm.homework.common;

import java.text.DecimalFormat;

public class Money {

    private final static DecimalFormat decimalFormat = new DecimalFormat("###,###원");
    private final int amount;

    public Money(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException();
        }
        this.amount = amount;
    }

    public Money add(Money money) {
        return new Money(amount + money.amount);
    }

    public Money subtract(Money money) {
        return new Money(amount - money.amount);
    }

    public String toFormat() {
        return decimalFormat.format(this.amount);
    }

}

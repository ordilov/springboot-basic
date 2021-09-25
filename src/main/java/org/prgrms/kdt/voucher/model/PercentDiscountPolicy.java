package org.prgrms.kdt.voucher.model;

import java.text.MessageFormat;
import org.prgrms.kdt.exception.InvalidDataException;

public class PercentDiscountPolicy implements DiscountPolicy {

    @Override
    public long discount(long beforeDiscount, long discount) {
        validateBeforeDiscountAmount(beforeDiscount);
        validateDiscountAmount(discount);
        return (beforeDiscount * (100 - discount)) / 100;
    }

    @Override
    public void validateBeforeDiscountAmount(long beforeDiscount) {
        if (beforeDiscount < 0) {
            throw new InvalidDataException(
                MessageFormat
                    .format("beforeDiscount amount should be positive: {0}", beforeDiscount));
        }
    }

    @Override
    public void validateDiscountAmount(long discount) {
        if (discount < 0 || discount > 100) {
            throw new InvalidDataException(
                MessageFormat.format("percentage discount amount should be 0~100: {0}", discount));
        }
    }
}
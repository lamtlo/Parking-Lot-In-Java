package com.lamtlo.parking;

public class PaymentServiceImpl implements PaymentService {
    private final int fixedPrice;

    public PaymentServiceImpl(int fixedPrice) {
        this.fixedPrice = fixedPrice;
    }

    @Override
    public int computePayment() {
        return fixedPrice;
    }
}

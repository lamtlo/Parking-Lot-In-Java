package com.lamtlo.parking;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceImplTest {
    @Test
    public void testComputePayment() {
        PaymentService paymentService = new PaymentServiceImpl(5);
        assertEquals(5, paymentService.computePayment());
    }
}
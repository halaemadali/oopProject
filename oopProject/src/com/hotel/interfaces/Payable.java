package com.hotel.interfaces;

import com.hotel.enums.PaymentMethod;

public interface Payable {
    public void pay(PaymentMethod method);
}

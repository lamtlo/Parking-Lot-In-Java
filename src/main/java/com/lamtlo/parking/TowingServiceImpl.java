package com.lamtlo.parking;

public class TowingServiceImpl implements TowingService {

    @Override
    public void tow(Car car) {
        System.out.println(String.format("Towing car, id = %d", car.getId()));
    }
}

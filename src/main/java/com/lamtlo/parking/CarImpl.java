package com.lamtlo.parking;

public class CarImpl implements Car {
    private int id;
    private String carType;

    public CarImpl(int carId) {
        id = carId;
        carType = "Normal car";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int assignParkingSpace(int parkingSpaceId) {
        return 0;
    }
}

package com.lamtlo.parking;

public interface ParkingSpot {
    boolean isOccupied();
    int getId();
    void addCar(Car car);
    void removeCar(Car car);
}

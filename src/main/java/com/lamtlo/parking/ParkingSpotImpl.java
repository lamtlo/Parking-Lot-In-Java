package com.lamtlo.parking;

public class ParkingSpotImpl implements ParkingSpot {
    private boolean occupiedStatus = false;
    private int parkingSpotId;
    private static int existingSpotNumber = 0;
    private Car occupyingCar;

    public ParkingSpotImpl() {
        this.parkingSpotId = existingSpotNumber++;
    }



    @Override
    public boolean isOccupied() {
        return this.occupiedStatus;
    }

    @Override
    public int getId() {
        return this.parkingSpotId;
    }

    @Override
    public void addCar(Car car) {
        this.occupyingCar = car;
        this.occupiedStatus = true;
    }

    @Override
    public void removeCar(Car car) {
        this.occupyingCar = null;
        this.occupiedStatus = false;
    }
}

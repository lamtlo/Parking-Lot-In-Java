package com.lamtlo.parking;

import java.util.HashMap;

/**
 * Keep track of capacity
 * Keep track where each car is
 * Keep track of parking space
 */
public class ParkingLot {
   private String parkingLotName;
   private String parkingLotAddress;
   private int maxCapacity;
   private HashMap<Car, ParkingSpot> carLocation;
   private int remainingSpace;
   public ParkingLot(int max_capacity, String name, String address) {
      this.parkingLotName = name;
      this.parkingLotAddress = address;
      this.maxCapacity = max_capacity;
      this.carLocation = new HashMap<>(maxCapacity);
      this.remainingSpace = max_capacity;
   }

   public int getRemainingCapacity() {
      return this.remainingSpace;
   }

   public int getParkingSpotId(Car car) {
      if (!this.carLocation.containsKey(car)) {
         return -1;
      }
      else {
         return this.carLocation.get(car).getId();
      }
   }

   public int assignParkingSpaceNumber(Car car, ParkingSpot spot) {
      if (spot.isOccupied()) {
         return -1;
      }
      else {
         this.carLocation.put(car, spot);
         this.remainingSpace--;
         return spot.getId();
      }
   }
   public void unassignParkingSpace(Car car) {
      if (this.carLocation.containsKey(car)) {
         this.carLocation.remove(car);
         this.remainingSpace++;
      }
   }

   public static void main(String[] args) {
      System.out.println("Some thing");
   }
}


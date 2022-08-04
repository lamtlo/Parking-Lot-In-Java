package com.lamtlo.parking;

import java.util.ArrayList;
import java.util.List;

/**
 * Keep track of capacity
 * Keep track where each car is
 * Keep track of parking space
 */
public class ParkingLot {
   private final int maxCapacity;
   private final List<Car> parking;
   private int remainingSpace;
   private final PaymentService paymentService;
   private final TowingService towingService;

   public static class Builder {
      private final int maxCapacity;
      private final int remainingSpace;
      private PaymentService paymentService;
      private TowingService towingService;

      public Builder(int maxCapacity) {
         this.maxCapacity = maxCapacity;
         this.remainingSpace = maxCapacity;
      }

      public Builder paymentService(PaymentService service) {
         paymentService = service;
         return this;
      }

      public Builder towingService(TowingService service) {
         towingService = service;
         return this;
      }

      public ParkingLot build() {
         return new ParkingLot(this);
      }
   }

   public ParkingLot(Builder builder) {
      maxCapacity = builder.maxCapacity;
      remainingSpace = builder.remainingSpace;
      parking = new ArrayList<>(maxCapacity);
      for (int i = 0; i < maxCapacity; i++) {
         parking.add(null);
      }
      paymentService = builder.paymentService;
      towingService = builder.towingService;
   }

   // Old constructor, bad
   private ParkingLot(int maxCapacity, PaymentService paymentService, TowingService towingService) {
      this.maxCapacity = maxCapacity;
      remainingSpace = maxCapacity;
      parking = new ArrayList<>(maxCapacity);
      for (int i = 0; i < maxCapacity; i++) {
         parking.add(null);
      }
      this.paymentService = paymentService;
      this.towingService = towingService;
   }

   public int getRemainingCapacity() {
      return remainingSpace;
   }

   public int getParkingSpotId(Car car) {
      if (!parking.contains(car)) {
         throw new RuntimeException(String.format("Car not found, id = %d", car.getId()));
      }
      else {
         return parking.indexOf(car);
      }
   }

   public int assignParkingSpaceNumber(Car car) {
      if (parking.contains(car)) {
         throw new RuntimeException(String.format("Car already parked, id = %d", car.getId()));
      }
      for (int i = 0; i < maxCapacity; i++) {
         if (parking.get(i) == null) {
            parking.set(i, car);
            remainingSpace--;
            return i;
         }
      }
      throw new RuntimeException("No parking available");
   }

   public void removeCarFromParkingSpace(Car car) {
      int i = parking.indexOf(car); // indexOf() returns -1 if car not found in parking
      if (i == -1) {
         throw new RuntimeException(String.format("Car not found, id = %d", car.getId()));
      }
      else {
         parking.set(i, null);
         remainingSpace++;
      }
   }

   public int payForParking(Car car) {
      getParkingSpotId(car);
      return paymentService.computePayment();
   }

   public void towCar(Car car) {
      getParkingSpotId(car);
      removeCarFromParkingSpace(car);
      towingService.tow(car);
   }
}


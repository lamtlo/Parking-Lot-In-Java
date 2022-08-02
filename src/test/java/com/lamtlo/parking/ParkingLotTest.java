package com.lamtlo.parking;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {
    @Test
    public void testGetParkingSpotIdSuccessful() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();
        for (int i = 0; i < 2; i ++) {
            parkingLot.assignParkingSpaceNumber(new CarImpl());
        }
        parkingLot.assignParkingSpaceNumber(car);

        // execute
        int parkingSpotId = parkingLot.getParkingSpotId(car);

        // verify
        assertEquals(2, parkingSpotId);
    }

    @Test
    public void testGetParkingSpotIdNonExistingCar() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();

        //execute
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLot.getParkingSpotId(car);
        });
        String expectedMessage = "Car not found, id = ";
        String actualMessage = exception.getMessage();

        // verify
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAssignParkingSpaceToParkedCar() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();
        parkingLot.assignParkingSpaceNumber(car);

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLot.assignParkingSpaceNumber(car);
        });
        String expectedMessage = "Car already parked, id = ";
        String actualMessage = exception.getMessage();
        int remainingSpaceAfter = parkingLot.getRemainingCapacity();

        // verify
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(0, remainingSpaceAfter - remainingSpaceBefore);
    }

    @Test
    public void  testAssignParkingSpaceSuccessful() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();
        for (int i = 0; i < 3; i++) {
            parkingLot.assignParkingSpaceNumber(new CarImpl());
        }

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        int parkingSpot = parkingLot.assignParkingSpaceNumber(car);
        int remainingSpaceAfter = parkingLot.getRemainingCapacity();

        //verify
        assertEquals(3, parkingSpot);
        assertEquals(-1, remainingSpaceAfter - remainingSpaceBefore);
    }

    @Test
    public void testAssignParkingSpotFulled() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();
        for (int i = 0; i < 10; i++) {
            parkingLot.assignParkingSpaceNumber(new CarImpl());
        }

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLot.assignParkingSpaceNumber(car);
        });
        String expectedMessage = "No parking available";
        String actualMessage = exception.getMessage();
        int remainingSpaceAfter = parkingLot.getRemainingCapacity();

        // verify
        assertTrue(actualMessage.equals(expectedMessage));
        assertEquals(0, remainingSpaceAfter - remainingSpaceBefore);
    }

    @Test
    public void testRemoveCarFromParkingSpaceNonExistingCar() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLot.removeCarFromParkingSpace(car);
        });
        String expectedMessage = "Car not found, id = ";
        String actualMessage = exception.getMessage();
        int remainingSpaceAfter = parkingLot.getRemainingCapacity();

        // verify
        assertTrue(actualMessage.contains(expectedMessage));
        assertEquals(0, remainingSpaceAfter - remainingSpaceBefore);
    }

    @Test
    public void testRemoveCarFromParkingSpaceSuccessful() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = new CarImpl();
        parkingLot.assignParkingSpaceNumber(car);

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        parkingLot.removeCarFromParkingSpace(car);
        int remainingSpaceAfter = parkingLot.getRemainingCapacity();

        // verify
        assertEquals(1, remainingSpaceAfter - remainingSpaceBefore);
    }

    @Test
    public void testAssignParkingSpaceUseFirstAvailableSpace() {
        // setup
        ParkingLot parkingLot = new ParkingLot(10);
        Car car = null;
        for (int i = 0; i < 5; i++) {
            Car tmpCar = new CarImpl();
            parkingLot.assignParkingSpaceNumber(tmpCar);
            if (tmpCar.getId() == 2) {
                car = tmpCar;
            }
        }
        parkingLot.removeCarFromParkingSpace(car);

        // execute
        int assignParkingSpaceNumber = parkingLot.assignParkingSpaceNumber(new CarImpl());

        // verify
        assertEquals(1, assignParkingSpaceNumber);
    }
}

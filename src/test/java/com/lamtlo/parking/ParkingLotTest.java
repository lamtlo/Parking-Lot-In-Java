package com.lamtlo.parking;

import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {
    @Test
    public void testGetParkingSpotIdSuccessful() {
        // setup
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
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
        ParkingLot parkingLot = new ParkingLot.Builder(10).build();
        Car car = null;
        for (int i = 0; i < 5; i++) {
            Car tmpCar = new CarImpl();
            parkingLot.assignParkingSpaceNumber(tmpCar);
            if (i == 1) {
                car = tmpCar;
            }
        }
        parkingLot.removeCarFromParkingSpace(car);

        // execute
        int assignParkingSpaceNumber = parkingLot.assignParkingSpaceNumber(new CarImpl());

        // verify
        assertEquals(1, assignParkingSpaceNumber);
    }

    @Test
    public void testPayForParkingSuccessful() {
        // setup
        PaymentService mockPaymentService = Mockito.mock(PaymentService.class);
        Mockito.when(mockPaymentService.computePayment()).thenReturn(5);
        ParkingLot parkingLot = new ParkingLot.Builder(10).paymentService(mockPaymentService).build();
        Car car = new CarImpl();

        // execute
        parkingLot.assignParkingSpaceNumber(car);
        int i = parkingLot.payForParking(car);

        // verify
        assertEquals(5, i);
    }

    @Test
    public void testPayForParkingNonExistingCar() {
        // setup
        PaymentService paymentService = new PaymentServiceImpl(5);
        PaymentService spyPaymentService = Mockito.spy(paymentService);
        ParkingLot parkingLot = new ParkingLot.Builder(10).paymentService(spyPaymentService).build();
        Car car = new CarImpl();

        // execute
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLot.payForParking(car);
        });
        String expectedMessage = "Car not found, id = ";
        String actualMessage = exception.getMessage();

        // verify
        Mockito.verifyNoInteractions(spyPaymentService);
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testTowCarSuccessful() {
        // setup
        TowingService towingService = new TowingServiceImpl();
        TowingService spyTowingService = Mockito.spy(towingService);
        ParkingLot parkingLot = new ParkingLot.Builder(10).towingService(spyTowingService).build();
        Car car = new CarImpl();
        ArgumentCaptor<Car> carArgumentCaptor = ArgumentCaptor.forClass(Car.class);
        parkingLot.assignParkingSpaceNumber(car);

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        parkingLot.towCar(car);
        int remainingSpaceAfter = parkingLot.getRemainingCapacity();
        Mockito.verify(spyTowingService, Mockito.times(1)).tow(carArgumentCaptor.capture());
        Car capturedCarArgument = carArgumentCaptor.getValue();
        int carId = car.getId();

        // verify
        assertEquals(1, remainingSpaceAfter - remainingSpaceBefore);
        assertEquals(carId, capturedCarArgument.getId());
    }

    @Test
    public void testTowCarNonExistingCar() {
        // setup
        TowingService towingService = new TowingServiceImpl();
        TowingService spyTowingService = Mockito.spy(towingService);
        ParkingLot parkingLot = new ParkingLot.Builder(10).towingService(spyTowingService).build();
        Car car = new CarImpl();

        // execute
        int remainingSpaceBefore = parkingLot.getRemainingCapacity();
        Exception exception = assertThrows(RuntimeException.class, () -> {
            parkingLot.towCar(car);
        });
        String expectedMessage = "Car not found, id = ";
        String actualMessage = exception.getMessage();

        int remainingSpaceAfter = parkingLot.getRemainingCapacity();

        // verify
        Mockito.verifyNoInteractions(spyTowingService);
        assertEquals(0, remainingSpaceAfter - remainingSpaceBefore);
        assertTrue(actualMessage.contains(expectedMessage));
    }
}

package com.lamtlo.parking;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class CarImpl implements Car {
    private int id;
    private static int numberOfExistingCar = 0;

    public CarImpl() {
        id = ++numberOfExistingCar;
    }

    @Override
    public int getId() {
        return id;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if(this == obj)
//            return true;
//
//        if(obj == null || obj.getClass()!= this.getClass())
//            return false;
//
//        // type casting of the argument.
//        CarImpl carImpl = (CarImpl) obj;
//
//        // comparing the state of argument with
//        // the state of 'this' Object.
//        return carImpl.id == this.id;
//    }
}

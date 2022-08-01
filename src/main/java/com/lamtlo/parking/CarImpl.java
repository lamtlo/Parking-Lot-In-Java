package com.lamtlo.parking;

import java.util.HashSet;
import java.util.Set;

public class CarImpl implements Car {
    private Set<Integer> set = new HashSet<>();

    @Override
    public int getId() {
        return 0;
    }
}

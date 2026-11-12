// 代码2-9  汽车开动方法的三个版本——Wheel（正文未给出源码，配套代码补充最小实现）
package com.example.pos.coupling.car;

public class Wheel {
    private final int speed;
    private int rotations;

    public Wheel(int speed) {
        this.speed = speed;
    }

    public void rotate() {
        rotations++;
    }

    public int getSpeed() {
        return speed;
    }

    public int getRotations() {
        return rotations;
    }
}

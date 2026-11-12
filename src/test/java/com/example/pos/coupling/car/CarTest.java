// 代码2-9  汽车开动方法的三个版本
package com.example.pos.coupling.car;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarTest {
    @Test
    void threeVersions() {
        Engine k8 = new Engine();
        List<Wheel> wheels = List.of(new Wheel(10), new Wheel(12));
        Car car = new Car(k8, wheels);
        car.move1();
        assertTrue(k8.isStarted());
        assertEquals(0, k8.getFeedbackCount());   // 间接耦合：发动机不接触车轮
        car.move2();
        assertEquals(2, k8.getFeedbackCount());
        assertEquals(12, k8.getLastSpeed());
        car.move3();
        assertEquals(4, k8.getFeedbackCount());
        assertEquals(3, wheels.get(0).getRotations());
    }
}

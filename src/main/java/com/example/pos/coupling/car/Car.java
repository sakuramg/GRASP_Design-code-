// 代码2-9  汽车开动方法的三个版本
package com.example.pos.coupling.car;

import java.util.List;

/**
 * move1：发动机与车轮间接耦合；move2：只传速度值，数据耦合；move3：传整个车轮对象，对象耦合。
 * 字段名 K8 沿用正文（表示K8型发动机）；构造方法为配套代码补充，用于注入发动机与车轮。
 */
public class Car {
    private Engine K8;                         // 发动机是K8型
    private List<Wheel> wheels;                // 车轮是一组

    public Car(Engine engine, List<Wheel> wheels) {
        this.K8 = engine;
        this.wheels = wheels;
    }

    public void move1() {
        K8.start();
        for (Wheel wheel : wheels) {
            wheel.rotate();                    // 发动机与车轮间接耦合
        }
    }

    public void move2() {
        K8.start();
        for (Wheel wheel : wheels) {
            wheel.rotate();
            K8.DM(wheel.getSpeed());           // 差速反馈只传速度值
        }
    }

    public void move3() {
        K8.start();
        for (Wheel wheel : wheels) {
            wheel.rotate();
            K8.DM(wheel);                      // 差速反馈传整个车轮对象
        }
    }
}

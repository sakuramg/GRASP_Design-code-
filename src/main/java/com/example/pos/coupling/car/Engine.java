// 代码2-9  汽车开动方法的三个版本——Engine（正文未给出源码，配套代码补充最小实现）
package com.example.pos.coupling.car;

/** 发动机：DM 为差速调整（Differential Modulation），两个重载分别对应数据耦合与对象耦合。 */
public class Engine {
    private boolean started;
    private int lastSpeed = -1;
    private int feedbackCount;

    public void start() {
        started = true;
    }

    /** move2：只接收一个速度值（数据耦合）。 */
    public void DM(int speed) {
        lastSpeed = speed;
        feedbackCount++;
    }

    /** move3：接收整个车轮对象（对象耦合），发动机可以访问车轮的任何公开成员。 */
    public void DM(Wheel wheel) {
        DM(wheel.getSpeed());
    }

    public boolean isStarted() {
        return started;
    }

    public int getLastSpeed() {
        return lastSpeed;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }
}

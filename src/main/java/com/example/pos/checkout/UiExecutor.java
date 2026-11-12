// 代码2-29 / 代码2-28 所依赖的支撑类型（正文未单列清单）
package com.example.pos.checkout;

/** 界面线程调度入口的抽象，便于测试替身。 */
public interface UiExecutor {

    void execute(Runnable task);
}

// 代码2-10  向打印机传递数据值——打印机（正文未给出源码，配套代码补充）
package com.example.pos.coupling.data;

/** 只接收字符串值的打印机：接收方拿到的是不可变的值，改不了发送方的数据。 */
public interface Printer {
    void print(String text);
}

package com.example.pos.member;

/**
 * 代码2-14：运行期授权，按调用方、会员范围和用途判断。
 * 正文中为包级私有接口；此处声明为 public，以便包外装配代码与测试传入策略实现。
 */
public interface AccessPolicy {

    void check(String caller, String memberId, String purpose);
}

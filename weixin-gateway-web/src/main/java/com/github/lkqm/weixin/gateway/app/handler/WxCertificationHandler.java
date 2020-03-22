package com.github.lkqm.weixin.gateway.app.handler;


import com.github.lkqm.weixin.gateway.annotation.WxController;
import com.github.lkqm.weixin.gateway.annotation.WxEvent;
import com.github.lkqm.weixin.gateway.annotation.WxParam;

/**
 * 微信认证事件处理器
 */
@WxController
public class WxCertificationHandler {

    /**
     * 资质认证成功
     */
    @WxEvent("qualification_verify_success")
    public void qualificationVerifySuccess(@WxParam("ExpiredTime") Long expiredTime) {
    }

    /**
     * 资质认证失败
     */
    @WxEvent("qualification_verify_fail")
    public void qualificationVerifyFail(@WxParam("FailTime") Long failTime,
                                        @WxParam("FailReason") String failReason) {
    }

    /**
     * 名称认证成功
     */
    @WxEvent("naming_verify_success")
    public void namingVerifySuccess(@WxParam("ExpiredTime") Long expiredTime) {
    }

    /**
     * 名称认证失败
     */
    @WxEvent("naming_verify_fail")
    public void namingVerifyFail(@WxParam("FailTime") Long failTime,
                                 @WxParam("FailReason") String failReason) {
    }

    /**
     * 年审通知
     */
    @WxEvent("annual_renew")
    public void annualRenew(@WxParam("ExpiredTime") Long expiredTime) {
    }

    /**
     * 认证过期失效通知审通知
     */
    @WxEvent("verify_expired")
    public void verifyExpired(@WxParam("ExpiredTime") Long expiredTime) {
    }
}
package com.cloud.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public enum ResponseStatus {
    RESPONSE_SUCCESS(10000, "成功"),
    RESPONSE_VALIDATE_ERROR(10003, "校验失败"),
    RESPONSE_OPERATION_ERROR(10004, "操作错误"),
    RESPONSE_VERIFICATIONCODE_ERROR(10005, "验证码错误"),
    RESPONSE_MOBILE_EXIST(10006, "手机号已存在"),
    RESPONSE_ZUUL_ERROR(90000, "zuul调用微服务失败，请查看是否微服务启动"),
    RESPONSE_MICRO_SERVICE_ERROR(401, "不能直接调用微服务!!"),
    RESPONSE_PHONE_ERROR(10007, "手机格式不对"),
    RESPONSE_PHONE_PAY_ERROR(10448, "非本账户绑定号码!"),
    RESPONSE_NOREGISTER_ERROR(10008, "手机号未注册"),
    RESPONSE_PHONE_DISABLE_ERROR(10009, "手机号被禁用"),
    RESPONSE_VERIFICATION_CODE__ERROR(10012, "获取验证码失败"),
    RESPONSE_VERIFICATION_CODE_DISABLE_ERROR(10013, "验证码失效"),
    RESPONSE_BINDING_PHONE_FALL_ERROR(10014, "用户绑定手机号失败"),
    RESPONSE_RESET_PASSWORD_ERROR(10015, "重置密码失败"),
    RESPONSE_UPLOAD_HEAD_IMAGE_ERROR(10016, "上传头像失败"),
    RESPONSE_CHANGE_MOBILE_ERROR(10017, "修改手机号失败"),
    RESPONSE_QUICK_LOGIN_FALL_ERROR(10018, "第三方登陆失败"),
    RESPONSE_CHANGEINFO_FALL_ERROR(10019, "修改个人信息失败"),
    RESPONSE_BINDING_REPEAT_ERROR(10020, "已经绑定成功,请勿重复绑定手机号"),
    RESPONSE_QUERY_SCENICSPOT_FALL_ERROR(10021, "查询景区失败或者附近没有景区"),
    RESPONSE_LOGIN_IN_OTHER_ERROR(10022, "改账号已在其他地方登陆,请重新登陆"),
    RESPONSE_NOBINDING_PHONE_ERROR(10023, "该账号未绑定手机号,请绑定手机号"),
    RESPONSE_FEEDBACKFALL_ERROR(10024, "填写意见反馈失败"),
    RESPONSE_CREATE_NEW_TOKEN(10025, "token过期，生成新的token"),
    RESPONSE_VERIFY_TOKEN_FAIL(10026, "登陆过期,请重新登陆"),
    RESPONSE_VERIFY_TOKEN_SUCCESS(10027, "token校验成功"),
    RESPONSE_NOPICTURE_ERROR(10028, "评论内容或者图片不能为空"),
    RESPONSE_VCODE_EMPTY_ERROR(10031, "验证码不能为空"),
    RESPONSE_NOMORE_DATA_ERROR(30000, "没有更多数据"),
    RESPONSE_NOCOLLECT_DATA_ERROR(10033, "您没有收藏景区"),
    RESPONSE_SCENIC_INDEX_FALL(10034, "查询景区失败"),
    RESPONSE_MYCOLLECT_FALL(10035, "展示收藏失败"),
    RESPONSE_DELECT_MYCOLLECT_FALL(10036, "删除收藏失败"),
    RESPONSE_ADD_MYCOLLECT_FALL(10037, "添加收藏失败"),
    RESPONSE_SCENICE_INFO_ERROR(10038, "景区详情失败"),
    RESPONSE_GET_ADCODE_FALL(10039, "获取地区列表失败"),
    RESPONSE_NODATA_FALL(10040, "数据为空"),
    RESPONSE_EVALUATE_FALL(10041, "填写评价失败"),
    RESPONSE_IS_INSCENIC_FALL(10042, "查询是否在景区错误"),
    RESPONSE_LOGIN_ERROR(10043, "请登录后再试!"),
    RESPONSE_UNAUTHORIZED_ERROR(10044, "登陆超时,请重新登陆!"),
    RESPONSE_LOGIN_IN_ERROR(10045, "您已经在其他地方登录，请重新登录！"),
    RESPONSE_NOPASSWORD_FALL(10046, "您还未设置密码,请设置密码或选择短信登陆方式"),
    RESPONSE_PHONE_ISBLANK_ERROR(10047, "账号或者密码不能为空"),
    RESPONSE_NOT_EMPTY_ERROR(1048, "数据不能为空"),
    RESPONSE_DELETE_FALL(1049, "删除失败"),
    RESPONSE_WXPAY_FALL(1050, "生成微信预付单失败"),
    RESPONSE_WXNOTIFY_FALL(1051, "异步通知超时"),
    RESPONSE_WXSELDATA_FALL(1052, "订单查询失败"),
    RESPONSE_ALIPAY_FALL(1053, "生成支付宝预付单失败"),
    RESPONSE_NOPAY_ERROR(1054, "订单未支付"),
    RESPONSE_NO_AGENCY(10055, "不是代办"),
    RESPONSE_APPLY_AGENCY_ERROR(10056, "申请被驳回"),
    RESPONSE_APPLY_WAIT(10057, "申请待审批,请等待"),
    RESPONSE_NEED_COUNT(10058, "赶快去充钱吧"),
    RESPONSE_NO_AMOUNT(10060, "漫向券不足"),
    RESPONSE_IDCARD_ERROR(10059, "身份证格式不对"),
    RESPONSE_IDCARD_CERTIFICATION_ERROR(10060, "还未实名认证请实名认证以后在申请提现!"),
    RESPONSE_ORDER_UNFINISHED_FALL(10061, "您还有未完成支付的订单,请先支付"),
    RESPONSE_NO_TICKET(10062, "票数不足"),
    RESPONSE_Pay_EROT(10066, "支付失败"),
    RESPONSE_LINE_Pay_ERROR(11118, "超出支付时间"),
    RESPONSE_SERIALNUMBER_EROT(10067, "订单号不能为空"),
    RESPONSE_NOPAYTYPE_ERROR(10068, "请告知付款什么"),
    RESPONSE_NO_PARENTUSER(10069, "上级会员不存在"),
    RESPONSE_NO_ORDER(10070, "订单不存在"),
    RESPONSE_NO_APPLY_ERROR(10071, "您还有未处理完的申请,请尽快处理"),
    RESPONSE_NO_AUTHIDCARD_ERROR(10072, "您还有未实名认证,请先进行实名认证"),
    RESPONSE_CERTIFICATE_ERROR(10073, "生成证书失败,请联系管理员"),
    RESPONSE_AUDIT_ERROR(10074, "审核失败!"),
    RESPONSE_IS_HIGH_AGENCY_ERROR(10075, "您已经是最高级别,不能再继续申请代办等级"),
    RESPONSE_NOMALL_AGENCY_ERROR(10076, "改用户已经是最高级别"),
    RESPONSE_NO_STOCK(10077, "库存不足"),
    RESPONSE_SYTEM_ERROR(10078, "系统繁忙,请稍后再试"),
    RESPONSE_NO_PRODUCT(10079, "没有该商品"),
    RESPONSE_NO_OLDTICKE(10080, "低价票购买成功,普通票数量不足"),
    RESPONSE_DATA_NULL(10088, "没有查询到数据"),
    RESPONSE_DATA_ERROR(10089, "添加数据失败"),
    RESPONSE_ERROR(10090, "网络连接失败,请重试!"),
    RESPONSE_CREAT_SUCCESS_ERROR(10000, "创建订单成功,请及时付款,避免库存不足给您造成不便"),
    RESPONSE_PRODUCT_OVER_ERROR(10092, "改商品不存在或者已经下架"),
    RESPONSE_CREAT_ORDER_ERROR(10093, "创建订单失败"),
    RESPONSE_COUPON_RANGE_ERROR(10094, "漫向券金额超出了范围"),
    RESPONSE_NOT_ALLOW_ERROR(10095, "操作不允许"),
    RESPONSE_NOT_USERORPHONE_ERROR(10096, "姓名或者电话不能为空"),
    RESPONSE_NO_PROFIT_ERROR(10097, "您还不具备分红等级,请尽快提升会员等级呦!"),
    RESPONSE_NOT_PROFIT_ERROR(10098, "您还不具备分红条件,直属下级会员需要3个十/百/千人团队"),
    RESPONSE_NO_FUNDS_ERROR(10099, "用户账户金额不足"),
    RESPONSE_MJCOUPON_NOENOUGH_ERROR(10100, "漫向券数量不能低于1000"),
    RESPONSE_PAYMENTCODE_TIMEOUT_ERROR(10101, "付款码已经过期"),
    RESPONSE_SUPPLIER_FUNDS_NOENOUGH_ERROR(10102, "您的余额已不足"),
    RESPONSE_SUPPLIER_ERROR(10103, "您还不是供应商,请先成为供应商"),
    RESPONSE_NO_TOPRICE_ERROR(10104, "请输入金额"),
    RESPONSE_QRCODE_ERROR(10105, "未能识别二维码中的信息"),
    RESPONSE_NO_YEARMEETING_ERROR(10106, "您还没有年会券"),
    RESPONSE_MONEY_TO_HUGE_ERROR(10107, "支付金额过大,请您选择线下支付"),
    RESPONSE_DATA_GET_ERROR(10454, "数据获取超时!"),
    RESPONSE_ID_GET_NULL(10456, "ID不能为空!"),
    RESPONSE_CLIENT_ID_ERROR(10457, "clientId或是clientSecret输入有误!"),
    RESPONSE_ACCESS_DENIED_ERROR(10458, "不允许访问"),
    RESPONSE_UPDATA_DATA_ERROR(10111, "修改数据失败"),
    RESPONSE_PHP_ERROR(8899, "未知错误!"),
    RESPONSE_INTERNAL_ERROR(9999, "服务器超时"),
    //=======================================用户相关==========================================
    RESPONSE_ACCOUNT_ERROR(10001, "用户不存在"),
    RESPONSE_USERNAME_PASSWORD_ERROR(10002, "用户名/密码错误"),
    RESPONSE_AUTHOR_ERROR(10010, "用户校验失败"),
    RESPONSE_REGISTER__ERROR(10011, "用户注册失败"),
    RESPONSE_NOUSER_ERROR(10029, "没有该用户信息"),
    RESPONSE_USER_LOCKED_ERROR(10030, "用户已锁定，不能登录!"),
    RESPONSE_PASSWORD_EQULE_ERROR(10108, "新密码和旧密码不能相同"),
    //=======================================景区评论==========================================
    RESPONSE_COMMENT_ERROR(10660, "评论模块超时!"),
    RESPONSE_COMMENT_SUCCEED(10000, "评论成功!"),
    RESPONSE_COMMENT_PASS(10770, "您已经评论过不可再次评论!"),
    //=======================================景区评论查询==========================================
    RESPONSE_COMMENT_SELECT_ERROR(10661, "评论查询模块超时!"),
    RESPONSE_COMMENT_SELECT_SUCCEED(10000, "查询成功!"),
    //=======================================提现记录查询==========================================
    RESPONSE_WITHDRAW_RECORD_ERROR(10662, "提现记录查询超时!"),
    RESPONSE_WITHDRAW_RECORD_SUCCEED(10000, "提现记录查询成功!"),
    RESPONSE_WITHDRAW_RECORD_ZERO(10449, "申请提现金额不能为0元"),
    //======================================收益明细查询=============================================
    RESPONSE_RETURNS_DETAILED_ERROR(10663, "收益明细查询超时!"),
    RESPONSE_RETURNS_DETAILED_SUCCEED(10000, "收益明细查询成功!"),
    //=======================================漫向券明细查询===========================================
    RESPONSE_MJQ_DETAILED_ERROR(10664, "漫向券明细查询超时!"),
    RESPONSE_MJQ_DETAILED_SUCCEED(10000, "漫向券明细查询成功!"),
    //=======================================VR菜单查询===========================================
    RESPONSE_VR_MENU_ERROR(10665, "VR菜单查询超时!"),
    RESPONSE_VR_MENU_SUCCEED(10000, "VR菜单查询成功!"),
    //=======================================VR数据查询===========================================
    RESPONSE_VR_MENU_DATA_ERROR(10665, "VR数据查询超时!"),
    RESPONSE_VR_MENU_DATA_SUCCEED(10000, "VR数据查询成功!"),
    //=======================================实名认证==========================================
    RESPONSE_CERTIFICATION_ERROR(10666, "实名认证超时!"),
    RESPONSE_CERTIFICATION_SUCCEED(10000, "实名认证成功!"),
    RESPONSE_CERTIFICATION_PASS(10776, "您已经实名认证过无需再次实名认证!"),
    //=======================================钱包明细==========================================
    RESPONSE_WALLET_DETAIL_ERROR(10667, "钱包明细查询超时!"),
    RESPONSE_WALLET_DETAIL_SUCCEED(10000, "钱包明细查询成功!"),
    //=======================================支付宝账号绑定==========================================
    RESPONSE_ZFB_ACCOUNT_ERROR(10668, "支付宝账号绑定超时!"),
    RESPONSE_ZFB_ACCOUNT_SUCCEED(10000, "支付宝账号绑定成功!"),
    RESPONSE_ZFB_ACCOUNT_PASS(10778, "您账号已经绑定过支付宝账号无需再次绑定!"),
    //=======================================年会卷查询===============================================
    RESPONSE_NHJ_SELECT_ERROR(10067, "年会券查询超时!"),
    RESPONSE_NHJ_SELECT_SUCCEED(10067, "年会券查询成功!"),
    //=======================================单数据返回===============================================
    RESPONSE_MJQ_VERIFICATION_TIMEOUT(104044, "门票已过有效期!"),
    //=======================================通用数据返回参数==========================================
    RESPONSE_NO_MORE_DATA(30000, "没有更多数据!"),
    RESPONSE_NOT_SCENIC_STAFF(14005, "您还不是景区工作人员或您二维码不属于本景区,扫码无效"),
    RESPONSE_SHOW_DATA(33333, "直接显示data里面的数据"),
    RESPONSE_TICKET_USED(14004, "门票已被使用!"),
    RESPONSE_NOT_UNREAD_MESSAGE(10444, "暂未未读消息!"),
    RESPONSE_PAY_SECONDARY_PASSWORD(10445, "支付密码输入错误,请重新输入"),
    RESPONSE_PAY_PASSWORD_LENGTH(10446, "支付密码为6位数字!"),
    RESPONSE_PAY_PASSWORD_UPDATE(10447, "此账号未设支付密码!"),
    //    ==========================================商城通用返回=======================================
    RESPONSE_STORE_SUCCEED(10000, "成功!"),
    RESPONSE_STORE_ERROR(44444, "失败!"),
    //    RESPONSE_store_mailing_address_()
    //==================================PC=============================================
    RESPONSE_USERCODE_EXIST(20001, "usercode已存在"),
    //==================================fx=============================================
    RESPONSE_MAX_KEY_ERROR(30000, "获取最大key失败"),
    //==================================手机验证码======================================
    RESPONSE_PHONE_MESSAGE_SUCCESS(17777, "短信验证码发送成功"),
    RESPONSE_PHONE_MESSAGE_ERROR(17778, "短信验证码发送失败"),
    //==================================组织和分组相关======================================
    RESPONSE_GROUPING_HANDLE_SUCCESS(200, "操作成功"),
    RESPONSE_GROUPING_HANDLE_FAILED(500, "操作失败"),
    RESPONSE_GROUPING_HANDLE_ERROR(110, "操作出现异常!"),

    RESPONSE_GROUP_HANDLE_SUCCESS(200, "操作成功"),
    RESPONSE_GROUP_HANDLE_FAILED(500, "操作失败"),
    RESPONSE_GROUP_HANDLE_ERROR(110, "操作出现异常!");

    public final int code;
    public final String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 根据错误信息来获取code
     *
     * @param value
     * @return
     */
    public static Integer getKeyForValue(String value) {
        int code = Optional.of(
                Arrays.stream(ResponseStatus.values())
                        .filter(r -> r.message.equals(value)))
                .orElse(Stream.of(ResponseStatus.RESPONSE_INTERNAL_ERROR))
                .findFirst().get().code;
        return code;
    }
}
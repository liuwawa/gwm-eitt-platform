package com.cloud.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.sms.SmsData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by lz on 2018/7/10.
 */
@Slf4j
public class JSONHttpClient {
    private static final AtomicBoolean isSwitch = new AtomicBoolean(false);
    private static final String SEND_URL = "/json/sms/Submit";
    private static final String GET_REPORT_URL = "/json/sms/Report";
    private static final String DELIVER_URL = "/json/sms/Deliver";
    private static final String BALANCE_URL = "/json/sms/Balance";
    private static final String BATCH_SEND_URL = "/json/sms/BatchSubmit";
    private static final String DEFAULT_RESPONSE = "{\"response\":{result:\"21\",desc:\"数据包/短信内容为空\"}}";
    private int conTimeOut = 30000;
    private int readTimeOut = 30000;
    private int retryCount = 3;
    private String masterUrl = "";
    private String slaveUrl = "";
    boolean isHttps = false;

    public JSONHttpClient(String masterURL) throws URIException {
        this(masterURL, null);
    }

    public JSONHttpClient(String masterURL, String slaveURL) throws URIException {
        masterUrl = getSchemaHost(masterURL);
        if (masterUrl == null || "".equals(masterUrl.trim())) {
            throw new URIException("master URL 地址必须输入");
        }
        slaveUrl = getSchemaHost(slaveURL);
    }

    private String getSchemaHost(String host) {

        if (host != null) {
            if (host.indexOf("http://") != -1) {
                return host;
            } else if (host.indexOf("https://") != -1) {
                this.isHttps = true;
                return host;
            } else {
                return "http://" + host;
            }
        }
        return null;
    }

    /**
     * 发送短信
     *
     * @param account  帐号（必填）
     * @param password 密码 （必填）
     * @param msgid    msgId（可选,长度最长32位）
     * @param phones   手机号码（必填）
     * @param content  发送内容（长度最长1000）
     * @param sign     短信签名（必填）
     * @param subcode  子号码（必填,大汉提供子号码+（用户扩展码,可选））
     * @param sendtime 定时发送时间（可选,格式:yyyyMMddHHmmss）
     */
    public String sendSms(String account, String password, String phones, String content, String sign, String subcode, String msgid, String sendtime) {
        JSONObject param = new JSONObject();
        param.put("account", account);
        param.put("password", EncryptUtil.MD5Encode(password));
        param.put("msgid", msgid);
        param.put("phones", phones);
        param.put("content", content);
        param.put("sign", sign);
        param.put("subcode", subcode);
        param.put("sendtime", sendtime);
        String requestData = param.toString();
        String resp = doPost(SEND_URL, requestData, false);
        return resp;

    }

    public String sendBatchSms(String account, String password, List<SmsData> list) {
        String resp = DEFAULT_RESPONSE;
        if (list != null && !list.isEmpty()) {
            JSONObject param = new JSONObject();
            param.put("account", account);
            param.put("password", EncryptUtil.MD5Encode(password));
            JSONArray array = new JSONArray();
            for (SmsData data : list) {
                JSONObject paramData = new JSONObject();
                paramData.put("msgid", data.getMsgid());
                paramData.put("phones", data.getPhones());
                paramData.put("content", data.getContent());
                paramData.put("sign", data.getSign());
                paramData.put("subcode", data.getSubcode());
                paramData.put("sendtime", data.getSendtime());
                array.add(paramData);
            }
            param.put("data", array);
            String requestData = param.toString();
            resp = doPost(BATCH_SEND_URL, requestData, false);
        }

        return resp;

    }

    public String sendSms(String account, String password, String phones, String content, String sign, String subcode, String msgid) {
        return sendSms(account, password, phones, content, sign, subcode, msgid, "");
    }

    public String sendSms(String account, String password, String phones, String content, String sign, String subcode) {
        return sendSms(account, password, phones, content, sign, subcode, "", "");
    }

    /**
     * 获取状态报告
     *
     * @param account  帐号（必填）
     * @param password 密码(必填)
     * @param msgid    msgid(可选)
     * @param phone    手机号码（可选）
     */
    public String getReport(String account, String password, String msgid, String phone) {

        JSONObject param = new JSONObject();
        param.put("account", account);
        param.put("password", EncryptUtil.MD5Encode(password));
        param.put("msgid", msgid);
        param.put("phone", phone);
        String requestData = param.toString();
        String resp = doPost(GET_REPORT_URL, requestData, false);
        return resp;
    }

    public String getReport(String account, String password) {
        return getReport(account, password, "", "");
    }

    /**
     * 获取上行短信
     *
     * @param account  帐号（必填）
     * @param password 密码(必填)
     */
    public String getSms(String account, String password) {

        JSONObject param = new JSONObject();
        param.put("account", account);
        param.put("password", EncryptUtil.MD5Encode(password));
        String requestData = param.toString();
        String resp = doPost(DELIVER_URL, requestData, false);
        return resp;
    }

    /**
     * 获取余额
     *
     * @param account  帐号（必填）
     * @param password 密码(必填)
     */
    public String getBalance(String account, String password) {

        JSONObject param = new JSONObject();
        param.put("account", account);
        param.put("password", EncryptUtil.MD5Encode(password));
        String requestData = param.toString();
        String resp = doPost(BALANCE_URL, requestData, false);
        return resp;
    }

    /**
     * HTTP POST 请求
     *
     * @param url  请求地址
     * @param data 提交数据
     * @return
     */
    public String doPost(String url, String data, boolean isReconn) {
        String response = null;
        String requestURL = null;
        try {
            if (isSwitch.get()) {
                if (slaveUrl != null && !"".equals(slaveUrl.trim())) {
                    requestURL = slaveUrl + url;
                    response = request(requestURL, data);
                }
            } else {
                requestURL = masterUrl + url;
                response = request(requestURL, data);
            }
        } catch (ConnectTimeoutException e) {
            redirectURL(url, data, isReconn, requestURL, e);
        } catch (ConnectException e) {
            redirectURL(url, data, isReconn, requestURL, e);
        } catch (Exception e) {
            log.error("访问：" + requestURL + " 异常:", e);
            System.err.println(e);
        }
        return response;
    }

    private String redirectURL(String url, String data, boolean isReconn, String lastRequestURL, Exception e) {

        if (!isSwitch.get()) {
            if (!isReconn && slaveUrl != null && !"".equals(slaveUrl.trim())) {
                isSwitch.set(true);
                log.info("访问：" + lastRequestURL + " 异常,地址已经已被自动切换到从地址");
                return doPost(url, data, true);
            } else {
                log.error("访问：" + lastRequestURL + " 异常:", e);
            }

        } else {
            if (!isReconn) {
                isSwitch.set(false);
                log.info("访问：" + lastRequestURL + " 异常,地址已经已被自动切换到主地址");
                return doPost(url, data, true);
            } else {
                log.error("访问：" + lastRequestURL + " 异常:", e);
            }
        }
        return null;
    }

    private String request(String url, String data) throws HttpException, IOException {
        String response = null;
        PostMethod postMethod = null;
        log.info("访问：" + url + " 请求数据:" + data);
        try {
            if (isHttps) {
                ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();
                Protocol.registerProtocol("https", new Protocol("https", fcty, 443));
            }
            HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
            postMethod = new PostMethod(url);
            postMethod.setRequestHeader("Connection", "close");
            postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
            byte[] byteData = data.getBytes("utf-8");
            RequestEntity requestEntity = new ByteArrayRequestEntity(byteData);
            postMethod.setRequestEntity(requestEntity);
            HttpConnectionManagerParams managerParams = client.getHttpConnectionManager().getParams();
            postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retryCount, false));
            managerParams.setConnectionTimeout(conTimeOut);
            managerParams.setSoTimeout(readTimeOut);
            client.executeMethod(postMethod);
            if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
                response = postMethod.getResponseBodyAsString();
                log.info("访问：" + url + " 响应数据:" + response);
            } else {
                log.error("访问：" + url + "异常，响应状态码：" + postMethod.getStatusCode() + ",响应内容：" + postMethod.getResponseBodyAsString());
            }
        } finally {
            if (postMethod != null)
                postMethod.releaseConnection();
        }
        return response;
    }

    public int getConTimeOut() {
        return conTimeOut;
    }

    public void setConTimeOut(int conTimeOut) {
        this.conTimeOut = conTimeOut;
    }

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryCount() {
        return retryCount;
    }

}

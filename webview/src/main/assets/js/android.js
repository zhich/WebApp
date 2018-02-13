var COM_TAG = "WebApp";
var App = {
    isShowBack: function (isShow) {
        var json = {
            "isShow": isShow
        }
        execSyn("App", "isShowBack", JSON.stringify(json));
    },
    setTitle: function (title) {
        var json = {
            "title": title
        }
        execSyn("App", "setTitle", JSON.stringify(json));
    },
    finish: function () {
        execSyn("App", "finish", null);
    },
    /**
     * 跳转到原生页面
     * @param viewName 预定义的页面名称
     * @param args  bundle 参数
     */
    jumpToNativePage: function (viewName, args) {
        if (typeof args == 'undefined') {
            args = {};
        }
        var json = args;
        json["viewName"] = viewName;
        execSyn("App", "jumpToNativePage", JSON.stringify(json));
    }
}

var Preference = {
    /**
     * 存值
     * @param key 键
     * @param value 值
     * @param fileName 存到哪个文件名对应的文件里面，不填则使用移动端 Preference 类定义的文件名
     */
    set: function (key, value, fileName) {
        var args = {
            "key": key,
            "value": value
        };
        if (fileName != undefined && fileName != '') {
            args["fileName"] = fileName;
        }
        return execSyn("Preference", "set", JSON.stringify(args));
    },
    /**
    * 取值
    * @param key 键
    * @param value 值
    * @param fileName 从哪个文件名对应的文件里面取值，不填则使用移动端 Preference 类定义的文件名
    */
    get: function (key, defValue, fileName) {
        var args = {
            "key": key,
            "defValue": defValue
        };
        if (fileName != undefined && fileName != '') {
            args["fileName"] = fileName;
        }
        return execSyn("Preference", "get", JSON.stringify(args));
    }
}

var Pay = {
    /*
     * 支付宝
     * @params orderString 订单 json 字符串
     */
    alipay: function (orderString, success) {
        execAsyn("Pay", "alipay", orderString, success);
    },
    /*
     * 微信支付
     * @params orderString 订单 json 字符串
     */
    weChatPay: function (orderString, success) {
        execAsyn("Pay", "weChatPay", orderString, success);
    }
}

var execSyn = function (service, action, args) {

    var json = {
        "service": service,
        "action": action
    };

    var result_str = prompt(JSON.stringify(json), args);
    var result, status, message;
    try {
        result = JSON.parse(result_str);
    } catch (e) {
        console.error(e.message);
    }
    if (result) {
        status = result.status;
        message = result.message;
    }
    if (status == 0) {
        return message;
    } else {
        console.error("service:" + service + " action:" + action + " error:" + message);
    }
}

var execAsyn = function (service, action, args, success, fail) {
    function doSuccess(result) {
        try {
            result = JSON.parse(result); // 如果是 json 字符串的话转成 json 对象
            success(result);
        } catch (e) {
            console.error(e.message);
            success(result); // 如果不是 json 字符串的话直接处理
            return null;
        }
    }
    WebApp.callNative(service, action, args, doSuccess, fail);
}

var WebApp = {
    idCounter: 0, // 计数器
    service: {},
    action: {},
    args: {},
    callBackSuccess: {},
    callBackFail: {},

    callNative: function (service, action, args, success, fail) {
        var id = "id_" + (++this.idCounter);
        if (args == null) {
            args = "{}";
        }
        this.service = service;
        this.action = action;
        this.args = args;

        var json = {
            "id": id,
            "service": service,
            "action": action,
            "args": args
        }
        execSyn("AsynParams", "setParams", JSON.stringify(json));

        if (typeof success != 'undefined') {
            this.callBackSuccess[id] = success;
        }
        if (typeof fail != 'undefined') {
            this.callBackFail[id] = fail;
        }
        var iframe = document.createElement("iframe");
        iframe.setAttribute("src", COM_TAG + "://ready?id=" + id);
        document.documentElement.appendChild(iframe);
        iframe.parentNode.removeChild(iframe);
        iframe = null;
    },

    callBackJs: function (result) {
        console.log(result);

        result = JSON.parse(result);
        var responseBodyObj = result.responseBody;
        var id = result.id;
        
        var message = responseBodyObj.message;
        var status = responseBodyObj.status;
        if (status == 0) {
            if (typeof this.callBackSuccess[id] != 'undefined') {
                setTimeout("WebApp.callBackSuccess['" + id + "']('" + message + "')", 0);
            }
        } else {
            if (typeof this.callBackFail[id] != 'undefined') {
                setTimeout("WebApp.callBackFail['" + id + "']('" + message + "')", 0);
            }
        }
    }
}
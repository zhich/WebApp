var App = {
    isShowBack: function (isShow) {
        var json = {
            "isShow": isShow
        }
        exec_syn("App", "isShowBack", JSON.stringify(json));
    },
    setTitle: function (title) {
        var json = {
            "title": title
        }
        exec_syn("App", "setTitle", JSON.stringify(json));
    },
    finish: function () {
        exec_syn("App", "finish", null);
    },
    /**
     * 跳转到原生页面
     * @param viewName 预定义的页面名称
     * @param args  bundle参数
     */
    jumpToNativePage: function (viewName, args) {
        if (typeof args == 'undefined') {
            args = {};
        }
        var json = args;
        json["viewName"] = viewName;
        exec_syn("App", "jumpToNativePage", JSON.stringify(json));
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
        return exec_syn("Preference", "set", JSON.stringify(args));
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
        return exec_syn("Preference", "get", JSON.stringify(args));
    }
}

var exec_syn = function (service, action, args) {

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
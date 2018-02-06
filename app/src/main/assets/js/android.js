var App = {
    isShowBack: function(isShow){
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
        console.error("service:" + service + " action:" + action + " error:"+ message);
    }
}
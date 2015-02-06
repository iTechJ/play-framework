/**
 * Created by Anton.Nekrasov on 8/15/2014.
 */

define(["application/service/authService",
    "application/model/user",
    "application/util/callback"
    ], function(authService, User, Callback) {
    "use strict";

    function LoginVM() {
        var name = ko.observable(),
            password = ko.observable(),
            login = function() {
                authService.login(name,
                    password,
                    new Callback(function(params){
                        var reply = params.reply,
                            data = reply.data;
                        if(reply.status === "SUCCESS") {
                            $("body").trigger("authorized", new User(data.id, data.name, data.role));
                        }
                    }, this, {}),
                    new Callback(function(params){
                        alert(params.reply.responseJSON.data);
                    }, this, {})
                );
            },
            logout = function(root) {
                authService.logout(
                    new Callback(function(){
                        name("");
                        password("");
                        root.goTo("lgn");
                    }, this, {}),
                    new Callback(function(params) {
                        var message = params.responseText ? params.responseText : params.statusText;
                        alert(message);
                    }, this, {})
                );
            };

        return {
            name: name,
            password: password,
            login: login,
            logout: logout
        }
    }

    return new LoginVM();
});
/**
 * Created by Anton.Nekrasov on 8/19/2014.
 */
define([], function() {
    "use strict";

    function BaseService() {
        var self = this,
            params = {};

        self.send = function(url, method, data, success, error, callback) {

            jQuery.ajax({
                url: url,
                method: method,
                data: data
            }).done(function(s) {
                if(success) {
                    params = success.parameters;
                    if(s) params.reply = s;
                    success.fn.apply(success.scope, [params]);
                }
            }).fail(function(e) {
                if(error) {
                    params = error.parameters;
                    if(e) params.reply = e;
                    error.fn.apply(error.scope, [params]);
                }
            }).always(function(c) {
                if(callback) {
                    params = callback.parameters;
                    if(c) params.reply = c;
                    callback.fn.apply(callback.scope, [params]);
                }
            });
        };

        return {
            send: self.send
        }
    }

    return new BaseService();
});
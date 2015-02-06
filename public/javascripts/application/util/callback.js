/**
 * Created by Anton.Nekrasov on 8/19/2014.
 */

define([], function() {
    "use strict";
    function Callback(fn, scope, parameters){
        this.fn = fn;
        this.scope = scope;
        this.parameters = parameters;
    }

    return Callback;
});
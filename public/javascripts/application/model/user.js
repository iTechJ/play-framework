/**
 * Created by Anton.Nekrasov on 8/15/2014.
 */

define(function() {
    "use strict";
    function User (id, name, role) {
        var self = this;
        self.id = id;
        self.name = name;
        self.role = role;
    }

    return User;
});

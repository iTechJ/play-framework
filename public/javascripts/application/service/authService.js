/**
 * Created by Anton.Nekrasov on 8/19/2014.
 */
define(["application/service/baseService"], function(baseService) {
    function AuthService(){


        var login = function(user, password, success, error, done) {
            baseService.send(
                "/login",
                "POST",
                {user: user, password: password},
                success,
                error,
                done
            );
        };

        var logout = function(success, error, done){
            baseService.send(
                "/logout",
                "POST",
                {},
                success,
                error,
                done
            );
        }

        return {
            login: login,
            logout: logout
        }
    }

    return new AuthService();
});
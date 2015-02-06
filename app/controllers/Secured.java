package controllers;

import models.Role;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Anton.Nekrasov
 * 8/19/2014 13:01
 */
public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Http.Context ctx) {
        return ctx.session().get("name");
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface BmRole {
//        Role[] role() default {Role.ADMIN, Role.USER};
        Role[] role() default {Role.ADMIN};
    }
}

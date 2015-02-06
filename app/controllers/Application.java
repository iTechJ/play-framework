package controllers;

import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import views.html.main;

import java.util.Map;

public class Application extends BaseController {

    // Admin / admin
    // User / user

    public static Result index() {
        return ok(main.render());
    }

    @Transactional
    public static Result login() {
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("user")[0];
        String password = values.get("password")[0];
        User user = User.findByName(name);
        if(user == null) {
            return badRequest(Json.toJson(
                    new Reply<>(Status.ERROR, "User not found"))
            );
        }
        if(BCrypt.checkpw(password, user.password)) {
            session().clear();
            session("name", name);
            session().put("role", user.role.toString());

            return ok(Json.toJson(
                    new Reply<>(Status.SUCCESS, user))
            );
        } else {
            return badRequest(Json.toJson(
                    new Reply<>(Status.ERROR, "Wrong password"))
            );
        }
    }

    public static Result logout() {
        session().clear();
        return ok();
    }

}

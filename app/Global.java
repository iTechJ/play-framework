import controllers.Secured;
import models.Role;
import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Anton.Nekrasov
 * 8/19/2014 13:52
 */
public class Global extends GlobalSettings {
    @Override
    public void onStart(Application application) {
        super.onStart(application);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Action onRequest(final Http.Request request, final Method actionMethod) {
        Annotation[] ans = actionMethod.getDeclaringClass().getAnnotations();
        for (Annotation a : ans) {
            if (a instanceof Security.Authenticated) {
                return new Action() {
                    @Override
                    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
                        Annotation annotation = actionMethod.getAnnotation(Secured.BmRole.class);
                        if (annotation != null) {
                            Role[] roles = ((Secured.BmRole) annotation).role();
                            String role = ctx.session().get("role");
                            for(Role r : roles) {
                                if(r.toString().equals(role)) {
                                    return delegate.call(ctx);
                                }
                            }
                            return F.Promise.pure((Result) forbidden());
                        }
                        return delegate.call(ctx);
                    }
                };
            }
        }

        return super.onRequest(request, actionMethod);
    }
}

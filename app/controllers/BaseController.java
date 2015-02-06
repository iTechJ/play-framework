package controllers;

import play.mvc.Controller;

/**
 * Created by Anton.Nekrasov
 * 8/14/2014 17:14
 */
public class BaseController extends Controller {

    public static class Reply<T> {
        public Status status;
        public T data;

        public Reply() {
            this.status = Status.ERROR;
        }

        public Reply(Status status, T data) {
            this.status = status;
            this.data = data;
        }
    }

    public static enum Status{
        SUCCESS,
        ERROR
    }

}

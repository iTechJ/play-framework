/**
 * Created by Surikat on 24.08.14.
 */

define(["application/service/baseService"], function(baseService) {
    function BookService(){
        var self = this;

        self.add = function(book, success, error, done) {
            baseService.send(
                "/book",
                "POST",
                book,
                success,
                error,
                done
            );
        };

        self.list = function(page, pageSize, success, error, done) {
            baseService.send(
                "/book/list/" + page + "/" + pageSize,
                "GET",
                {},
                success,
                error,
                done
            );
        };

        self.get = function(id, success, error, done) {
            baseService.send(
                "/book/" + id,
                "GET",
                {},
                success,
                error,
                done
            );
        };

        self.update = function(book, success, error, done) {
            baseService.send(
                "/book/" + book.id,
                "PUT",
                book,
                success,
                error,
                done
            );
        };

        self.remove = function(id, success, error, done) {
            baseService.send(
                "/book/" + id,
                "DELETE",
                {},
                success,
                error,
                done
            );
        };

        return {
            add: self.add,
            list: self.list,
            get: self.get,
            update: self.update,
            remove: self.remove
        }
    }

    return new BookService();
});
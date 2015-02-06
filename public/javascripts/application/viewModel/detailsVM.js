/**
 * Created by Anton.Nekrasov on 8/15/2014.
 */

define(["application/service/bookService", "application/util/callback"], function(bookService, Callback) {
    "use strict";

    function DetailsVM() {
        var self = this,
            reply,
            book = ko.observable(),
            submit = function(root){

                var record = book(),
                    success = new Callback(function(params){
                            reply = params.reply;
                            if(reply.status === "SUCCESS") {
                                clean();
                                root.listVM.list(1, root.listVM.PAGE_SIZE);
                                root.goTo("lst");
                            }
                        }, self, {}
                    ),
                    error = new Callback(function(params){
                            reply = params.reply;
                            var message = reply.responseText ? reply.responseText : reply.statusText;
                            alert(message);
                        }, self, {}
                    );

                if(record.id) {
                    bookService.update(record, success, error);
                } else {
                    bookService.add(record, success, error);
                }

            },
            setBook = function(b){
                book(b);
            },
            clean = function(){
                book(null);
            };

        return {
            submit: submit,
            setBook: setBook,
            book: book
        }
    }

    return new DetailsVM();
});
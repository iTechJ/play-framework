/**
 * Created by Anton.Nekrasov on 8/15/2014.
 */

define(["application/service/bookService",
        "application/util/callback",
        "application/model/book"], function(bookService, Callback, Book) {
    "use strict";

    function ListVM() {
        var self = this,
            books = ko.observableArray(),
            PAGE_SIZE = 3,
            currentPage = ko.observable(1),
            totalPages = ko.observable(),
            reply,
            list = function(page, pageSize) {
                bookService.list(page, pageSize,
                    new Callback(function(params){
                            reply = params.reply;
                            if(reply.status === "SUCCESS") {
                                books([]);
                                totalPages(reply.data.totalPages);
                                for(var i = 0, lth = reply.data.list.length; i < lth; i++) {
                                    var book = reply.data.list[i];
                                    addBook(book.id, book.name, book.price);
                                }
                            }
                        }, self, {}
                    ),
                    new Callback(function(params){
                            reply = params.reply;
                            var message = reply.responseText ? reply.responseText : reply.statusText;
                            alert(message);
                        }, self, {}
                    )
                )
            },
            addRecord = function(root) {
                root.detailsVM.setBook(new Book("", "", ""));
                root.goTo("det");
            },
            next = function() {
                var pn = currentPage() < totalPages() ? currentPage() + 1: currentPage();
                currentPage(pn);
                list(currentPage(), PAGE_SIZE);
            },
            previous = function() {
                if(currentPage() <= 1) {
                    currentPage(1);
                    return;
                }
                var pn = currentPage() > 1 ? currentPage() - 1 : 1;
                currentPage(pn);
                list(currentPage(), PAGE_SIZE);
            },
            loadRecord = function(book, event, root) {
                if(event.target.className !== "bm-remove") {

                    bookService.get(book.id,
                        new Callback(function(params){
                                reply = params.reply;
                                if(reply.status === "SUCCESS") {
                                    root.detailsVM.setBook(new Book(reply.data.id, reply.data.name, reply.data.price));
                                    root.goTo("det");
                                }
                            }, self, {}
                        ),
                        new Callback(function(params){
                                reply = params.reply;
                                var message = reply.responseText ? reply.responseText : reply.statusText;
                                alert(message);
                            }, self, {}
                        )
                    )
                }
            },
            deleteBook = function(book) {
                bookService.remove(book.id,
                    new Callback(function(params){
                            reply = params.reply;
                            if(reply.status === "SUCCESS") {
                                currentPage(1);
                                list(currentPage(), PAGE_SIZE);
                            }
                        }, self, {}
                    ),
                    new Callback(function(params){
                        reply = params.reply;
                        var message = reply.responseText ? reply.responseText : reply.statusText;
                        alert(message);
                    }, self, {})
                )
            },
            addBook = function(id, name, price) {
                books.push(new Book(id, name, price));
            };

        return {
            books: books,
            list: list,
            previous: previous,
            next: next,
            addBook: addBook,
            loadRecord: loadRecord,
            addRecord: addRecord,
            deleteBook: deleteBook,
            totalPages: totalPages,
            currentPage: currentPage,
            PAGE_SIZE: PAGE_SIZE
        }
    }

    return new ListVM();

});
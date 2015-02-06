package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import dto.BookDTO;
import models.Book;
import models.Role;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Result;
import play.mvc.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by Anton.Nekrasov
 * 8/14/2014 16:44
 */

@Security.Authenticated(Secured.class)
public class BookController extends BaseController {

    @Transactional
    public static Result getBook(Long id) {
        Book book = Book.findById(id);
        if(book == null) {
                return notFound(Json.toJson(new Reply()));
        }

        Reply<Book> reply = new Reply<>(Status.SUCCESS, book);
        return ok(Json.toJson(reply));
    }

    @Transactional
    public static Result listBooks(Integer pageNumber, Integer pageSize) {
        if(pageNumber == null || pageSize == null || pageNumber <= 0 || pageNumber <= 0) {
                return badRequest(Json.toJson(new Reply()));
        }

        Long total = Book.total();
        Integer totalPages = Double.valueOf(Math.ceil((double) total / pageSize)).intValue();
        List<Book> bookList = Book.list(pageNumber, pageSize);
        List<BookDTO> dtoList = new ArrayList<>();
        for(Book b : bookList) {
            dtoList.add(BookDTO.getBook(b));
        }

        ObjectNode result = Json.newObject();
        result.put("totalPages", totalPages);
        result.put("list", Json.toJson(dtoList));

        return ok(Json.toJson(
                new Reply<>(Status.SUCCESS, result))
        );
    }

    @Transactional
    @Secured.BmRole(role = {Role.ADMIN, Role.USER})
    public static Result createBook() {
        Book book = new Book();
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];
        String price = values.get("price")[0];
        book.name = name;
        // caution: no validation!
        book.price = Integer.valueOf(price);
        Book.update(book);

        return ok(Json.toJson(
                new Reply<>(Status.SUCCESS, book)
        ));
    }

    @Transactional
    @Secured.BmRole
    public static Result updateBook(Long id) {
        Book book = Book.findById(id);
        final Map<String, String[]> values = request().body().asFormUrlEncoded();
        String name = values.get("name")[0];
        String price = values.get("price")[0];
        book.name = name;
        // caution: no validation!
        book.price = Integer.valueOf(price);
        Book.update(book);
        return ok(Json.toJson(
                new Reply<>(Status.SUCCESS, book)
        ));
    }

    @Transactional
    @Secured.BmRole
    public static Result deleteBook(Long id) {
        Book.delete(id);
        return ok(Json.toJson(
                new Reply<>(Status.SUCCESS, id)
        ));
    }
}

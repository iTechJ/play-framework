package dto;


import models.Book;

/**
 * Created by Anton.Nekrasov
 * 8/12/2014 16:05
 */

public class BookDTO {
    public Long id;
    public String name;

    public static BookDTO getBook(Book book) {
        BookDTO dto = new BookDTO();
        dto.id = book.id;
        dto.name = book.name;
        return dto;
    }
}

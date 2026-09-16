package com.library.model;

import java.util.List;

public interface Searchable {
    List<Book> searchByTitle(String keyword);
    List<Book> searchByAuthor(String keyword);
}
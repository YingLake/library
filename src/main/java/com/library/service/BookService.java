package com.library.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.library.entity.Book;

public interface BookService {

    IPage<Book> findPage(int page, int size, String keyword);

    Book borrowBook(Long bookId, Long userId);

    Book returnBook(Long bookId);

    Book findById(Long id);

}
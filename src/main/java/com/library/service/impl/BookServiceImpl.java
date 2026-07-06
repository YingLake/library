package com.library.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.library.entity.Book;
import com.library.exception.BusinessException;
import com.library.mapper.BookMapper;
import com.library.service.BookService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public IPage<Book> findPage(int page, int size, String keyword) {
        Page<Book> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
        if (keyword != null && !keyword.isEmpty()) {
            wrapper.like(Book::getName, keyword);
        }
        wrapper.orderByDesc(Book::getCreateTime);
        return bookMapper.selectPage(pageParam, wrapper);
    }

    @Override
    public Book borrowBook(Long bookId, Long userId) {
        Book book = findById(bookId);
        if (book == null) {
            throw new BusinessException(404, "图书不存在");
        }
        if (book.getStatus() != 0) {
            throw new BusinessException(400, "该图书已被借出");
        }
        book.setStatus(1);
        book.setBorrowerId(userId);
        book.setBorrowTime(LocalDateTime.now());
        bookMapper.updateById(book);
        return book;
    }

    @Override
    public Book returnBook(Long bookId) {
        Book book = findById(bookId);
        if (book == null) {
            throw new BusinessException(404, "图书不存在");
        }
        if (book.getStatus() != 1) {
            throw new BusinessException(400, "该图书未被借出");
        }
        book.setStatus(0);
        book.setBorrowerId(null);
        book.setBorrowTime(null);
        bookMapper.updateById(book);
        return book;
    }

    @Override
    public Book findById(Long id) {
        return bookMapper.selectById(id);
    }

}
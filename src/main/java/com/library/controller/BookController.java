package com.library.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.library.entity.Book;
import com.library.service.BookService;
import com.library.utils.JwtUtil;
import com.library.utils.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;
    private final JwtUtil jwtUtil;

    public BookController(BookService bookService, JwtUtil jwtUtil) {
        this.bookService = bookService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/list")
    public Result<Object> list(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", required = false) String keyword) {
        IPage<Book> bookPage = bookService.findPage(page, size, keyword);
        return Result.success(bookPage);
    }

    @PostMapping("/borrow/{bookId}")
    public Result<Object> borrow(@PathVariable(name = "bookId") Long bookId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Long userId = jwtUtil.getUserIdFromToken(token);
        Book book = bookService.borrowBook(bookId, userId);
        return Result.success("借阅成功", book);
    }

    @PostMapping("/return/{bookId}")
    public Result<Object> returnBook(@PathVariable(name = "bookId") Long bookId) {
        Book book = bookService.returnBook(bookId);
        return Result.success("归还成功", book);
    }

}
package com.library.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.library.entity.Book;
import com.library.entity.User;
import com.library.mapper.BookMapper;
import com.library.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserMapper userMapper;
    private final BookMapper bookMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public DataInitializer(UserMapper userMapper, BookMapper bookMapper) {
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        initUsers();
        initBooks();
    }

    private void initUsers() {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, "admin");
        User admin = userMapper.selectOne(wrapper);
        if (admin == null) {
            admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(1);
            userMapper.insert(admin);
        } else {
            admin.setPassword(passwordEncoder.encode("123456"));
            userMapper.updateById(admin);
        }

        wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, "user1");
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            user = new User();
            user.setUsername("user1");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setRole(0);
            userMapper.insert(user);
        } else {
            user.setPassword(passwordEncoder.encode("123456"));
            userMapper.updateById(user);
        }
    }

    private void initBooks() {
        String[][] books = {
            {"深入理解Java虚拟机", "周志明"},
            {"Spring Boot实战", "Craig Walls"},
            {"Redis深度历险", "钱文品"},
            {"MySQL必知必会", "Ben Forta"},
            {"设计模式", "GoF"}
        };

        for (String[] bookInfo : books) {
            LambdaQueryWrapper<Book> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Book::getName, bookInfo[0]);
            if (bookMapper.selectOne(wrapper) == null) {
                Book book = new Book();
                book.setName(bookInfo[0]);
                book.setAuthor(bookInfo[1]);
                book.setStatus(0);
                bookMapper.insert(book);
            }
        }
    }

}
package com.listeners;

import com.common.Book;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;

@WebListener
public class LibraryContextServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ArrayList<Book> books = new ArrayList<>();
        for(int i = 0; i< 10; i++) books.add(new Book(i));

        sce.getServletContext().setAttribute("books", books);
        ServletContextListener.super.contextInitialized(sce);
    }
}

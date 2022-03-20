package com.servlet;

import com.common.Book;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

@WebServlet(name = "adminServlet", value = "/adminDashboard")
public class AdminServlet extends HttpServlet {
    private String message = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        ArrayList<Book> books = (ArrayList<Book>) getServletContext().getAttribute("books");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println(message + "<br>");
        out.println("<h1>Books Descriptions</h1><br>");
        books.forEach(book -> out.println(book.toString() + "<br>"));
        out.println("<h1>Books Admin Duties</h1><br>");
        out.println("<form method='post' action='adminDashboard'>");
        out.println("<label for='title'>Title:</label><br>");
        out.println("<input type='text' id='title' name='title'><br>");
        out.println("<label for='author'>Author:</label><br>");
        out.println("<input type='text' id='author' name='author'><br>");
        out.println("<label for='year'>Year of publishing:</label><br>");
        out.println("<input type='number' id='year' name='year'><br>");
        out.println("<input type='submit' name='button' value='Add'>");
        out.println("<input type='submit' name='button' value='Remove'>");
        out.println("</form>");
        out.println("</body></html>");
        out.close();
        message = "";
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        message = "";
        String button = req.getParameter("button");
        if (button != null) {
            executeAdminTask(req, button);
        }
        doGet(req, resp);
    }

    private boolean isBookInList(Book newBook, ArrayList<Book> books) {
        return books.contains(newBook);
    }

    private void executeAdminTask(HttpServletRequest req, String button) {
        ArrayList<Book> books = (ArrayList<Book>) getServletContext().getAttribute("books");
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String yearString = req.getParameter("year");
        if (!title.isEmpty() && !author.isEmpty() && !yearString.isEmpty()) {
            int year = parseInt(yearString);
            Book newBook = new Book(title, author, year);

            if ("Add".equals(button)) {
                if (isBookInList(newBook, books)) {
                    message = "This book is already in the database.";
                } else {
                    books.add(newBook);
                    getServletContext().setAttribute("books", books);
                }
            }
            if ("Remove".equals(button)) {
                if (isBookInList(newBook, books)) {
                    books.remove(newBook);
                    getServletContext().setAttribute("books", books);
                } else {
                    message = "Can't remove a book not in the database.";
                }
            }
        } else {
            message = "None of the fields can be empty.";
        }
    }
}

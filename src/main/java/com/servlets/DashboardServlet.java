package com.servlets;

import com.beans.Book;
import com.beans.User;
import com.enums.Role;
import com.google.gson.Gson;
import com.requests.AddBookRequest;
import com.responses.BookResponse;
import com.responses.ExceptionResponse;
import com.responses.GetDashboardResponse;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.beans.Constants.*;

@WebServlet(name = "dashboard", value = {"/dashboard", "/dashboard/*"})
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse
            response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Gson gson = new Gson();
        try {
            List<Book> books = getBooksFromContext(request.getServletContext());
            GetDashboardResponse res = new GetDashboardResponse(books, 200);
            gson.toJson(res, response.getWriter());
        } catch (Exception ex) {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);
            gson.toJson(exResponse, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        if (isUserAdmin(request.getSession())) {
            try {
                AddBookRequest addBookRequest = gson.fromJson(request.getReader(), AddBookRequest.class);
                addBook(addBookRequest, request, response);
            } catch (Exception ex) {
                ExceptionResponse exResponse = new ExceptionResponse();
                exResponse.setMessage(ex.getLocalizedMessage());
                exResponse.setStatus(500);
                response.setStatus(500);
                gson.toJson(exResponse, response.getWriter());
            }
        } else {
            sendUserNotAdminMessage(gson, response);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        if (isUserAdmin(request.getSession())) {
            try {
                int id = Integer.parseInt(request.getPathInfo().replace("/", ""));
                deleteBook(id, request, response);
            } catch (Exception ex) {
                ExceptionResponse exResponse = new ExceptionResponse();
                exResponse.setMessage(ex.getLocalizedMessage());
                exResponse.setStatus(500);
                response.setStatus(500);
                gson.toJson(exResponse, response.getWriter());
            }
        } else {
            sendUserNotAdminMessage(gson, response);
        }
    }

    private List<Book> getBooksFromContext(ServletContext servletContext) {
        return (List<Book>) servletContext.getAttribute("books");
    }

    private boolean isUserAdmin (HttpSession session) {
        User currentUser = (User) session.getAttribute("loggedUser");
        return currentUser.getRole().equals(Role.ADMIN);
    }

    private void addBook(AddBookRequest addBookRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        String title = addBookRequest.getTitle();
        String author = addBookRequest.getAuthor();
        int year = addBookRequest.getYear();
        if (isUserAdmin(request.getSession())){
            if (!title.isEmpty() && !author.isEmpty()) {
                Book newBook = new Book(title, author, year);

                if (isBookInContext(request.getServletContext(), newBook)) {
                    ExceptionResponse exResponse = new ExceptionResponse();
                    exResponse.setMessage(ERROR_BOOK_IN_DATABASE);
                    exResponse.setStatus(400);
                    response.setStatus(400);
                    gson.toJson(exResponse, response.getWriter());
                } else {
                    addBookToContext(request.getServletContext(), newBook);
                    BookResponse bookResponse = new BookResponse(newBook, 200);
                    gson.toJson(bookResponse, response.getWriter());
                }
            } else {
                ExceptionResponse exResponse = new ExceptionResponse();
                exResponse.setMessage(ERROR_FIELD_EMPTY);
                exResponse.setStatus(400);
                response.setStatus(400);
                gson.toJson(exResponse, response.getWriter());
            }
        } else {
            sendUserNotAdminMessage(gson, response);
        }
    }

    private void deleteBook(int id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        if (isUserAdmin(request.getSession())) {
            if (!isBookWithIdInContext(request.getServletContext(), id)) {
                ExceptionResponse exResponse = new ExceptionResponse();
                exResponse.setMessage(ERROR_BOOK_NOT_IN_DATABASE);
                exResponse.setStatus(400);
                response.setStatus(400);
                gson.toJson(exResponse, response.getWriter());
            } else {
                Book newBook = getBookWithIdFromContext(getServletContext(), id);
                deleteBookFromContext(request.getServletContext(), newBook);
                BookResponse bookResponse = new BookResponse(newBook, 200);
                gson.toJson(bookResponse, response.getWriter());
            }
        } else {
            sendUserNotAdminMessage(gson, response);
        }
    }

    private boolean isBookInContext(ServletContext servletContext, Book newBook) {
        return getBooksFromContext(servletContext).contains(newBook);
    }

    private boolean isBookWithIdInContext(ServletContext servletContext, int id) {
        return getBooksFromContext(servletContext).stream().anyMatch(book -> book.getId() == id);
    }

    private Book getBookWithIdFromContext(ServletContext servletContext, int id) {
        return (Book) getBooksFromContext(servletContext).stream().filter(book -> book.getId() == id).toArray()[0];
    }

    private void addBookToContext(ServletContext servletContext, Book newBook) {
        List<Book> books = getBooksFromContext(servletContext);
        books.add(newBook);
        servletContext.setAttribute("books", books);
    }

    private void deleteBookFromContext(ServletContext servletContext, Book newBook) {
        List<Book> books = getBooksFromContext(servletContext);
        books.remove(newBook);
        servletContext.setAttribute("books", books);
    }

    private void sendUserNotAdminMessage(Gson gson, HttpServletResponse response) throws IOException {
        ExceptionResponse exResponse = new ExceptionResponse();
        exResponse.setMessage(ERROR_USER_NOT_ADMIN);
        exResponse.setStatus(400);
        response.setStatus(400);
        gson.toJson(exResponse, response.getWriter());
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.rjc.bookwebapp2.controller;

import edu.wctc.rjc.bookwebapp2.entity.Author;
import edu.wctc.rjc.bookwebapp2.entity.Book;
import edu.wctc.rjc.bookwebapp2.service.AuthorService;
import edu.wctc.rjc.bookwebapp2.service.BookService;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 *
 * @author ryancorbin
 */
public class BookController extends HttpServlet {

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listBooks.jsp";
    private static final String UPDATE_PAGE = "/updateBook.jsp";
    private static final String INSERT_PAGE = "/insertBook.jsp";
    private static final String ABOUT_PAGE = "about.jsp";
    private static final String INDEX_PAGE = "/index.html";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String UPDATE_FINAL_ACTION = "updateFinal";
    private static final String INSERT_ACTION = "insert";
    private static final String INSERT_FINAL_ACTION = "insertFinal";
    private static final String DELETE_ACTION = "delete";
    private static final String ACTION_PARAM = "action";
    private static final String CANCEL_ACTION = "cancel";
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        BookService bookService = (BookService) ctx.getBean("bookService");
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
        
        HttpSession session = request.getSession();
        
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        String user = request.getParameter("userName");
        
        if ("guest".equals(user)){
            session.setAttribute("user", "guest");
            session.setAttribute("guestUserFeatures", "hidden");
        } else if (user != null){
            session.setAttribute("user", user);
            session.setAttribute("guestUserFeatures", "");
        }
        
//        String bgColor = request.getParameter("bgColor");
//        
//        if (bgColor != null){
//        ctx.setAttribute("ctxBgColor", bgColor);
//        }
        
        Author author = null;
        Book book = null;
        
        try {
            
            if (action.equals(LIST_ACTION)) {
                    this.refreshBookList(request, bookService);
                    this.refreshAuthorList(request, authService);
                    destination = LIST_PAGE;
            }  else if (action.equals(DELETE_ACTION)) {
                    String bookId = request.getParameter("Id");
                    book = bookService.findById(bookId);
                    bookService.remove(book);
                    //Reload page without deleted author
                    this.refreshBookList(request, bookService);
                    destination = LIST_PAGE;
            } else if (action.equals(UPDATE_ACTION)) {
                    String bookId =request.getParameter("Id");
                    book = bookService.findById(bookId);
                    request.setAttribute("book", book);
                    this.refreshAuthorList(request, authService);
                    destination = UPDATE_PAGE; 
            } else if (action.equals(UPDATE_FINAL_ACTION)){
                
                String title = request.getParameter("title");
                String isbn = request.getParameter("isbn");
                String authorId = request.getParameter("authorId");
                String bookId = request.getParameter("BookId");    
            
                book = bookService.findById(bookId);
                book.setTitle(title);
                book.setIsbn(isbn);
                if(authorId != null) {
                        author = authService.findById(authorId);
                        book.setAuthorId(author);
                }
                    
                bookService.edit(book);
                this.refreshBookList(request, bookService);
                this.refreshAuthorList(request, authService);
                destination = LIST_PAGE;
            } else if (action.equals(INSERT_ACTION)) {
                    this.refreshAuthorList(request, authService);
                    destination = INSERT_PAGE; 
            } else if (action.equals(INSERT_FINAL_ACTION)){
                
                String title = request.getParameter("title");
                String isbn = request.getParameter("isbn");
                String authorId = request.getParameter("authorId");
            
                book = new Book(0);
                book.setTitle(title);
                book.setIsbn(isbn);
                author = null;
                if (authorId != null) {
                    author = authService.findById(authorId);
                    book.setAuthorId(author);
                }
                    
                bookService.edit(book);
                this.refreshBookList(request, bookService);
                this.refreshAuthorList(request, authService);
                destination = LIST_PAGE;
            
            } else {
                    // no param identified in request, must be an error
                    request.setAttribute("errMsg", NO_PARAM_ERR_MSG + " " + action);
                    destination = LIST_PAGE;
            }
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }  
    
    private void refreshBookList(HttpServletRequest request, BookService bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }
    
    private void refreshAuthorList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

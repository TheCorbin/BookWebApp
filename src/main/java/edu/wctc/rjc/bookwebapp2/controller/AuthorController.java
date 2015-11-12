package edu.wctc.rjc.bookwebapp2.controller;


import edu.wctc.rjc.bookwebapp2.entity.Author;
import edu.wctc.rjc.bookwebapp2.entity.Book;
import edu.wctc.rjc.bookwebapp2.service.AuthorService;
import edu.wctc.rjc.bookwebapp2.service.BookService;


import java.io.IOException;
import static java.lang.System.console;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * The main controller for author-related activities
 *
 */
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String UPDATE_PAGE = "/updateAuthor.jsp";
    private static final String INSERT_PAGE = "/insertAuthor.jsp";
    private static final String ABOUT_PAGE = "about.jsp";
    private static final String INDEX_PAGE = "/index.html";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String UPDATE_ACTION = "update";
    private static final String DELETE_ACTION = "delete";
    private static final String INSERT_ACTION = "insert";
    private static final String ACTION_PARAM = "action";
    private static final String UPDATE_FINAL = "updateFinal";
    private static final String INSERT_FINAL = "insertFinal";
    private static final String CANCEL_ACTION = "cancel";
    private static final String MAIN_ACTION = "main";
    
 
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
        AuthorService authService = (AuthorService) ctx.getBean("authorService");
        BookService bookService = (BookService) ctx.getBean("bookService");
        

        HttpSession session = request.getSession();
        
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);
        
        String user = request.getParameter("userName");
        
//        if ("guest".equals(user)){
//            session.setAttribute("user", "guest");
//            session.setAttribute("guestUserFeatures", "hidden");
//        } else if (user != null){
//            session.setAttribute("user", user);
//            session.setAttribute("guestUserFeatures", "");
//        }
        
//        String bgColor = request.getParameter("bgColor");
//        
//        if (bgColor != null){
//        ctx.setAttribute("ctxBgColor", bgColor);
//        }
        
        Author author = null;
        Book book = null;
        
        try {
            
            if (action.equals(LIST_ACTION)) {
                this.refreshList(request, authService);
                destination = LIST_PAGE;
                
            }  else if (action.equals(DELETE_ACTION)) {
                String authorId = request.getParameter("Id");
                author = authService.findById(authorId);
                authService.remove(author);
                //Reload page without deleted author
                this.refreshList(request, authService);
                destination = LIST_PAGE;
            } else if (action.equals(UPDATE_ACTION)) {
                String authorId = request.getParameter("Id");
                Author authorAlt = authService.findByIdAndFetchBooksEagerly(authorId);
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new java.util.Date();
                request.setAttribute("date", dateFormat.format(authorAlt.getDateAdded()));
                request.setAttribute("author", authorAlt);
                destination = UPDATE_PAGE; 
            } else if (action.equals(UPDATE_FINAL)) {
                Integer authorID = Integer.parseInt(request.getParameter("Id"));
                String authorName = request.getParameter("authorName");
                String authorAdded = request.getParameter("authorAdded");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                Date parsedDate = formatter.parse(authorAdded);
                
                author = new Author(authorID);
                author.setAuthorName(authorName);
                author.setDateAdded(parsedDate);
                
                authService.edit(author);
                this.refreshList(request, authService);
                destination = LIST_PAGE;
            } else if (action.equals(INSERT_ACTION)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new java.util.Date();
                request.setAttribute("date", dateFormat.format(date));
                this.refreshBookList(request, bookService);
                destination = INSERT_PAGE;
            } else if (action.equals(INSERT_FINAL)){
                String authorName = request.getParameter("authorName");
                String authorAdded = request.getParameter("authorAdded");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                Date parsedDate = formatter.parse(authorAdded);
                author = new Author();
                author.setAuthorName(authorName);
                author.setDateAdded(parsedDate);
                authService.edit(author);
                book = new Book();
                
                String bookTitle = request.getParameter("bookTitle");
                if (!bookTitle.equals("")){
                String bookISBN = request.getParameter("bookISBN");
                book.setAuthorId(author);
                book.setTitle(bookTitle);
                book.setIsbn(bookISBN);
                bookService.edit(book);
                }
                this.refreshList(request, authService);
                this.refreshBookList(request, bookService);
                destination = LIST_PAGE;
            } else if (action.equals(MAIN_ACTION)){
                response.sendRedirect("index.html"); 
            } else if (action.equals(CANCEL_ACTION)){
                this.refreshList(request, authService);
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

    private void refreshList(HttpServletRequest request, AuthorService authService) throws Exception {
        List<Author> authors = authService.findAll();
        request.setAttribute("authors", authors);
    }
    
    private void refreshBookList(HttpServletRequest request, BookService bookService) throws Exception {
        List<Book> books = bookService.findAll();
        request.setAttribute("books", books);
    }
    
    @Override
    public void init() throws ServletException {
        // Get init params from web.xml

        // You can't do the Java Reflection stuff here because exceptions
        // are thrown that can't be handled by this stock init() method
        // because the method signature can't be modified -- remember, you 
        // are overriding the method.
    }
    
    
    
    
    
}

package edu.wctc.rjc.bookwebapp2.controller;


import edu.wctc.rjc.bookwebapp2.model.Author;
import edu.wctc.rjc.bookwebapp2.model.AuthorDAO;
import edu.wctc.rjc.bookwebapp2.model.BookService;
import edu.wctc.rjc.bookwebapp2.model.DbStrategy;
import edu.wctc.rjc.bookwebapp2.model.InterAuthorDAO;
import edu.wctc.rjc.bookwebapp2.model.MySqlDbStrategy;
import java.io.IOException;
import static java.lang.System.console;
import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

/**
 * The main controller for author-related activities
 *
 * @author jlombardo
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

    
    //Init Parameters
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbStrategyClassName;
    private String daoClassName;
    private DbStrategy db;
    private InterAuthorDAO authorDao;
    
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

        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        
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
        
        
        
        String bgColor = request.getParameter("bgColor");
        
        if (bgColor != null){
        ctx.setAttribute("ctxBgColor", bgColor);
        }
        
        /*
         For now we are hard-coding the strategy objects into this
         controller. In the future we'll auto inject them from a config
         file. Also, the DAO opens/closes a connection on each method call,
         which is not very efficient. In the future we'll learn how to use
         a connection pool to improve this.
         */
//        DbStrategy db = new MySqlDbStrategy();
        
//        String className = getServletContext().getInitParameter("dbStrategy");
//        Class dbClass = Class.forName(className);
//        DbStrategy db = (DbStrategy)dbClass.newInstance();
        
//        try {
//            String email = this.getServletContext().getInitParameter("email");
//            Class c = Class.forName(dbClassName);
//            DbStrategy db = c.newInstance();
//        } catch (exception e{
//            
//        }
        
//        InterAuthorDAO authDao
//                = new AuthorDAO(db, "com.mysql.jdbc.Driver",
//                        "jdbc:mysql://localhost:3306/book", "root", "adminC");
        

        try {
            
            BookService authService = injectDependenciesAndGetAuthorService();
            /*
             Here's what the connection pool version looks like.
             */
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource)ctx.lookup("jdbc/book");
//            AuthorDaoStrategy authDao = new ConnPoolAuthorDao(ds, new MySqlDbStrategy());
//            AuthorService authService = new AuthorService(authDao);

            /*
             Determine what action to take based on a passed in QueryString
             Parameter
             */
            if (action.equals(LIST_ACTION)) {
                List<Author> authors = null;
                authors = authService.allAuthors();
                request.setAttribute("authors", authors);
                destination = LIST_PAGE;
                
//                response.sendRedirect("/about.jsp");
                
            } else if (action.equals(ADD_ACTION)) {
                // coming soon
            } else if (action.equals(UPDATE_ACTION)) {
                String authorID = request.getParameter("Id");
                Author author = authService.getAuthor(authorID);
                request.setAttribute("author", author);
                destination = UPDATE_PAGE; 
                
            } else if (action.equals(DELETE_ACTION)) {
                String authorID = request.getParameter("Id");
                authService.deleteAuthor(authorID);
                //Reload page without deleted author
                this.refreshList(request, authService);
                destination = LIST_PAGE;
            } else if (action.equals(UPDATE_FINAL)) {
                String authorID = request.getParameter("authorID");
                String authorName = request.getParameter("authorName");
                String authorAdded = request.getParameter("authorAdded");
                
                ArrayList<String> descriptions = new ArrayList<>();
                descriptions.add("author_name");
                descriptions.add("date_added");
                
                ArrayList colValues = new ArrayList();
                colValues.add(authorName);
                colValues.add(authorAdded);
                
                authService.updateAuthor("author", descriptions, colValues, "author_id", authorID);
                this.refreshList(request, authService);
                destination = LIST_PAGE;
            } else if (action.equals(INSERT_ACTION)) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = new java.util.Date();
                request.setAttribute("date", dateFormat.format(date));
                destination = INSERT_PAGE;
          } else if (action.equals(INSERT_FINAL)){
                String authorName = request.getParameter("authorName");
                String authorAdded = request.getParameter("authorAdded");
                
                ArrayList<String> descriptions = new ArrayList<>();
                descriptions.add("author_name");
                descriptions.add("date_added");
                
                ArrayList colValues = new ArrayList();
                colValues.add(authorName);
                colValues.add(authorAdded);
                
                authService.insertAuthor("author", descriptions, colValues);
                this.refreshList(request, authService);
                destination = LIST_PAGE;
            } else if (action.equals(CANCEL_ACTION)){
                response.sendRedirect("about.jsp");
                return;
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

    private void refreshList(HttpServletRequest request, BookService authService) throws Exception {
        List<Author> authors = authService.allAuthors();
        request.setAttribute("authors", authors);
    }
    
    private BookService injectDependenciesAndGetAuthorService() throws Exception {
        
        Class dbClass = Class.forName(dbStrategyClassName);
        DbStrategy db = (DbStrategy) dbClass.newInstance();
        
        InterAuthorDAO authorDao = null;
        Class daoClass = Class.forName(daoClassName);
        Constructor constructor = null;
        try {
            constructor = daoClass.getConstructor(new Class[]{
            DbStrategy.class, String.class, String.class, String.class, String.class});
        } catch(NoSuchMethodException e) {
            // do nothing
        }
        
        if (constructor != null) {
            Object[] constructorArgs = new Object[]{
                db, driverClass, url, userName, password };
            authorDao = (InterAuthorDAO) constructor.newInstance(constructorArgs);
        
        } else {
        
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/book");
            constructor = daoClass.getConstructor(new Class[]{
                DataSource.class, DbStrategy.class
            });
            Object[] constructorArgs = new Object[]{ds,db};

            authorDao = (InterAuthorDAO) constructor.newInstance(constructorArgs);
        }
        
        return new BookService(authorDao);
    }
    
    @Override
    public void init() throws ServletException {
        // Get init params from web.xml
        ServletContext ctx = getServletContext();
        driverClass = ctx.getInitParameter("driverClass");
        url = ctx.getInitParameter("url");
        userName = ctx.getInitParameter("userName");
        password = ctx.getInitParameter("password");
        dbStrategyClassName = this.getServletContext().getInitParameter("dbStrategy");
        daoClassName = this.getServletContext().getInitParameter("authorDao");

        // You can't do the Java Reflection stuff here because exceptions
        // are thrown that can't be handled by this stock init() method
        // because the method signature can't be modified -- remember, you 
        // are overriding the method.
    }
    
    
    
    
    
}

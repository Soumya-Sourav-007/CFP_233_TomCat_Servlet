import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/LogInServlet")
public class LogInServlet extends HttpServlet {
    private static Matcher getValidation(String input, String regexPattern) {
        Pattern pattern = Pattern.compile(regexPattern);
        return pattern.matcher(input);
    }

    private void errorPrint(String errors, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Login.html");
        PrintWriter writer = response.getWriter();
        writer.println("<font color=red> " + errors + " </font>");
        dispatcher.include(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("user");
        String password = request.getParameter("password");

        boolean userResult = getValidation(username, RegLogIn.USERNAME_START_WITH_CAPITAL.getConstant()).find();
        boolean passwordResult = getValidation(password, RegLogIn.PASSWORD_HAVE_ONE_SPECIAL_CHARACHTER.getConstant()).find();
        if (!userResult || !passwordResult) {
            if(!userResult && !passwordResult){
                errorPrint("Invalid Username and Password both",request,response);
            } else if(!userResult){
                errorPrint("Invalid Username",request,response);
            } else {
                errorPrint("Invalid Password",request,response);
            }
        }else{
            if(username.equals("Soumya") && password.equals("baba@007")){
                HttpSession session = request.getSession();

                session.setAttribute("username",username);
                response.sendRedirect("sucess.jsp");

            }else {
                errorPrint("Incorrect Credentials",request,response);
            }
        }
    }
}

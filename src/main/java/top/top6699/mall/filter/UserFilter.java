package top.top6699.mall.filter;

import org.springframework.beans.factory.annotation.Autowired;
import top.top6699.mall.common.Constant;
import top.top6699.mall.model.pojo.User;
import top.top6699.mall.service.UserService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LongHaiJiang
 * @date 2021/10/23 23:33
 * @description 用户过滤器
 **/
public class UserFilter implements Filter {

    public static User currentUser;

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        servletResponse.setContentType("application/json;charset=utf-8");
        currentUser = (User) session.getAttribute(Constant.SOUTH_MALL_USER);
        if (currentUser == null) {
            PrintWriter out = new HttpServletResponseWrapper(
                    (HttpServletResponse) servletResponse).getWriter();
            out.write("{\n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_LOGIN\",\n"
                    + "    \"data\": null\n"
                    + "}");
            out.flush();
            out.close();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}

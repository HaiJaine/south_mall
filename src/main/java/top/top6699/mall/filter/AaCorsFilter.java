package top.top6699.mall.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author LongHaiJiang
 * @date 2021/11/17 15:46
 * @description
 **/

public class AaCorsFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //*号表示对所有请求都允许跨域访问
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String[] allowDomain = {
                "http://127.0.0.1",
                "http://localhost",
                "http://127.0.0.1:8080",
                "http://localhost:8080",
        };
        String origin = req.getHeader("Origin");
        if (Arrays.asList(allowDomain).contains(origin)) {
            res.addHeader("Access-Control-Allow-Credentials", "true");
            res.addHeader("Access-Control-Allow-Origin", origin);
            res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
            res.addHeader("Access-Control-Allow-Headers", "Content-Type,X-CAF-Authorization-Token,sessionToken,X-TOKEN");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

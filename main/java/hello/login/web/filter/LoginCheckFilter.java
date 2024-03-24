package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Slf4j
public class LoginCheckFilter implements Filter {
    private static final String[] whitelist = {"/", "/members/add", "/login", "/css/*"};

    //init 과 destroy 는 구현 안해도 됨
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작{}",requestURI);

            if (isLoginCheckPath(requestURI)) {
                HttpSession session = httpRequest.getSession();
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null)
                {
                    log.info("미인증 사용자{}", requestURI);
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;
                }
            }

            chain.doFilter(request, response);
        }catch (Exception e){
            throw e;
        }finally {
            log.info("인증 체크 필터 종료{}", requestURI);
        }
    }

    /**
     * whitelist 의 경우 인증 체크 x
     */
    private boolean isLoginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}

package com.ex.member.interceptor;

import com.ex.member.common.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class loginCheckInterceptor implements HandlerInterceptor {

    @Override // 기존 메서드 재정의 하는데 씀
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        // proHandle을 쓰는데 있어서 3개의 매개변수는 무조건 선언이 되어있어야함. -> 사용여부 관계 없음.
        // Client -> Server : Request
        // Server -> Client : Response

        // 사용자가 요청한 주소
        String requestURI = request.getRequestURI();
        // 어떻게 동작하는지 보기 위해서 찍음
        System.out.println("requestURI = " + requestURI);

        // 세션을 가지고 옴
        HttpSession session = request.getSession();
        // 세선에 로그인 정보가 있는지 확인
        if (session.getAttribute(SessionConst.LOGIN_EMAIL) == null) {
            // 미 로그인 상태
            // 로그인을 하지 않은 경우 바로 로그인 페이지로 보냄.
            // 로그인을 하면 요청한 화면을 보여줌.
//            response.sendRedirect("/member/login?redirectURL="+requestURI);

            session.setAttribute("redirectUTL", requestURI);
            response.sendRedirect("/member/login");

            return false;
        } else {
            // 로그인 상태

            return true;
        }

    }

}


// 오류가 발생했는데 로그인 하면 다시 / 페이지로 감.
/*
    문제가 발생한 이유
    - 사용자가 요청한 주소 값을 가지고 login.html 페이지로 갔는데.
      로그인을 하고 났을 때 요청한 주소값을 실행 시키지 못함/.(즉 주소값을 안가지고 가고 그냥 기본 값만 실행이 되게 만듬)

 */

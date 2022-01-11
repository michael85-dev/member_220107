package com.ex.member.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// @Configuration : 설정정보를 스프링실행시 등록해줌.
//@Configuration // 설정과 관련된 것을 실행할 때 씀.
// 위의 어노테이션을 주석처리하면 예외처리 하지 않음.... 즉 아래꺼에 대해서 실행하지 않으며 주소를 모두 접근가능하게 바뀜.
public class WebConfig implements WebMvcConfigurer {
    // 로그인 여부에 따른 접속 가능 페이지 구분
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 체인 메서드의 방법으로 작성된것. 아무데서나 쓸 수는 없음
        // registry의 addinter~ registry의 order~ 등등 의 의미.
        registry.addInterceptor(new loginCheckInterceptor()) // 만든 LoginCheckInterceptor 클래스 내용을 넘김
                .order(1) // 해당 인터셉터가 적용되는 순서
                .addPathPatterns("/**") // 해당 프로젝트의 모든 주소에 대해 인터셉터를 적용, 로그인 하면 모든 주소를 못보게 할껀데
                .excludePathPatterns("/", "/member/save", "/member/login", "/member/logout", "/css/**"); // 그 중에 이 주소는 제외, 이모든 주소중 여기는 제외함.

    }
}

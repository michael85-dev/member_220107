package com.example.member.controller;

import com.example.member.dto.MemberDetailDTO;
import com.example.member.dto.MemberLoginDTO;
import com.example.member.dto.MemberSaveDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // final 키워드가 붙은 필드만으로 생성자를 만들어줌
public class MemberController {
//    @Autowired
//    private MemberService ms; 기존 mybatis 방식
    private final MemberService ms;

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("member", new MemberSaveDTO()); // 타임리프가 있어서 할 수 있는 기능
        // 해당 명령어가 있기 때문에 save.html에서 th:field="*{}"가 가능

        return "member/save";
    }

//    @PostMapping("save")
//    public String save(@ModelAttribute MemberSaveDTO msDTO) {
//        ms.save(msDTO);
//
//        System.out.println("MemberController.save");
//        System.out.println("msDTO = " + msDTO);
//
//        return "index";
//    }
//
//    @GetMapping("/member/login")
//    public String login() {
//
//        return "member/";
//    }

    @PostMapping("save")
    public String save(@Validated @ModelAttribute("member") MemberSaveDTO msDTO, BindingResult bindingResult) { //NotBlank의 부분 적용을 위함
        // NotBlank를 안쓰면 그냥 @ModelAttribute MemberSaveDTO msDTO 로 쓰게 됨.
        System.out.println("MemberController.save"); //soutm
        System.out.println("msDTO = " + msDTO); // soutp

        if (bindingResult.hasErrors()) {
            return "member/save";
        }
        // 이메일 중복 처리
        try {
            ms.save(msDTO);
        } catch (IllegalStateException e){
            // e.getMessage()에는 서비스에서 지정한 예외 메세지가 담겨 있음.
            bindingResult.reject("emailCheck", e.getMessage());
        }
        return "redirect:/member/login"; // 리다이렉트가 없으면 문제가 생김.
    }

    @GetMapping("login")
    public String loginForm(Model model) {
        model.addAttribute("login", new MemberLoginDTO());

        return "member/login";
    }

    @PostMapping("login")
    public String login(@Validated @ModelAttribute("login") MemberLoginDTO mlDTO, BindingResult bindingResult, HttpSession session) { //NotBlank의 부분 적용을 위함
        // 데이터를 받아서 DB에서 조회
        // 있으면 반환 없으면 다른 처리가 필요함.
        // 오어스 (카카오톡, 네이버 등이 정보를 가지고 오는 것) -> 외부 API 이용.

        if (bindingResult.hasErrors()) { // 벨리데이션 체크
            return "member/login";
        }

        // 로그인 체크
        boolean loginResult = ms.login(mlDTO);

        if (loginResult) {
            session.setAttribute("loginEmail", mlDTO);
            return "redirect:/member/findAll";

        } else {
            bindingResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다."); // binding을 통해서 문제가 있을시 표출할 메세지 설정ss
            return "member/login";
        }

    }

    // 상세조회
    // /member/2 /member/15 ... -> /member/{memberId}
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {
                            // 얘가 번호 값을 가지고 오는 명령어
        System.out.println("memberId = " + memberId);

        MemberDetailDTO member = ms.findById(memberId); // DTO로 담아야 함.
        // 지정하는 이유는 메서드 만들때 리턴 타입이 저정되게 하기 위해서

        model.addAttribute("member", member);
        return "member/detail";
    }

    // 목록출력
    @GetMapping // 주소를 /member/*로 할 것이기 때문에 따로 주소를 적어주지 않음
    public String findAll(Model model) {
        List<MemberDetailDTO> mList = ms.findAll();
        model.addAttribute("mList", mList);

        return "member/findAll";
    }
}

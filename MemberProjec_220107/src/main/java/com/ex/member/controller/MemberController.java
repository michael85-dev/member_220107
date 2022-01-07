package com.ex.member.controller;

import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberLoginDTO;
import com.ex.member.dto.MemberSaveDTO;
import com.ex.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.List;

import static com.ex.member.common.SessionConst.LOGIN_EMAIL;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor // 생성자 주입을 받아야함. final 붙은 애들만 생성자로 만들어 줌.
public class MemberController {
    private final MemberService ms; // final이 있냐 없냐에 따라 생성자 생기는 것들이 다름.

    @GetMapping("save")
    public String saveForm() {

        return "member/save";
    }

    // 회원 가입
    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO msDTO) {
        Long memberId = ms.save(msDTO); // 기존에는 리턴이 없었으나 리턴으로 memberId를 잡음 <- pk!

        return "member/login";
    }

    // 로그인 폼 띄우기.
    @GetMapping("login")
    public String loginForm(Model model) {
//        model.addAttribute("login", new MemberLoginDTO); // 타임리프를 사용해서 명명할때 주어짐. 사용안했기 때문에 그냥 적어둔 것

        return "member/login";
    }

    @PostMapping("login")
    public String login(@ModelAttribute MemberLoginDTO mlDTO, BindingResult bResult, HttpSession hs) {
        boolean cResult = ms.login(mlDTO);

        if (cResult) {
            hs.setAttribute("loginEmail", mlDTO.getMemberEmail()); // sessionScope.loginEmail(~~~)로 쓸떄 쓰는거.
            // 만약 common 패키지에 SessionConst로 설정을 해놨다면 하단 처럼도 사용 가능.
            hs.setAttribute(LOGIN_EMAIL, mlDTO.getMemberEmail());

            return "redirect:/member/findAll";
        } else {
            bResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다.");

            return "member/login";
        }

    }

    //회원목록
    @GetMapping
    public String findAll(Model model) {
        List<MemberDetailDTO> mList = ms.findAll();
        model.addAttribute("mList", mList);

        return "member/findAll";
    }

    // 상세조회(회원조회) (ex> /member/5)
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model) {

        MemberDetailDTO mdDTO = ms.findById(memberId);

        model.addAttribute("member", mdDTO);

        return "member/findById";
    }

    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable("memberId") Long memberId) {
        // 만약 mapping의 이름이랑 Pathvariable의 이름이 같다면 생략 가능 함.
        // 즉 @PathVariable Long memberId로 가능
        MemberDetailDTO mdDTO = ms.findById(memberId);

        return mdDTO;
    }

    // 회원 삭제 Get 방식
    @GetMapping("delete/{memberId}")
    public String deleteGet(@PathVariable("memberId") Long memberId) {
        ms.deleteById(memberId);

        return "redirect:/member/findAll";
    }

    @DeleteMapping("{memberId}")
    public String delete(@PathVariable("memberId") Long memberId) {


        return "redirect:/member/findAll";
    }
}

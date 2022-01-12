package com.ex.member.controller;

import com.ex.member.dto.MemberDetailDTO;
import com.ex.member.dto.MemberLoginDTO;
import com.ex.member.dto.MemberSaveDTO;
import com.ex.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 원래 방식
//    @PostMapping("login")
//    public String login(@ModelAttribute MemberLoginDTO mlDTO, BindingResult bResult, HttpSession hs) {
//        boolean cResult = ms.login(mlDTO);
//
//        if (cResult) {
//            hs.setAttribute("loginEmail", mlDTO.getMemberEmail()); // sessionScope.loginEmail(~~~)로 쓸떄 쓰는거.
//            // 만약 common 패키지에 SessionConst로 설정을 해놨다면 하단 처럼도 사용 가능.
//            hs.setAttribute(LOGIN_EMAIL, mlDTO.getMemberEmail());
//
////            return "redirect:/member/"; // <- 주소 값임... 이걸 많이 헷갈릴 수도 있음
//            return "member/mypage";
//        } else {
//            bResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다.");
//
//            return "member/login";
//        }
//
//    }

    // interceptor 하고 나서의 방식
    @PostMapping("login")
    // redirectURL 파라미터를 가지고 올 수 있게 설정해둔것 -> interceptor에 의해서.
    public String login(@ModelAttribute MemberLoginDTO mlDTO, BindingResult bResult, HttpSession hs) { // @RequestParam(defaultValue = "/") String redirectURL
        System.out.println("MemberController.login");


        boolean cResult = ms.login(mlDTO);

        if (cResult) {
            hs.setAttribute("loginEmail", mlDTO.getMemberEmail()); // sessionScope.loginEmail(~~~)로 쓸떄 쓰는거.
            // 만약 common 패키지에 SessionConst로 설정을 해놨다면 하단 처럼도 사용 가능.
            hs.setAttribute(LOGIN_EMAIL, mlDTO.getMemberEmail());

//            return "redirect:/member/"; // <- 주소 값임... 이걸 많이 헷갈릴 수도 있음

            // 01.12일 변경된 것
            String redirectURL = (String) hs.getAttribute("redirectURL");

            System.out.println("redirectURL = " + redirectURL);
            // 인터셉터를 거쳐서 오면 redirectURL에 값이 있을 것이고. 그냥 로그인을 해서 오면 redirectURL에 값이 있을 것이기 때문에
            // if else로 구분을 함.
            if (redirectURL != null) {
                return "redirect:" + redirectURL; // 사용자가 요청한 주소로 보내주기 위해서.
            } else {
                return "redirect:/";
            }
        } else {
            bResult.reject("loginFail", "이메일 또는 비밀번호가 틀립니다.");

            return "member/login";
        }

    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "index";
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

    // 회원 삭제 Delete 방식(/member/5)
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById(@PathVariable("memberId") Long memberId) { // memberId는 같으므로 안해도 됨, ResponseEntity는 무엇인가.
        ms.deleteById(memberId);
        /*
            -> 단순 화면 출력이 아닌 데이터를 리턴하고자 할때 사용하는 리턴 방식. (ex. findById 등...)
            1. ResponseEntity 라는 것은 : 데이터 & 상태 코드를 함께 리턴할 수 있음
                -> 상태코드? (200, 400, 404, 405, 500 등.... 페이지 에러날때 볼 수 있던 것들.. 실제 코드당 어떤 부분에서 문제가 생기는지 아니면 그 문제를 가지고 있는것등에 대한 정보)
            2. @Responseody랑 : 데이터를 리턴 할 수 있음.
                -> ajax 등을 쓸 때 json 방식으로 데이터를 꺼내기 위해서 데이터만 선택해서 보냄
         */
        // 200코드를 리턴. -> ok라는 코드는 200을 뜻ㅎ.ㅁ -> HTTP 코드 검색하면 MAD? 어쩌구에서 볼 수 이씀.
        // ok 코드 입력시 무조건 ... success로...
        // fail(bad request : 400) 입력시 error로.. 감....
        return new ResponseEntity(HttpStatus.OK);
    }

    // 수정.... 은 다음과 같은 절차로 가봄
    /*
        로그인 -> 마이페이지로 이동 -> 수정으로 이동
     */
//    @GetMapping("update")
//    public String updateForm(@RequestParam("loginEmail") Long memberId, Model model) {
//        MemberDetailDTO mlDTO = ms.findById(memberId);
//        System.out.println(mlDTO.toString());
//
//        model.addAttribute("mlDTO", mlDTO);
//
//        return "member/update";
//    }
    // T sol
    @GetMapping("update")
    public String updateForm(Model model, HttpSession hs) {
        String memberEmail = (String)hs.getAttribute(LOGIN_EMAIL);// 이 방식을 통해 email 정보를 가지고 옴 -> getAttribute는 Object이기 때문에 형변환을 강제로 해줘야함.
//        MemberUpdateDTO mpDTO = ms.findMyEmail(memberEmail); // 만약 타임리프를 쓸꺼면.
        MemberDetailDTO mdDTO = ms.findByEmail(memberEmail);
        model.addAttribute("member", mdDTO);

        return "member/update";
    }

    // 수정 처리(post)
    @PostMapping("update")
    public String updatePost(@ModelAttribute MemberDetailDTO mdDTO) {
        Long memberId = ms.update(mdDTO); // service에 해당 데이터를 보내기 위한 것.


        // 수정 완료 후 해당 회원의 상세 페이지(findById.html) 출력
        return "redirect:/member/" + mdDTO.getMemberId();
    }

    // 수정 처리(put)
    @PutMapping("{memberId}") // json으로 넘길때 Id도 같이 넘겼기 때문에 굳이 필요하지는 않음.
    // json읋 전달되면 @RequestBody로 받아줘야함.
    public ResponseEntity updatePut(@RequestBody MemberDetailDTO memberDetailDTO) {
        Long memberId = ms.update(memberDetailDTO); // memberId가 넘어가게 하기 위한 것.

        return new ResponseEntity(HttpStatus.OK);
    }
}

package com.hmh.board.controller;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j // log를 기록할 수 있는 것.
public class BoardController {
    private final BoardService bs;

    @GetMapping("save")
    public String saveForm(Model model) {
        model.addAttribute("bsave", new BoardSaveDTO());

        return "/board/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO, HttpSession session) {
        Long boardId = bs.save(boardSaveDTO);
//        session.setAttribute("loginId", boardSaveDTO.);

//        return "redirect:/board/";
        return "index";
    }

    @GetMapping //아무것도 적지 않으면 /board/가 넘어옴
    // requestMapping에서 /board로 하면은 /를 적어줘야함.
    public String findAll(Model model) {
        List<BoardDetailDTO> bList = bs.findAll();
        model.addAttribute("bList", bList);

        return "board/findAll";
    }

    @GetMapping("{boardId}")
    public String findByContents(@PathVariable("boardId") Long boardId, Model model) {
        log.info("글보기 메서드 호출. 요청글 번호 : {}", boardId); // {} 안에 지정한 변수가 찍힘.
        /*
            로그 그분에는 단계가 있음.
            1. info
            2. warning
            3. error
            4. chase
            ....
         */
        BoardDetailDTO boardDetailDTO = bs.findById(boardId);

        model.addAttribute("board", boardDetailDTO);

        return "board/detail";
    }

    @PostMapping("{boardId}")
    public @ResponseBody BoardDetailDTO detail(@PathVariable("boardId") Long boardId) {
        BoardDetailDTO board = bs.findById(boardId);

        return board;
    }
}

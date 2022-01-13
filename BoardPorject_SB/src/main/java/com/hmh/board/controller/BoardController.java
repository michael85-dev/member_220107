package com.hmh.board.controller;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        return "board/findById";
//        return "board/detail";
    }

    // responsebody를 사용한 Detail 조회 방법 (detailAjax)
//    @PostMapping("{boardId}")
//    public @ResponseBody BoardDetailDTO detail(@PathVariable("boardId") Long boardId) {
//        BoardDetailDTO board = bs.findById(boardId);
//
//        return board;
//    }

    @PostMapping("{boardId}")
    public ResponseEntity findById2(@PathVariable Long boardId) {
        BoardDetailDTO board = bs.findById(boardId);

        return new ResponseEntity<BoardDetailDTO>(board, HttpStatus.OK);
        //                         DTO 부부는 생략이 가능하지만 명명해주는 것이 좋음 <- 안헷갈림.
    }

    // My T
//    @PostMapping("{boardId}")
//    public String update(@PathVariable("boardId") Long boardId, @ModelAttribute BoardDetailDTO boardDetailDTO) {
//        boardId = bs.update(boardDetailDTO);
//
//        return "/board/findAll";
//    }
//
//    @PutMapping("{boardId}")
//    public ResponseEntity updateAjax(@PathVariable("boardId") Long boardId, @ModelAttribute BoardDetailDTO boardDetailDTO) {
//        boardId = bs.update(boardDetailDTO);
//        if (boardId != null) {
//            return new ResponseEntity(HttpStatus.OK);
//        } else {
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//
//    }

    // By T
    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("b", new BoardDetailDTO());
        model.addAttribute("board", board);

        return "board/update";
    }

    @PostMapping("/uodate")
    public String update(@ModelAttribute BoardDetailDTO boardDetailDTO) {

        bs.update(boardDetailDTO);
        return "redirect:/board/" + boardDetailDTO.getBoardId(); //DTO에 담고 있기 때문에 그렇슴...
    }

    @PutMapping("{boardId}") // 주소 체계 차이 떄문에
    public ResponseEntity updateAjax(@RequestBody BoardDetailDTO boardDetailDTO) { // ResponseEntity 를 쓸 때는 반드시 @ResponseBody로 받아야함.
        bs.update(boardDetailDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

}

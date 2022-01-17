package com.hmh.board.controller;

import com.hmh.board.dto.CommentDetailDTO;
import com.hmh.board.dto.CommentSaveDTO;
import com.hmh.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comment/*")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService cs;

    @PostMapping("save")
    public @ResponseBody List<CommentDetailDTO> save(@ModelAttribute CommentSaveDTO commentSaveDTO) { // 갑자기 왜 DetailDTO가 나온 것인가? -> 등록후에 해당 데이터를 불러서 넣어줘야 하기 때문이지.
        Long commentId = cs.save(commentSaveDTO);

        List<CommentDetailDTO> commentDetailDTOList = cs.findAll(commentSaveDTO.getBoardId());

        return commentDetailDTOList;
    }
}

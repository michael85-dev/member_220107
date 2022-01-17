package com.hmh.board.service;

import com.hmh.board.dto.CommentDetailDTO;
import com.hmh.board.dto.CommentSaveDTO;

import java.util.List;

public interface CommentService {
    Long save(CommentSaveDTO commentSaveDTO);

    List<CommentDetailDTO> findAll(Long boardId);
}

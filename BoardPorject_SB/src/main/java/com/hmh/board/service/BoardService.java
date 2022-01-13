package com.hmh.board.service;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardSaveDTO;

import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO);

    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);

    Long update(BoardDetailDTO boardDetailDTO);
}

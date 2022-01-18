package com.hmh.board.service;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardPageDTO;
import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO);

    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);

    Long update(BoardDetailDTO boardDetailDTO);

    Page<BoardPageDTO> paging(Pageable pageable);

    List<BoardDetailDTO> findAll(Long memberId);
}

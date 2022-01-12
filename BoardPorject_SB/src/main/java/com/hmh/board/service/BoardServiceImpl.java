package com.hmh.board.service;

import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.entity.BoardEntity;
import com.hmh.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository br;

    @Override
    public Long save(BoardSaveDTO boardSaveDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO);

        Long boardId = br.save(boardEntity).getId();

        return boardId;
    }

    @Override
    public List<BoardDetailDTO> findAll() {
        List<BoardEntity> boardEntityList = br.findAll();
        List<BoardDetailDTO> bList = new ArrayList<>();

        for (BoardEntity b: boardEntityList) {
            bList.add(BoardDetailDTO.toBoardDetailDTO(b));
        }

        return bList;
    }

    @Override
    public BoardDetailDTO findById(Long boardId) {
        Optional<BoardEntity> board = br.findById(boardId);

        BoardDetailDTO boardDetailDTO = null;

        if(board.isPresent()) {
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(board.get());
        } else {
            return null;
        }
        /*
            Optional 객체 메서드
            - isPresent() : 데이터가 있으면 true, 없으면 false 반환
            - isEmpty() : 데이터가 없으면 true, 있으면 false 반환
         */

        return boardDetailDTO;
    }
}

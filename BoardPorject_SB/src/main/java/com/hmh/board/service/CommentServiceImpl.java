package com.hmh.board.service;

import com.hmh.board.dto.CommentDetailDTO;
import com.hmh.board.dto.CommentSaveDTO;
import com.hmh.board.entity.BoardEntity;
import com.hmh.board.entity.CommentEntity;
import com.hmh.board.repository.BoardRepository;
import com.hmh.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository cr;
    private final BoardRepository br; // boardId만 가지고 있지만 그걸 가지고 Entity데이터를 불러와서 넣어야 함.

    @Override
    public Long save(CommentSaveDTO commentSaveDTO) {
        Optional<BoardEntity> boardEntityOptional = br.findById(commentSaveDTO.getBoardId());
        BoardEntity boardEntity = boardEntityOptional.get();

        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentSaveDTO, boardEntity);

        Long commentId = cr.save(commentEntity).getId();

        return commentId;
    }

    @Override
    public List<CommentDetailDTO> findAll(Long boardId) {
        Optional<BoardEntity> boardEntityOptional = br.findById(boardId);
        BoardEntity boardEntity = boardEntityOptional.get();

        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList(); // 위의 2개의 작업이 중요함 이유는 BoardEntity를 통해서 두개의 연결을 맺었기 때문에.

        // Entity의 내용을 Detail에 담아서 보내야 함.
        List<CommentDetailDTO> commentList = new ArrayList<>();

        for (CommentEntity c: commentEntityList) {
            CommentDetailDTO commentDetailDTO = CommentDetailDTO.toCommentDetailDTO(c);
            commentList.add(commentDetailDTO);
        }

        // 또는
//        for (CommentEntity c: commentEntityList) {
//            CommentDetailDTO commentDetailDTO = CommentDetailDTO.toCommentDetailDTO(c, boardId);
//            commentList.add(commentDetailDTO);
//        }

        return commentList;
    }
}

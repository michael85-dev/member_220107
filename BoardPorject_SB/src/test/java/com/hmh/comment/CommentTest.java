package com.hmh.comment;

import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.dto.CommentDetailDTO;
import com.hmh.board.dto.CommentSaveDTO;
import com.hmh.board.entity.CommentEntity;
import com.hmh.board.repository.BoardRepository;
import com.hmh.board.repository.CommentRepository;
import com.hmh.board.service.BoardService;
import com.hmh.board.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;


public class CommentTest {
    @Autowired
    private BoardService bs;
    @Autowired
    private CommentService cs;
    @Autowired
    private BoardRepository br;
    @Autowired
    private CommentRepository cr;

    @Test
    @Transactional
    @Rollback(value = false) // 롤백 안함.
    @DisplayName("코멘트를 제대로 달 수 있는가.") // BoardTest부분에도 동일한 것을 만들어 보았고 그 것이전에 여기서 선생님 과의 비교를 위해서 따로 작성을 해둔다.
    public void CommentSaveTest() {
        // 게시글 존재.
        BoardSaveDTO boardSaveDTO = new BoardSaveDTO("BoardWriter", "BoardContents", "BoardTitle", "BoardContents");
        Long boardId = bs.save(boardSaveDTO);

        // 댓글 생성
        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(boardId, "commentWriter", "commentContents");
        cs.save(commentSaveDTO);



    }

    @Test
    @Transactional
    @DisplayName("댓글 조회")
    public void commentReviewTest() {
        CommentEntity commentEntity = cr.findById(1L).get(); // 1L이라고 쓴 이유 : Long type의 1번 변수 라는 의미. // float type은 f라고 붙여야함.
        System.out.println("commentEntity.toString() = " + commentEntity.toString());
        System.out.println("commentEntity.getId() = " + commentEntity.getId());
        System.out.println("commentEntity.getCommentWriter() = " + commentEntity.getCommentWriter());
        System.out.println("commentEntity.getCommentContents() = " + commentEntity.getCommentContents());
        System.out.println("commentEntity.getBoardEntity() = " + commentEntity.getBoardEntity());

        System.out.println("commentEntity.getBoardEntity().getBoardWriter() = " + commentEntity.getBoardEntity().getBoardWriter()); // boardEntity 안의 내용을 추가 조회가 가능함.
    }
    
    @Test
    @Transactional
    @DisplayName("전체 댓글 목록 출력")
    public void findAllTest() {
        List<CommentDetailDTO> commentDetailDTOList = cs.findAll(1L);
        for (CommentDetailDTO c: commentDetailDTOList) {
            System.out.println("c.toString() = " + c.toString());
        }
    }
}

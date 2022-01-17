package com.hmh.board;

import com.hmh.board.common.PagingConst;
import com.hmh.board.dto.BoardPageDTO;
import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.dto.CommentSaveDTO;
import com.hmh.board.entity.BoardEntity;
import com.hmh.board.repository.BoardRepository;
import com.hmh.board.repository.CommentRepository;
import com.hmh.board.service.BoardService;
import com.hmh.board.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

public class BoardTest {
    @Autowired
    private BoardService bs;

    @Autowired
    private BoardRepository br;

    @Autowired
    private CommentRepository cr;

    @Autowired
    private CommentService cs;

    @Test
    @DisplayName("게시판 생성")
    public void newBoard() {
        IntStream.rangeClosed(1, 30).forEach(i -> {
            bs.save(new BoardSaveDTO("writer"+i, "pass"+i, "title"+i, "contents"+i));
        });
    }

    // 페이징 관련 테스트 코드
    @Test
    @DisplayName("삼항 연산자")
    public void test1() {
        int num = 10;
        int num2 = 0;
        if (num == 10) {
            num2 = 5;
        } else {
            num2 = 100;
        }

        // if 문 처럼 동작하는 연산자
        num2 = (num == 10)? 5: 100; // num 이 10이면 num2가 5, 아니면 200
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("페이지 테스트")
    public void pagingTest() {
        // 페이지 값을 바로 줘서 리파지토리에서 바로 보는 방식으로?
        int page = 0;
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));

        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContents() = " + boardEntities.getContent()); // 요청 페이지에 들어 있는 데이터. 주소값이 찍히고 주소값 없애고 내용 보려면 toString이나 이런거로 봐야함.
        System.out.println("boardEntities.getTotalElements = " + boardEntities.getTotalElements()); // 전체 글 갯수.
        System.out.println("boardEntities.get\\ = " + boardEntities.getNumber()); // 요청페이지
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 개수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 인지
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 인지.
        // 위에 처럼 나온 이유는 페이지를 1페이지 (page = 0)으로 설정했기 때문에.

        // Entity의 기능들을 그대로 살려서 DTO로 담기 위한 방법
        // Page<BoardEntity> -> Page<BoardPageDTO>로 담는 작업
        // map(): 엔티티가 담긴 페이지 객체를 dto가 담긴 페이지 객체로 변환해주는 역활
        Page<BoardPageDTO> boardList = boardEntities.map(
                // board : Entity 객체를 담기위한 반복용 변수 : 명명 자유
                // ex: forEach 사용할 때 b 이런식으로 적은거랑 같은 것
                board -> new BoardPageDTO(board.getId(),
                                          board.getBoardWriter(),
                                          board.getBoardTitle())
        );

        System.out.println("boardList.getTotalElements() = " + boardList.getTotalElements()); // 이것처럼 메서드 사용 가능.
    }

    @Test
    @Transactional // 연관 관계가 있을때 해줘야 함.
    @DisplayName("댓글 작성 코멘트 달기?")
    public void CommnetTest(CommentSaveDTO commentSaveDTO) {
        // try 1
//        BoardEntity boardEntity = new BoardEntity();
//        Long boardId = boardEntity.getId();
////        String CommentWriter = null;
////        String CommentContensts = null;
//
////        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(boardId, "CommentWriter", "CommentContents");
//
//
//        IntStream.rangeClosed(1, 30).forEach(i -> {
//            cs.save(new CommentSaveDTO(boardId ,"commentWriter" + i, "commentContents" + i));
//        });
//
//        cs.save(commentSaveDTO);

        // try 2

        Long boardId = null;

        IntStream.rangeClosed(1, 30).forEach(i -> {
            bs.save(new BoardSaveDTO("boardWriter" + i, "boardPassword" + i, "boardTitle" + i, "boardContents"+ i));
        });

        boardId = new BoardEntity().getId();

        Long finalBoardId = boardId;
        IntStream.rangeClosed(1, 20).forEach(i -> {
            cs.save(new CommentSaveDTO(finalBoardId, "commentWriter"+ i, "commentContents"+ i));
        });

        // commentTest에서 보자
    }

    @Test
    @Transactional
    @DisplayName("게시글 삭제")
    public void boardDelete() {
        br.deleteById(1L); // 이렇게 하면 에러가 발생함. -> 밑에 딸린 데이터가 존재하기 때문에.
    }
}

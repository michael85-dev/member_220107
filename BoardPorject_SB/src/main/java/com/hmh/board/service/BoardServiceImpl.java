package com.hmh.board.service;

import com.hmh.board.common.PagingConst;
import com.hmh.board.dto.BoardDetailDTO;
import com.hmh.board.dto.BoardPageDTO;
import com.hmh.board.dto.BoardSaveDTO;
import com.hmh.board.entity.BoardEntity;
import com.hmh.board.entity.MemberEntity;
import com.hmh.board.repository.BoardRepository;
import com.hmh.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository br;
    private final MemberRepository mr;

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

    @Override
    public Long update(BoardDetailDTO boardDetailDTO) {
        BoardEntity boardEntity = BoardEntity.updateEntity(boardDetailDTO);

        Long getId = br.save(boardEntity).getId();

        return getId;
    }

    @Override
    public Page<BoardPageDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // jpa는 1페이지를 0번으로 인식함.
        // 요청한 페이지가 1이면 페이지 값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
// 기존 : page = page -1;
        page = (page == 1)? 0: (page -1);

        Page<BoardEntity> boardEntityList = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // 페이지            페이지 얼만 ㅏ보여줄지,    내림차순 여부,          order by 의 기준되는 것(Entity 필드 이름)
        // Page<BoardEntity> -> Page<BoardPagingDTO>
        // 만약 그냥 옴겨담으면 Entity에서 제공하는 메서드를 하나도 못쓰기 때문에 방법을 다르게 담아야 함.

        Page<BoardPageDTO> boardList = boardEntityList.map(
                // board : Entity 객체를 담기위한 반복용 변수 : 명명 자유
                // ex: forEach 사용할 때 b 이런식으로 적은거랑 같은 것
                board -> new BoardPageDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle())
        );


        return boardList;
    }

    @Override
    public List<BoardDetailDTO> findAll(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId);
        MemberEntity memberEntity = memberEntityOptional.get();

        List<BoardEntity> boardEntityList = memberEntity.getBoardEntityList();

        List<BoardDetailDTO> boardList = new ArrayList<>();

        for (BoardEntity b: boardEntityList) {
            BoardDetailDTO boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(b);
            boardList.add(boardDetailDTO);
        }

        return boardList;
    }
}

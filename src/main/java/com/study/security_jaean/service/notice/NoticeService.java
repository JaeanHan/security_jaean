package com.study.security_jaean.service.notice;

import com.study.security_jaean.web.dto.notice.AddNoticeReqDto;
import com.study.security_jaean.web.dto.notice.GetNoticeListRespDto;
import com.study.security_jaean.web.dto.notice.GetNoticeRespDto;

import java.util.List;

public interface NoticeService {
    public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
    public GetNoticeRespDto getNotice(String flag, int noticeCode) throws Exception;
    public List<GetNoticeListRespDto> getNoticeList(int page, String searchFlag, String searchValue) throws Exception;
}

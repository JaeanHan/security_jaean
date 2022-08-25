package com.study.security_jaean.web.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetNoticeListRespDto {
    private int noticeCode;
    private String noticeTitle;
    private String userId;
    private String createDate;
    private int noticeCount;
    private int totalNoticeCount;
}

package com.study.security_jaean.web.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetNoticeRespDto {
    private int noticeCode;
    private String noticeTitle;
    private int userCode;
    private String userId;
    private String createData;
    private int noticeCount;
    private String noticeContent;
    private List<Map<String, Object>> downloadFiles;
}

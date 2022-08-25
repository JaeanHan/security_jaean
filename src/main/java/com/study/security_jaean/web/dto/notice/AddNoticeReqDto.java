package com.study.security_jaean.web.dto.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data //content type: form data
public class AddNoticeReqDto {
    private String noticeTitle;
    private int userCode;
    private String ir1;
    private List<MultipartFile> file; // 스프링에서 지원해주는 객체(디펜던시 추가가능)
}

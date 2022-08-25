package com.study.security_jaean.domain.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NoticeFile {
    private int file_code;
    private int notice_code;
    private String file_name;
}

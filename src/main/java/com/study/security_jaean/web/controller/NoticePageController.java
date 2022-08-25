package com.study.security_jaean.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticePageController {

    @GetMapping("/list")
    public String loadNotice() {
        return"notice/notice";
    }
    @GetMapping("/addition")
    public String loadNoticeInsert() {
        return"notice/notice_insert";
    }
    @GetMapping("/detail/{noticeCode}")
    public String loadNoticeDetail(@PathVariable String noticeCode) {
        return"notice/notice_detail";
    }
    @GetMapping("/modification/{noticeCode}")
    public String loadNoticeModify(@PathVariable String noticeCode) {
        return"notice/notice_modify";
    }
}

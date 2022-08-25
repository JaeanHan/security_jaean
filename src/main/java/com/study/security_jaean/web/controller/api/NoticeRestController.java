package com.study.security_jaean.web.controller.api;

import com.study.security_jaean.service.notice.NoticeService;
import com.study.security_jaean.web.dto.CMRespDto;
import com.study.security_jaean.web.dto.notice.AddNoticeReqDto;
import com.study.security_jaean.web.dto.notice.GetNoticeListRespDto;
import com.study.security_jaean.web.dto.notice.GetNoticeRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/v1/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeRestController {

    @Value("${file.path}")
    private String filePath;

    private final NoticeService noticeService;

    @GetMapping("/list/{page}")
    public ResponseEntity<?> getNoticeList(@PathVariable int page,@RequestParam String searchFlag, @RequestParam String searchValue) {
        List<GetNoticeListRespDto> listRespDtos;

        log.info("{}, {}", searchFlag, searchValue);

        try {
            listRespDtos = noticeService.getNoticeList(page, searchFlag, searchValue);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", null));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "lookup successful", listRespDtos));
    }

    @PostMapping("")
    public ResponseEntity<?> addNotice(AddNoticeReqDto addNoticeReqDto) {
        log.info(">>>>> {}", addNoticeReqDto);
        log.info(">>>>> fileName: {}", addNoticeReqDto.getFile().get(0).getOriginalFilename());

        int noticeCode = 0;

        try {
            noticeCode = noticeService.addNotice(addNoticeReqDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "failed to write", noticeCode));
        }

        return ResponseEntity.ok(new CMRespDto<>(1, "completed creation", noticeCode));
    }

    @GetMapping("/{noticeCode}")
    public ResponseEntity<?> getNotice(@PathVariable int noticeCode) {
        GetNoticeRespDto getNoticeRespDto = null;
        try {
            getNoticeRespDto = noticeService.getNotice(null, noticeCode);
            if(getNoticeRespDto == null) {
                return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "request failed", null));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", null));
        }
        return ResponseEntity.ok().body(new CMRespDto<>(1, "lookup successful", getNoticeRespDto));
    }

    @GetMapping("/{flag}/{noticeCode}")
    public ResponseEntity<?> getNotice(@PathVariable String flag, @PathVariable int noticeCode) {
        GetNoticeRespDto getNoticeRespDto = null;
        if(flag.equals("pre") || flag.equals("next")) {
            try {
                getNoticeRespDto = noticeService.getNotice(flag, noticeCode);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database error", null));
            }
        }else {
            return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "request failed", null));
        }
        return ResponseEntity.ok().body(new CMRespDto<>(1, "lookup successful", getNoticeRespDto));
    }

    @GetMapping("/file/download/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
        Path path = Paths.get(filePath + "notice/" + fileName);

        String contentType = Files.probeContentType(path); // get mimetype from file

        log.info("contentType : {}", contentType); // 확인용

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileName, StandardCharsets.UTF_8)
                .build());

        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        Resource resource = new InputStreamResource(Files.newInputStream(path));

        return ResponseEntity.ok().headers(headers).body(resource);
    }
}

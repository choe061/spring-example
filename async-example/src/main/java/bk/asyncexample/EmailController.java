package bk.asyncexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/email")
@RestController
@RequiredArgsConstructor
public class EmailController {
    private static final List<Integer> MEMBERS = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private final EmailService service;

    @GetMapping("kakao")
    public ResponseEntity<String> sendToKakao() {
        service.sendToKakao(MEMBERS);
        return ResponseEntity.ok("OK!");
    }

    @GetMapping("naver")
    public ResponseEntity<Map<Boolean, List<Integer>>> sendToNaver() {
        log.info("this is controller");
        return ResponseEntity.ok(service.sendToNaver(MEMBERS));
    }
}

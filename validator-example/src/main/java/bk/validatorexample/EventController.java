package bk.validatorexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    @PostMapping("message")
    public ResponseEntity<MessageDTO> saveMessage(@RequestBody @Valid MessageDTO message) {
        if (message.getName().equals("test")) {
            throw new IllegalArgumentException("test");
        }
        return ResponseEntity.ok(message);
    }
}

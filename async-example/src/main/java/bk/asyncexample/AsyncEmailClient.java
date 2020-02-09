package bk.asyncexample;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class AsyncEmailClient implements EmailClient {

    @Async("asyncKakaoEmailTaskExecutor")
    @SneakyThrows
    @Override
    public void sendToKakao(final int memberNumber) {
        log.info("member number : {}", memberNumber);
        Thread.sleep(3_000);
    }

    @Async("asyncNaverEmailTaskExecutor")
    @SneakyThrows
    @Override
    public CompletableFuture<EmailSentResultDTO> sendToNaver(final int memberNumber) {
        log.info("member number : {}", memberNumber);
        Thread.sleep(3_000);

        if (memberNumber % 2 == 0) {
            return CompletableFuture.completedFuture(new EmailSentResultDTO(memberNumber, false));
        }

        return CompletableFuture.completedFuture(new EmailSentResultDTO(memberNumber, true));
    }
}

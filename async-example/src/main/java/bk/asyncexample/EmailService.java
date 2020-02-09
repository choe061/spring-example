package bk.asyncexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailClient emailClient;

    public void sendToKakao(final List<Integer> members) {
        for (final var member : members) {
            emailClient.sendToKakao(member);
        }
    }

    public Map<Boolean, List<Integer>> sendSyncToNaver(final List<Integer> members) {
        // 아래 코드는 stream 의 lazy evaluation 때문인지 CompletableFuture 를 리턴 받는데 동작이 비동기로 동작하지 않는다.
        return members.stream()
                      .map(emailClient::sendToNaver)
                      .map(CompletableFuture::join)
                      .collect(partitioningBy(EmailSentResultDTO::isSuccess, mapping(EmailSentResultDTO::getMemberNumber, toList())));
    }

    public Map<Boolean, List<Integer>> sendToNaver(final List<Integer> members) {
        log.info("this is service");
        final var results = members.stream()
                                   .map(emailClient::sendToNaver)
                                   .collect(toList());
        return results.stream()
                      .map(CompletableFuture::join)
                      .collect(partitioningBy(EmailSentResultDTO::isSuccess, mapping(EmailSentResultDTO::getMemberNumber, toList())));
    }
}

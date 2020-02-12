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

    public Map<Boolean, List<Integer>> sendToNaver(final List<Integer> members) {
        return members.stream()
                      .map(emailClient::sendToNaver)
                      .map(CompletableFuture::join)
                      .collect(partitioningBy(EmailSentResultDTO::isSuccess, mapping(EmailSentResultDTO::getMemberNumber, toList())));
    }
}

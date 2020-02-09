package bk.asyncexample;

import java.util.concurrent.CompletableFuture;

public interface EmailClient {
    void sendToKakao(int memberNumber);

    CompletableFuture<EmailSentResultDTO> sendToNaver(int memberNumber);
}

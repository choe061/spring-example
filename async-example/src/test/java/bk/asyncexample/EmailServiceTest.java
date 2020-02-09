package bk.asyncexample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EmailServiceTest {
    private EmailService service;
    private EmailClient client;

    @BeforeEach
    void setUp() {
        client = mock(EmailClient.class);
        service = new EmailService(client);
    }

    @Test
    void sendToKakao() {
        // Given
        final var members = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        // When
        service.sendToKakao(members);
        // Then
        verify(client, timeout(100).times(10)).sendToKakao(anyInt());
    }

    @Test
    void sendToNaver() {
        // Given
        final var members = List.of(1, 2);
        given(client.sendToNaver(1)).willReturn(CompletableFuture.completedFuture(new EmailSentResultDTO(1, true)));
        given(client.sendToNaver(2)).willReturn(CompletableFuture.completedFuture(new EmailSentResultDTO(2, false)));
        // When
        final var resultMap = service.sendToNaver(members);
        // Then
        assertAll(
                () -> verify(client, timeout(100).times(2)).sendToNaver(anyInt()),
                () -> assertThat(resultMap.get(true)).hasSize(1),
                () -> assertThat(resultMap.get(false)).hasSize(1)
        );
    }
}

package bk.asyncexample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncThreadPoolConfiguration {

    @Bean
    public Executor asyncKakaoEmailTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(8);
        threadPoolTaskExecutor.setMaxPoolSize(8);
        threadPoolTaskExecutor.setThreadNamePrefix("AsyncKakaoEmailTask-");
        return threadPoolTaskExecutor;
    }

    @Bean
    public Executor asyncNaverEmailTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // cf) default core pool size is 1
        threadPoolTaskExecutor.setCorePoolSize(8);
        threadPoolTaskExecutor.setMaxPoolSize(8);
        threadPoolTaskExecutor.setThreadNamePrefix("AsyncNaverEmailTask-");
        return threadPoolTaskExecutor;
    }
}

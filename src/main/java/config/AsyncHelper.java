package idb2camp.b2campjufrin.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
public class AsyncHelper {

    private AsyncHelper() {
    }

    public static CompletableFuture<Void> executeAsync(Runnable fun) {
        try {
            return CompletableFuture.runAsync(fun);
        } catch (Exception e) {
            log.warn("[file sender backgroundJob]", e);
            return null;
        }

    }
}

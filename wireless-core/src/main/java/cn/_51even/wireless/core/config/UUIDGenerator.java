package cn._51even.wireless.core.config;

import org.springframework.stereotype.Component;
import org.springframework.util.IdGenerator;

import java.util.UUID;

@Component
public class UUIDGenerator implements IdGenerator {
    @Override
    public UUID generateId() {
        return UUID.randomUUID();
    }
}

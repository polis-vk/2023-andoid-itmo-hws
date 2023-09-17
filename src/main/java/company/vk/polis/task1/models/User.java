package company.vk.polis.task1.models;

import company.vk.polis.task1.Entity;
import org.jetbrains.annotations.Nullable;

public record User(Integer id, String name, @Nullable String avatarUrl) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
}
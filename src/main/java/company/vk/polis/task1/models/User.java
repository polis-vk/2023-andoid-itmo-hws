package company.vk.polis.task1.models;

import org.jetbrains.annotations.Nullable;

public record User(Integer id, String name, @Nullable String avatarUrl) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Boolean isValid() {
        return id != null && name != null;
    }


}
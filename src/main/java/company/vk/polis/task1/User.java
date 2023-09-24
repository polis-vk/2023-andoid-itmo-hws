package company.vk.polis.task1;

import org.jetbrains.annotations.Nullable;

public record User(Integer id, String name, @Nullable String avatarUrl) implements JavaEntity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isValid() { return id != null && name != null; }
}
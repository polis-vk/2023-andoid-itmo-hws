package company.vk.polis.task1;

import org.jetbrains.annotations.Nullable;

public record User(Integer id, String name, @Nullable String avatarUrl) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean Valid() {
        if (id == null || id < 0 || name == null){
            return false;
        }
        return true;
    }


}
package company.vk.polis.task1;

import org.jetbrains.annotations.Nullable;

public record User(Integer id, String name, @Nullable String avatarUrl) implements KotlinEntity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean checkValid() {
        if (id == null || id < 0 || name == null){
            return false;
        }
        return true;
    }

}
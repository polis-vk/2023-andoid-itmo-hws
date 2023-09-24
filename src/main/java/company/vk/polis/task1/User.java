package company.vk.polis.task1;

import org.jetbrains.annotations.Nullable;

import java.util.List;

public record User(Integer id, String name, @Nullable String avatarUrl, List<Integer> groupChatIds) implements JavaEntity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isValid() { return id != null && name != null; }

    public void addToGroupChat(Integer chatId)
    {
        groupChatIds.add(chatId);
    }
}
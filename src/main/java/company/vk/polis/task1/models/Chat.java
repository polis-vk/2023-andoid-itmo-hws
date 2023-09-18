package company.vk.polis.task1.models;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements ChatEntity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Boolean isValid() {
        return id != null && userIds != null && messageIds != null;
    }

    @Override
    public boolean checkUser(int userId) {
        return userIds.senderId() == userId || userIds.receiverId() == userId;
    }

    @NotNull
    @Override
    public List<Integer> messageIds() {
        return messageIds;
    }
}

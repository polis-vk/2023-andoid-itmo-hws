package company.vk.polis.task1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity, Chatable {
    @Override
    public Integer getId() {
        return id;
    }

    @NotNull
    @Override
    public List<Integer> getMessages() {
        return messageIds;
    }

    @NotNull
    @Override
    public List<Integer> getUsers() {
        return List.of(userIds.senderId());
    }
}

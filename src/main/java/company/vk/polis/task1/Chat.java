package company.vk.polis.task1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity, ChatInterface {
    @Override
    public Integer getId() {
        return id;
    }

    @NotNull
    @Override
    public List<Integer> getMessageIds() {
        return messageIds;
    }

    @NotNull
    @Override
    public List<Integer> getSenderIds() {
        return List.of(userIds.senderId());
    }
}

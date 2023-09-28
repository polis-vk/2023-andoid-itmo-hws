package company.vk.polis.task1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements BaseChat {
    @Override
    public Integer getId() {
        return id;
    }

    @NotNull
    @Override
    public List<Integer> getMessageIds() {
        return messageIds;
    }

}

package company.vk.polis.task1;

import java.util.List;

public record Chat(
        Integer id,
        UserPair userIds,
        List<Integer> messageIds
) implements Entity, ChatInterface {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public List<Integer> getMessageIds() {
        return messageIds;
    }
}

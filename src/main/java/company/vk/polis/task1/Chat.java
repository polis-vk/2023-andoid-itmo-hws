package company.vk.polis.task1;

import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements MetaChat {

    @Override
    public List<Integer> getUserIds() {
        if (userIds == null) {
            return List.of();
        }
        return List.of(userIds.senderId(), userIds.receiverId());
    }

    @Override
    public List<Integer> getMessageIds() {
        return messageIds == null ? List.of() : messageIds;
    }

    @Override
    public Integer getId() {
        return id;
    }
}

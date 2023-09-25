package company.vk.polis.task1;

import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
    public List<Integer> get_message_Ids() {
        return messageIds;
    }
}

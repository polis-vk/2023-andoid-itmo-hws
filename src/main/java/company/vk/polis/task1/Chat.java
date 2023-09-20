package company.vk.polis.task1;

import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public Boolean isValid() {
        return id != null && userIds != null && messageIds != null;
    }
    public List<Integer> get_message_Ids() {
        return messageIds;
    }
}

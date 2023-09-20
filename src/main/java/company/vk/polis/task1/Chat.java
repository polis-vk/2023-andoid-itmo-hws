package company.vk.polis.task1;

import java.util.ArrayList;
import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity, ChatInterface {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public List<Integer> getUserIds() {
        List<Integer> list = new ArrayList<>();
        list.add(userIds.receiverId());
        list.add(userIds.senderId());
        return list;
    }


    @Override
    public List<Integer> getMessageIds() {
        return messageIds;
    }
}

package company.vk.polis.task1;

import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements AllChats{

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public List<Integer> getListMessagesId() {
        return messageIds;
    }

    @Override
    public List<Integer> getListUsersId() {
        return List.of(userIds.senderId(), userIds.receiverId());
    }

    @Override
    public String getAvatar() {
        return "nothing";
    }
}

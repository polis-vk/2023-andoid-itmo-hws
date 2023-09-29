package company.vk.polis.task1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements AllChats{

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean checkValid() {
        if (id == null || userIds == null|| id < 0 || messageIds == null){
            return false;
        }
        return true;
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

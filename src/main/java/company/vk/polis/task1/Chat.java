package company.vk.polis.task1;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity, AllChats{

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean Valid() {
        if (id == null || userIds == null|| id < 0 || messageIds == null){
            return false;
        }
        return true;
    }

    @Override
    public int getType() {
        return 1;
    }

    public UserPair ListMembers(){
        return userIds;
    }


    @Override
    public int ChatId() {
        return id;
    }
}

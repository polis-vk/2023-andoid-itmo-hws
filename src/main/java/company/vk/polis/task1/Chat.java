package company.vk.polis.task1;


import java.util.List;

record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements ChatInterface {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public List<Integer> getMessageIds() {
        return messageIds;
    }
}

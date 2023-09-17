package company.vk.polis.task1.models;

import company.vk.polis.task1.Entity;

import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
}

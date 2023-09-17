package company.vk.polis.task1.models;

import company.vk.polis.task1.Entity;

public record Message(Integer id, String text, Integer senderId, Long timestamp, State state) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
}

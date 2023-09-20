package company.vk.polis.task1;

public record Message(Integer id, String text, Integer senderId, Long timestamp, MessageState state) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
}

package company.vk.polis.task1;

public record Message(Integer id, String text, Integer senderId, Long timestamp, MessageState state) implements JavaEntity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isValid() { return id != null && text != null && senderId != null && timestamp != null && state != null; }
}

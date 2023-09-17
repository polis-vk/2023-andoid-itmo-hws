package company.vk.polis.task1.models;

public record Message(Integer id, String text, Integer senderId, Long timestamp, State state) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public Boolean isValid() {
        return id != null && text != null && senderId != null && timestamp != null && state != null;
    }
}

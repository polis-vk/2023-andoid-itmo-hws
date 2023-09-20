package company.vk.polis.task1;

record Message(Integer id, String text, Integer senderId, Long timestamp, State_Msg state) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }
    @Override
    public Boolean isValid() {
        return id != null && text != null && senderId != null && timestamp != null && state != null;
    }
}

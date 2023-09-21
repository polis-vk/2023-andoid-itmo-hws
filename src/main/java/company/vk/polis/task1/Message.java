package company.vk.polis.task1;

record Message(Integer id, String text, Integer senderId, Long timestamp) implements Entity {
    private static State state = State.UNREAD;


    public State getState() {
        return state;
    }

    public void setState(State state) {
        Message.state = state;
    }

    @Override
    public Integer getId() {
        return id;
    }
}

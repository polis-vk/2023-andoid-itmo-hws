package company.vk.polis.task1;

public record Message(Integer id, String text, Integer senderId, Long timestamp, State state) implements Entity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean Valid() {
        if (id == null || timestamp == null|| id < 0 || text == null || senderId == null || senderId < 0 || timestamp < 0 || state == null){
            return false;
        }
        return true;
    }


    public State getState() {
        return state;
    }


}

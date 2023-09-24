package company.vk.polis.task1;

import java.util.List;

public record Chat(Integer id, UserPair userIds, List<Integer> messageIds) implements JavaEntity {
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isValid() { return id != null && userIds != null && messageIds != null; }
}

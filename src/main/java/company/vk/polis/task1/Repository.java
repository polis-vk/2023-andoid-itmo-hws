package company.vk.polis.task1;

import company.vk.polis.task1.models.Entity;

import java.util.List;

public interface Repository {
    static List<Entity> getInfo() {
        return DataUtils.generateEntity();
    }
}

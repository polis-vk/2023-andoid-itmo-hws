package company.vk.polis.task1.repository;

import java.util.List;

import company.vk.polis.task1.models.Entity;

public interface Repository {
    static List<Entity> getInfo() {
        return DataUtils.generateEntity();
    }
}

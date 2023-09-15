package company.vk.polis.task1.repository

import company.vk.polis.task1.entity.Entity

interface Repository {
    val info: List<Entity>
    fun <E : Entity> addEntity(e: E)
}

object RandomRepository : Repository {
    override val info: List<Entity>
        get() = DataUtils.generateEntity()

    override fun <E : Entity> addEntity(e: E) {
        DataUtils.generateEntity()
    }
}

object StorageRepository : Repository {
    private val entities = mutableListOf<Entity>()

    override val info: List<Entity>
        get() = entities

    override fun <E : Entity> addEntity(e: E) {
        entities += e
    }
}

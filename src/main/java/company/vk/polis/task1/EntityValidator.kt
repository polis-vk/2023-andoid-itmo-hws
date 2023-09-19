package company.vk.polis.task1

class EntityValidator {
    companion object {
        @JvmStatic
        fun validateEntity(entity: Entity): Boolean {
            return null != entity.id && entity.id >= 0 && when (entity) {
                is User -> null != entity.name
                is Message -> null != entity.text && null != entity.senderId && null != entity.timestamp && null != entity.state
                is Chat -> null != entity.userIds && null != entity.getMessageIds()
                is GroupChat -> true
                else -> throw NotImplementedError("Entity of type ${entity.javaClass} is not implemented")
            }
        }
    }
}
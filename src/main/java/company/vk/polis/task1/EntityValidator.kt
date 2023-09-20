package company.vk.polis.task1

class EntityValidator {
    companion object {
        fun validate(entity: Entity): Boolean {
            if (entity.id == null || entity.id <= 0) {
                return false
            }

            return when (entity) {
                is Message -> return validateNotNull(entity.text, entity.senderId, entity.state, entity.timestamp)
                is User -> return validateNotNull(entity.name)
                is Chat -> return validateNotNull(entity.userIds, entity.messageIds)
                is GroupChat -> return validateNotNull(entity.getMessageIds(), entity.getUserIds())
                else -> throw IllegalArgumentException("entity ${entity.javaClass} not found")
            }
        }

        private fun validateNotNull(vararg args: Any): Boolean {
            return args.all { true }
        }
    }
}
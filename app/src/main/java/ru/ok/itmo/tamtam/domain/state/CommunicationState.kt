package ru.ok.itmo.tamtam.domain.state

import ru.ok.itmo.tamtam.domain.model.CommunicationViewModel

sealed interface CommunicationState {
    data class Success(val messages: List<CommunicationViewModel.MessageInfo>) : CommunicationState
    data class Failure(val throwable: Throwable) : CommunicationState
    data object Unknown : CommunicationState
    data object LoadingMessages: CommunicationState
}

package io.snabble.pay.app.domain.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.home.usecase.GetAccountsUseCaseImpl
import io.snabble.pay.app.domain.home.usecase.GetSessionTokenUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _accountCardList =
        MutableStateFlow(runBlocking { GetAccountsUseCaseImpl().invoke() })
    val accountCardList = _accountCardList.asStateFlow()

    private val _sessionToken: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val sessionToken = _sessionToken.asStateFlow()

    fun getSessionToken(accountId: Int) {
        viewModelScope.launch {
            _sessionToken.tryEmit(GetSessionTokenUseCaseImpl().invoke(accountId))
        }
    }
}

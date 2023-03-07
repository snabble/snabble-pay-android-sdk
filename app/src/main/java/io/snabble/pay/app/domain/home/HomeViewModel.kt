package io.snabble.pay.app.domain.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.snabble.pay.app.domain.home.usecase.GetAccountsUseCase
import io.snabble.pay.app.domain.home.usecase.GetSessionTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccounts: GetAccountsUseCase,
    private val getSession: GetSessionTokenUseCase,
) : ViewModel() {

    private val _accountCardList =
        MutableStateFlow(runBlocking { getAccounts() })
    val accountCardList = _accountCardList.asStateFlow()

    private val _sessionToken: MutableStateFlow<String?> =
        MutableStateFlow(null)
    val sessionToken = _sessionToken.asStateFlow()

    fun getSessionToken(accountId: String) {
        viewModelScope.launch {
            val accounts = _accountCardList.first().map { accMod ->
                if (accMod.accountId == accountId) {
                    accMod.copy(qrCodeToken = getSession(accountId))
                } else {
                    accMod
                }
            }
            _accountCardList.tryEmit(accounts)
        }
    }
}

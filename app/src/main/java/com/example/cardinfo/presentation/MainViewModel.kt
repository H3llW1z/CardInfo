package com.example.cardinfo.presentation

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cardinfo.data.implementation.CardInfoRepositoryImpl
import com.example.cardinfo.domain.entity.CardInfo
import com.example.cardinfo.domain.entity.Result
import com.example.cardinfo.domain.usecases.AddToPreviousRequestsUseCase
import com.example.cardinfo.domain.usecases.GetCardInfoUseCase
import com.example.cardinfo.domain.usecases.GetPreviousRequestsUseCase
import com.example.cardinfo.domain.usecases.RemoveFromPreviousRequestsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = CardInfoRepositoryImpl(application)
    private val getCardInfoUseCase = GetCardInfoUseCase(repository)
    private val getPreviousRequestsUseCase = GetPreviousRequestsUseCase(repository)
    private val addToPreviousRequestsUseCase = AddToPreviousRequestsUseCase(repository)
    private val removeFromPreviousRequestsUseCase = RemoveFromPreviousRequestsUseCase(repository)

    private val _state: MutableLiveData<State> = MutableLiveData()
    val state: LiveData<State>
        get() = _state

    val previousRequests: LiveData<List<CardInfo>> = getPreviousRequestsUseCase()

    fun loadCardInfo(bin: String) {
        if (!bin.isDigitsOnly() || bin.length < 6) {
            _state.value =  State.InputError
            return
        }
        viewModelScope.launch {
            _state.value = State.Progress
            when (val result = getCardInfoUseCase(bin)) {
                is Result.Success -> {
                    _state.value = State.Success(result.data)
                    addToPreviousRequestsUseCase(result.data)
                }
                is Result.Error.NetworkError -> {
                    _state.value = State.NetworkError
                }
                is Result.Error.ServerError -> {
                    _state.value = State.ServerError
                }
                is Result.Error.CardNotFound -> {
                    _state.value = State.CardNotFound
                }
            }
        }
    }

    fun removeFromPreviousRequests(bin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            removeFromPreviousRequestsUseCase(bin)
        }
    }
}
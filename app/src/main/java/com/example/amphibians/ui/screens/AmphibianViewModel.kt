package com.example.amphibians.ui.screens

import android.text.Editable.Factory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.amphibians.AmphibianApplication
import com.example.amphibians.data.AmphibiansRepository
import com.example.amphibians.model.AmphibianCardItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AmphibianViewModel(private val amphibiansRepository: AmphibiansRepository) : ViewModel(){

    sealed interface AmphibianUiState{
        data class Success(val amphibians: List<AmphibianCardItem>) : AmphibianUiState
        object Error : AmphibianUiState
        object Loading : AmphibianUiState
    }

    var amphibianUiState: AmphibianUiState by mutableStateOf(AmphibianUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians(){
        viewModelScope.launch {
            amphibianUiState = try{
                AmphibianUiState.Success(amphibiansRepository.getAmphibians())
            } catch (e: IOException){
                AmphibianUiState.Error
            } catch (e: HttpException){
                AmphibianUiState.Error
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibianApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibianViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}
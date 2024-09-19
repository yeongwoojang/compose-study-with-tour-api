package com.example.tourmanage.viewmodel

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tourmanage.UiState
import com.example.tourmanage.common.ServerGlobal
import com.example.tourmanage.common.data.server.item.AreaItem
import com.example.tourmanage.common.util.PermissionUtils
import com.example.tourmanage.ui.home.OverlayRoute
import com.example.tourmanage.usecase.domain.area.GetAreaUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val getAreaUseCase: GetAreaUseCase,
    @ApplicationContext private val applicationContext: Context
): AndroidViewModel(applicationContext as Application) {

    private val _exceptionState = MutableSharedFlow<Throwable>()
    val exceptionState = _exceptionState.asSharedFlow()

    private val ceh = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            _exceptionState.emit(throwable)
        }
    }


    private val _locationFlow = MutableStateFlow<Boolean>(false)
    val locationFlow = _locationFlow.onStart {
        getLocation(applicationContext)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), false)

    private val _areaCodesState = MutableStateFlow<UiState<ArrayList<AreaItem>>>(UiState.Loading())
    val areaCodesState = _areaCodesState.onStart {
        getAreaCode()
    }.stateIn(viewModelScope + ceh, SharingStarted.WhileSubscribed(5000,), UiState.Loading())

    private fun getAreaCode() {
        viewModelScope.launch(ceh) {
            val data = getAreaUseCase().getOrThrow()
            _areaCodesState.value = UiState.Success(data)
        }
    }

    private fun getLocation(context: Context) {
        val onSuccessListener = OnSuccessListener<Location> { location ->
            location?.let {
                Timber.i("좌표: ${it.latitude}, ${it.longitude}")
                ServerGlobal.setGPS(it)
                _locationFlow.value = true
            }
        }
        val onFailureListener = OnFailureListener {
            _locationFlow.value = true
        }

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener)
    }
}
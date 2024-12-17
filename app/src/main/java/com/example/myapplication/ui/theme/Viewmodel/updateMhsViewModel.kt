package com.example.myapplication.ui.theme.Viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.entity.Mahasiswa
import com.example.myapplication.repository.RepositoryMhs
import com.example.myapplication.ui.theme.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class updateMhsViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
) : ViewModel() {

    var updateUIState by mutableStateOf(MhsUIState())
        private set

    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            updateUIState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState(mahasiswaEvent: MahasiswaEvent) {
        updateUIState = updateUIState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    fun validateFields() : Boolean {
        val event = updateUIState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            kelas =  if (event.kelas.isNotEmpty()) null else "Kelas tidak boleh kosong",
            angkatan =  if (event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong",
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currentEvent = updateUIState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data berhasil diupdate",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("SnackBarMessageDiatur: ${updateUIState.snackbarMessage}")
                } catch (e:Exception) {
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data Gagal Diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data Gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }
}

fun Mahasiswa.toUIStateMhs() : MhsUIState = MhsUIState (
    mahasiswaEvent = this.toDetailUiEvent(),
)
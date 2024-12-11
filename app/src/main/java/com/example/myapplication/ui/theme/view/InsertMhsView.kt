package com.example.myapplication.ui.theme.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.Viewmodel.FormErrorState
import com.example.myapplication.ui.theme.Viewmodel.MahasiswaEvent
import com.example.myapplication.ui.theme.Viewmodel.MahasiswaViewModel
import com.example.myapplication.ui.theme.Viewmodel.MhsUIState
import com.example.myapplication.ui.theme.Viewmodel.PenyediaViewModel
import com.example.myapplication.ui.theme.navigation.AlamatNavigasi
import kotlinx.coroutines.launch


object DestinasiInsert: AlamatNavigasi{
    override val route: String = "insert_mhs"
}

@Preview(showBackground = true)
@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){

    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nama,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.nama != null,
            placeholder = { Text("Masukkan nama") },
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.nim,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(nim = it))
            },
            label = { Text("NIM") },
            isError = errorState.nim != null,
            placeholder = { Text("Masukkan NIM") },
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.size(14.dp))
        Text(text = "Jenis Kelamin")
        Row(
            modifier=Modifier.fillMaxWidth())
        {
            jenisKelamin.forEach { jk->
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {  RadioButton(
                    selected = mahasiswaEvent.jenisKelamin == jk,
                    onClick = {
                        onValueChange(mahasiswaEvent.copy(jenisKelamin = jk))
                    },
                )
                    Text(text = jk)

                }
                Text(
                    text = errorState.jenisKelamin ?: "",
                    color = Color.Red
                )
            }
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.alamat,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan alamat") },
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.size(14.dp))
        Text(text = "Kelas")
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            kelas.forEach { kls->
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {  RadioButton(
                    selected = mahasiswaEvent.kelas == kls,
                    onClick = {
                        onValueChange(mahasiswaEvent.copy(kelas = kls))
                    },
                )
                    Text(text = kls)

                }
                Text(
                    text = errorState.kelas ?: "",
                    color = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.size(14.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mahasiswaEvent.angkatan,
            onValueChange = {
                onValueChange(mahasiswaEvent.copy(angkatan = it))
            },
            label = { Text("Angkatan") },
            isError = errorState.angkatan != null,
            placeholder = { Text("Masukkan angkatan") },
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}
@Composable
fun InsertMhsView(
    onBack: ()->Unit,
    onNative: ()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(factory =  PenyediaViewModel.Factory)// Inisialisasi ViewModel

){
    val uiState = viewModel.uiState // Ambil ui state dari view model
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    // Observasi perubahan snackBarMessage
    LaunchedEffect(uiState.snackbarMessage)  {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

}
@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUIState,
    onClick:() -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMahasiswa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Simpan")
        }
    }
}
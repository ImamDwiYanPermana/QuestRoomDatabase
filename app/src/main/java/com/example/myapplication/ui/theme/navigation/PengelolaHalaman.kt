package com.example.myapplication.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.view.DestinasiInsert
import com.example.myapplication.ui.theme.view.InsertBodyMhs
import com.example.myapplication.ui.theme.view.InsertMhsView


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiInsert.route
    ) {
        composable(
            route = DestinasiInsert.route
        ){
            InsertMhsView(
                onBack = {}, onNative = {}
            )
        }
    }
}
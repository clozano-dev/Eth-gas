package com.clozanodev.ethereumgastracker.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.clozanodev.ethereumgastracker.R
import com.clozanodev.ethereumgastracker.viewmodel.GasViewModel
import com.clozanodev.ethereumgastracker.ui.screen.HomeScreen
import com.clozanodev.ethereumgastracker.ui.screen.InfoScreen
import com.clozanodev.ethereumgastracker.ui.screen.NotificationsScreen

@Composable
fun MainScreen(viewModel: GasViewModel) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .background(Color(0xFF8992b1)),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.ethereum_home),
                            contentDescription = "Ethereum Logo",
                            modifier = Modifier.size(56.dp).padding(end = 8.dp)
                        )

                        Text("Ethereum Gas Tracker")
                    }
                },
                backgroundColor = Color(0xFF464A74),
                contentColor = Color(0xFFE0E2EE),
                elevation = 10.dp
            )
        }, bottomBar = {
            BottomNavigationBar(navController)
        }) { innerPadding ->
        NavHost(
            navController, startDestination = "home", modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(viewModel) }
            composable("notifications") { NotificationsScreen(viewModel) }
            composable("info") { InfoScreen() }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomNavigation(
        backgroundColor = Color(0xFF464A74),
        contentColor = Color(0xFFE0E2EE)
    )
    {
        val currentRoute = currentRoute(navController)
        BottomNavigationItem(icon = { Icon(Icons.Filled.Home, contentDescription = null) },
            label = { Text(text = "Home") },
            selected = currentRoute == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        BottomNavigationItem(icon = { Icon(Icons.Filled.Notifications, contentDescription = null) },
            label = { Text(text = "Notifications") },
            selected = currentRoute == "notifications",
            onClick = {
                navController.navigate("notifications") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
        BottomNavigationItem(icon = { Icon(Icons.Filled.Info, contentDescription = null) },
            label = { Text(text = "Information") },
            selected = currentRoute == "info",
            onClick = {
                navController.navigate("info") {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


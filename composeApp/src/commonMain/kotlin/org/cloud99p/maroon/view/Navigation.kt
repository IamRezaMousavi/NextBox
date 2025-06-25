package org.cloud99p.maroon.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.cloud99p.maroon.view.screen.CounterScreen
import org.cloud99p.maroon.view.screen.HomeScreen
import org.cloud99p.maroon.view.screen.TransactionDetails
import org.cloud99p.maroon.view.screen.TransactionsScreen

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object Counter : Screen()

    @Serializable
    data object Transactions : Screen()

    @Serializable
    data class TransactionDetail(val id: Long) : Screen()
}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home,
        modifier = modifier
    ) {
        composable<Screen.Home> {
            HomeScreen(navController)
        }

        composable<Screen.Counter> {
            CounterScreen(navController)
        }

        composable<Screen.Transactions> {
            TransactionsScreen(navController)
        }

        composable<Screen.TransactionDetail> { navBackStackEntry ->
            val transactionId = navBackStackEntry.toRoute<Long>()
            TransactionDetails(transactionId = transactionId, navController = navController)
        }
    }
}

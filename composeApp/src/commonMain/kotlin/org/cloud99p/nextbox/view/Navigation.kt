package org.cloud99p.nextbox.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import org.cloud99p.nextbox.view.screen.account.AccountAdd
import org.cloud99p.nextbox.view.screen.home.CounterScreen
import org.cloud99p.nextbox.view.screen.home.HomeScreen
import org.cloud99p.nextbox.view.screen.transaction.TransactionAdd
import org.cloud99p.nextbox.view.screen.transaction.TransactionDetails

sealed class Screen {
    @Serializable
    data object Home : Screen()

    @Serializable
    data object Counter : Screen()

    @Serializable
    data object TransactionAdd : Screen()

    @Serializable
    data class TransactionDetail(val id: Long) : Screen()

    @Serializable
    data object AccountAdd : Screen()
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

        composable<Screen.TransactionAdd> {
            TransactionAdd(navController)
        }

        composable<Screen.TransactionDetail> { navBackStackEntry ->
            val transaction = navBackStackEntry.toRoute<Screen.TransactionDetail>()
            TransactionDetails(transactionId = transaction.id, navController = navController)
        }

        composable<Screen.AccountAdd> {
            AccountAdd(navController)
        }
    }
}

package net.cserny.commanderclient.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import net.cserny.commanderclient.R

enum class CommanderScreen(@StringRes val title: Int) {
    Loading(title = R.string.loading_screen_title),
    Servers(title = R.string.servers_screen_title),
    Commands(title = R.string.commands_screen_title),
    Shutdown(title = R.string.shutdown_screen_title)
}

@Composable
fun CommanderTopBar(
    currentScreen: CommanderScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CommanderApp(
    modifier: Modifier = Modifier,
    viewModel: ServersViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CommanderScreen.valueOf(
        backStackEntry?.destination?.route ?: CommanderScreen.Loading.name
    )

    Scaffold(
        topBar = {
            CommanderTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CommanderScreen.Loading.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CommanderScreen.Loading.name) {
                LoadingScreen(
//                    status = uiState.status,
//                    quantityOptions = quantityOptions,
//                    onNextButtonClicked = {
//                        viewModel.setQuantity(it)
//                        navController.navigate(CupcakeScreen.Flavor.name)
//                    }
                )
            }
        }
    }
}

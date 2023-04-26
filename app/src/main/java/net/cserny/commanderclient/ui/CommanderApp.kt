package net.cserny.commanderclient.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
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
import net.cserny.commanderclient.data.ServerAction
import net.cserny.commanderclient.ui.commands.ShutdownScreen

enum class CommanderScreen(@StringRes val title: Int) {
    Servers(title = R.string.servers_screen_title),
    Commands(title = R.string.commands_screen_title),
    Shutdown(title = R.string.shutdown_screen_title);

    companion object {
        fun convert(action: ServerAction) : CommanderScreen {
            return when (action) {
                ServerAction.SHUTDOWN -> Shutdown
            }
        }
    }
}

@Composable
fun CommanderTopBar(
    currentScreen: CommanderScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    refresh: () -> Unit,
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
            } else {
                IconButton(onClick = refresh) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
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
        backStackEntry?.destination?.route ?: CommanderScreen.Servers.name
    )

    Scaffold(
        topBar = {
            CommanderTopBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                refresh = {
                    viewModel.resetState()
                },
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CommanderScreen.Servers.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CommanderScreen.Servers.name) {
                viewModel.loadServers()
                ServersScreen(uiState.status, uiState.serverDtos, viewModel, navController)
            }

            composable(route = CommanderScreen.Commands.name) {
                CommandsScreen(uiState.currentServer, navController)
            }

            composable(route = CommanderScreen.Shutdown.name) {
                ShutdownScreen(uiState.currentServer, viewModel, uiState.actionExecuted)
            }
        }
    }
}

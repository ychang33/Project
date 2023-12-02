package com.example.project.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.components.AppToolbar
import com.example.project.components.BottomNavigationBar
import com.example.project.components.RewardsLazyColumn
import com.example.project.components.TitleComponent
import com.example.project.data.RegistrationViewModel
import com.example.project.data.RewardData
import com.example.project.functions.getRewardsDataFromFirebase
import com.example.project.navigation.defaultNavItems


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Rewards(
    registrationViewModel: RegistrationViewModel,
    navController: NavHostController,
    uid: String
) {
    var uid = uid

    val rewardsList by remember { mutableStateOf(mutableListOf<RewardData>()) }

    // Fetch user data from Firebase when the screen is first created
    LaunchedEffect(rewardsList) {
        getRewardsDataFromFirebase { rewardsData ->
            rewardsList.clear()
            rewardsList.addAll(rewardsData)
        }
    }

    var bottomNavState by rememberSaveable { mutableIntStateOf(1) }
    var context = LocalContext.current

    Scaffold(
        topBar = {
            AppToolbar(
                toolbarTitle = "Rewards",
                logoutButtonClicked = {
                    registrationViewModel.logout()
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = defaultNavItems,
                selectedIndex = bottomNavState,
                onItemSelected = { index ->
                    bottomNavState = index
/*                    when (defaultNavItems[index].title) {
                        "Dashboard" -> navController.navigate("Dashboard/$uid")
                        "Rewards" -> navController.navigate("Rewards/$uid")
                        "Account" -> navController.navigate("Account/$uid")
                    }*/
                    when(defaultNavItems[index].title){
                        context.resources.getString(R.string.nav_dashboard) -> navController.navigate(context.resources.getString(
                            R.string.nav_dashboard) + "/$uid")
                        context.resources.getString(R.string.nav_rewards) -> navController.navigate(context.resources.getString(
                            R.string.nav_rewards) + "/$uid")
                        context.resources.getString(R.string.nav_account) -> navController.navigate(context.resources.getString(
                            R.string.nav_account) + "/$uid")
                    }
                }
            )
        }
    ) { contentPadding ->

        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .padding(contentPadding)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 5.dp),
                    elevation = CardDefaults.cardElevation(),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    TitleComponent("All Rewards")

                    RewardsLazyColumn(rewardsList = rewardsList, onItemClick = { reward ->
                        if (reward != null) {
                            navController.navigate("Redeem/${reward.rewardId}/$uid")
                        } else {
                            // Handle the case where rewardId is null or empty
                            navController.navigate("Rewards/$uid")

                            Log.e("Navigation", "Invalid rewardId: ${reward.rewardId}")
                        }
                    })
                }
            }
        }
    }
}


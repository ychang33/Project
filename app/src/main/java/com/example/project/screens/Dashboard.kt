package com.example.project.screens

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.project.components.AppToolbar
import com.example.project.components.ButtonWithIconAndMessageComponent
import com.example.project.components.HeadingComponent
import com.example.project.components.MessageComponent
import com.example.project.components.SubheadingComponent
import com.example.project.components.TextButtonComponent
import com.example.project.components.WelcomeBackComponent
import com.example.project.data.RegistrationUIEvent
import com.example.project.data.RegistrationViewModel
import com.example.project.data.UserRecord
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class NavItemState(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Dashboard(registrationViewModel: RegistrationViewModel, navController: NavHostController,  uid: String) {

    var firstName by remember { mutableStateOf("") }
    var accumulatedPoints by remember { mutableStateOf(0) }

    // Fetch user data from Firebase when the screen is first created
    LaunchedEffect(uid) {

        Log.d(TAG, "LaunchedEffect - UID: $uid")
        getUserDataFromFirebase(uid) { userRecord ->
            userRecord?.let {
                firstName = it.firstName
                accumulatedPoints = it.accumulatedPoints

                Log.d(TAG, "Fetched user data: firstName=$firstName, accumulatedPoints=$accumulatedPoints, uid: $uid")
            }
        }
    }




    val items = listOf(
        NavItemState(
            title = "Dashboard",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavItemState(
            title = "Register",
            selectedIcon = Icons.Filled.Email,
            unselectedIcon = Icons.Outlined.Email
        ),
        NavItemState(
            title = "Account",
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face
        )
    )

    var bottomNavState by rememberSaveable { mutableStateOf(0)}

    Scaffold (
        topBar = {
             AppToolbar(
                 toolbarTitle = "PointGrow",
                 logoutButtonClicked = {
                     registrationViewModel.logout()
                 }
             )
        },


        bottomBar = {
            NavigationBar (
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(20.dp)),
                containerColor = MaterialTheme.colorScheme.secondary.copy (alpha = .5f)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavState == index,
                        onClick = { },

                        icon = {
                            Icon(
                                imageVector = if (bottomNavState == index) item.selectedIcon
                                else item.unselectedIcon,
                                contentDescription = item.title
                            )
//                            }
                        },
                        label = {
                            Text(text = item.title)
                        },
                        colors = NavigationBarItemDefaults.colors(
//                            selectedIconColor = Color(0xFF552A27),
                            selectedTextColor = Color(0xFF131F0D)
//                            indicatorColor = Color(0xFFF1F1EA)
                        )
                    )
                }
            }
        }
    ){ contentPadding ->

        Surface (
            modifier = Modifier.fillMaxWidth()
        ){

            Column (
                modifier = Modifier
                    .padding(contentPadding)
            ) {
                WelcomeBackComponent(firstName = firstName, points = accumulatedPoints)
            }
}
    }
}

//@Composable
fun getUserDataFromFirebase(uid: String, onResult: (UserRecord?) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val userRecord = snapshot.getValue(UserRecord::class.java)
            onResult(userRecord)
        }

        override fun onCancelled(error: DatabaseError) {
            // Handle error
            onResult(null)
        }
    })
}
@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.project.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Celebration
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Discount
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.project.R
import com.example.project.data.MyRewardData
import com.example.project.data.RewardData
import com.example.project.functions.updateAccumulatedPoints
import com.example.project.functions.updateMyRewardInFirebase
import com.example.project.functions.updateMyRewardStatus
import com.example.project.functions.updateRewardQuantity
import com.example.project.functions.updateTransactionInFirebase
import com.example.project.navigation.NavItemState

// Login, LoginFailure, Registration, RegFailure
@Composable
fun HeadingComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp)
            .padding(start = 30.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
}

@Composable
fun SubheadingComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
//            .fillMaxWidth()
            .heightIn(min = 30.dp)
            .padding(start = 30.dp, top = 3.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        color = MaterialTheme.colorScheme.tertiary
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun InputComponent(
    labelValue: String,
    iconName: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    var input by remember { mutableStateOf("") }

    OutlinedTextField(
        value = input,
        onValueChange = {
            input = it
            onTextSelected(it)
        },
        label = { Text(text = labelValue) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
            cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = .7f)
        ),
        leadingIcon = {
            Icon(imageVector = iconName, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions( // Set Keyboard
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        isError = !errorStatus
    )
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PasswordComponent(
    labelValue: String,
    iconName: ImageVector,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) } // password is not visible

    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        value = password,
        onValueChange = {
            password = it
            onTextSelected(it)
        },
        label = { Text(text = labelValue) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary.copy(alpha = .5f),
            cursorColor = MaterialTheme.colorScheme.primary.copy(alpha = .7f)
        ),
        leadingIcon = {
            Icon(imageVector = iconName, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if (passwordVisible) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            var description = if (passwordVisible) {
                "Hide Password"
            } else {
                "Show Password"
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },

        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        keyboardOptions = KeyboardOptions( // Set Keyboard
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        isError = !errorStatus
    )
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AlignRightTextComponent(value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 25.dp, 0.dp)
            .height(30.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        TextButton(
            onClick = { }
        ) {
            Text(
                text = value, fontSize = 10.sp, fontStyle = FontStyle.Italic
            )
        }
    }
}


@Composable
fun MessageComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 5.dp)
            .padding(40.dp, 10.dp, 40.dp, 5.dp),
        fontSize = 13.sp,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
    )
}


@Composable
fun ButtonComponent(
    value: String,
    iconName: ImageVector,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false,
    errorMessage: String? = null
) {

    // Display an error message if provided
    errorMessage?.let {
        Text(
            text = it,
            color = Color.Red,
            fontSize = 14.sp
//            modifier = Modifier.padding(top = 2.dp)
        )
    }

    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        colors = ButtonDefaults.buttonColors(),
        contentPadding = PaddingValues(16.dp),
        shape = MaterialTheme.shapes.medium,
//        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = iconName, contentDescription = value)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = value)
        }
    }
}


@Composable
fun LandingButtonComponent(
    value: String,
    iconName: ImageVector,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false,
    errorMessage: String? = null
) {
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9DC8B5)),
        contentPadding = PaddingValues(16.dp),
        shape = MaterialTheme.shapes.medium,
        enabled = isEnabled
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = iconName, contentDescription = value)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = value)
        }
    }

    // Display an error message if provided
    errorMessage?.let {
        Text(
            text = it,
            color = Color.Red,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun ButtonWithIconAndMessageComponent(
    value: String,
    iconName: ImageVector,
    points: String,
    message: String,
    onButtonClicked: () -> Unit,
    isEnabled: Boolean = false
) {
    Button(
        onClick = {
            onButtonClicked.invoke()
        },
        modifier = Modifier.fillMaxWidth(0.5f),
        shape = RoundedCornerShape(10.dp),
        enabled = isEnabled
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
            Icon(
                imageVector = iconName,
                contentDescription = "",
                modifier = Modifier.size(50.dp),
                tint = Color(0xFFBB7E7A)
            )
            Text(
                text = points,
                fontSize = 15.sp
            )
            Text(text = "Points")
            Text(
                text = message,
                textAlign = TextAlign.Center
            )
        }

    }
}


@Composable
fun DividerComponent() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 60.dp, 30.dp, 15.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.tertiary
    )
}


@Composable
fun TextButtonWithMessageComponent(message: String, action: () -> Unit, buttonText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            fontSize = 14.sp
        )

        TextButton(
            onClick = action
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp
            )
        }
    }
}


@Composable
fun TextButtonComponent(action: () -> Unit, buttonText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp),
        verticalAlignment = Alignment.CenterVertically
//        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = action
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(toolbarTitle: String, logoutButtonClicked: () -> Unit, navController: NavController) {
    TopAppBar(
        title = {
            Text(text = toolbarTitle)
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(
                onClick = {
                    logoutButtonClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout"
                )
            }
        }
    )
}


@Composable
fun BottomNavigationBar(
    items: List<NavItemState>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp)),
        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = .5f)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    Icon(
                        imageVector = if (selectedIndex == index) item.selectedIcon
                        else item.unselectedIcon,
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color(0xFF131F0D),
                    selectedIconColor = Color(0xFF552A27),
                    indicatorColor = Color(0xFFF1F1EA)
                )
            )
        }
    }
}


@Composable
fun WelcomeBackComponent(firstName: String, points: Int) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            HeadingComponent("Welcome back, $firstName")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                SubheadingComponent(value = "$points")
                Text(text = " Points")
            }
        }
    }
}


// Dashboard
@Composable
fun GeneralGreeting(firstName: String, points: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome back, $firstName",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$points",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = " Points",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    //                Spacer(modifier = Modifier.weight(1f))

                }
            }
        }
    }
}


// Dashboard
@Composable
fun TitleComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
    )
}

// Dashboard
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsLazyRow(rewardsList: List<RewardData>, onItemClick: (RewardData) -> Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        content = {
            items(rewardsList) { reward ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = { onItemClick(reward) }),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(start = 30.dp, 10.dp, 30.dp, 10.dp)
                            .height(180.dp)
                            .width(180.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Discount,
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = reward.rewardName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            softWrap = true,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Required Points: ${reward.requiredPoints}", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Quantity: ${reward.quantity}", fontSize = 14.sp)
                    }
                }
            }
        }
    )
}


// Dashboard
@Composable
fun RewardsTextButtonComponent(action: () -> Unit, buttonText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = action
        ) {
            Text(
                text = buttonText,
                fontSize = 14.sp
            )
        }
    }
}


// Rewards
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsLazyColumn(rewardsList: List<RewardData>, onItemClick: (RewardData) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 10.dp),
        content = {
            items(rewardsList) { reward ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable(onClick = { onItemClick(reward) }),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .height(180.dp)
                            .fillMaxWidth(), // Adjusted to fill the width
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Discount,
                            contentDescription = "",
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = reward.rewardName,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            softWrap = true,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Required Points: ${reward.requiredPoints}", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Quantity: ${reward.quantity}", fontSize = 14.sp)
                    }
                }
            }
        }
    )
}


@Composable
fun UserInfoTextComponent(greeting: String, message: String) {
    Column {
        Text(
            text = greeting,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 2.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            color = MaterialTheme.colorScheme.tertiary
        )

        Text(
            text = message,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 2.dp),
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal,
            color = MaterialTheme.colorScheme.tertiary
        )
    }

}


// Account
@Composable
fun AccountGreeting(firstName: String, points: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.profile),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Welcome back, $firstName",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$points",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = " Points",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = MaterialTheme.colorScheme.tertiary
                )
//                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


// Account
@Composable
fun ProfileInfoItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}


// myRewards
@Composable
fun myRewardsItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

// MyRewards
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myRewardsLazyColumn(
    myRewardsList: List<MyRewardData>,
    uid: String,
    navController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp, 10.dp),
        content = {
            items(myRewardsList) { myreward ->
                Card(
                    modifier = Modifier
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Box(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .height(300.dp)
                            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(), // Adjusted to fill the width
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Discount,
                                contentDescription = "",
                                modifier = Modifier.size(48.dp)
                            )
                            Text(
                                text = myreward.rewardName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                softWrap = true,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Expiry Date: ${myreward.expiryDate}", fontSize = 16.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Status: ${myreward.rewardStatus}", fontSize = 14.sp)

                            ButtonComponent(
                                value = "Use Reward",
                                iconName = Icons.Filled.Celebration,
                                onButtonClicked = {
                                    updateMyRewardStatus(myreward.myRewardId)
                                    navController.navigate("myRewards/${uid}")
                                },
                                isEnabled = myreward.rewardStatus == "Active",
                                errorMessage = ""
                            )
                        }
                    }
                }
            }
        }
    )
}


// Redeem
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardInfoCard(
    uid: String,
    accumulatedPoints: Int,
    rewardKey: String,
    rewardId: String,
    rewardName: String,
    requiredPoints: Int,
    rewardQuantity: Int,
    navController: NavHostController,
    onUpdatePoints: (Int) -> Unit
) {
    var redeemQuantity by remember { mutableStateOf(0) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Discount,
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = try {
                    rewardName
                } catch (e: Exception) {
                    // Log the exception
                    e.printStackTrace()
                    "Reward Name: N/A"
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Required Points: $requiredPoints", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "$rewardQuantity left", fontSize = 14.sp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (redeemQuantity > 0) redeemQuantity-- },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease Quantity"
                    )
                }

                // Input Box
                OutlinedTextField(
                    value = redeemQuantity.coerceAtMost(5).toString(),
                    onValueChange = {
                        if (it.isDigitsOnly()) {
                            redeemQuantity = it.toInt().coerceIn(0, minOf(5, rewardQuantity))
                        }
                    },
                    label = { Text("Redeem Quantity") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .width(150.dp),
                    textStyle = TextStyle.Default.copy(fontSize = 16.sp)
                )

                IconButton(
                    onClick = { redeemQuantity++ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Increase Quantity")
                }
            }

            ButtonComponent(
                value = "Redeem",
                iconName = Icons.Default.AddCircle,
                onButtonClicked = {
                    // Check if the user has enough points
                    val requiredPoints = redeemQuantity * requiredPoints
                    if (requiredPoints > accumulatedPoints) {
                        errorMessage = "Not enough points to redeem"
                    } else {
                        // Deduct points and update user data in Firebase
                        val newPoints = accumulatedPoints - requiredPoints
                        val newQuantity = rewardQuantity - redeemQuantity
                        updateAccumulatedPoints(uid, newPoints, onUpdatePoints = {})
                        updateRewardQuantity(rewardKey, newQuantity, onUpdatePoints = {})
                        updateTransactionInFirebase(
                            uid,
                            rewardId,
                            rewardName,
                            requiredPoints,
                            redeemQuantity
                        )
                        repeat(redeemQuantity) {
                            updateMyRewardInFirebase(uid, rewardId, rewardName)
                        }
                        navController.navigate("RedemptionSuccess/${requiredPoints}/${newPoints}/${uid}")
                    }
                },
                isEnabled = redeemQuantity > 0,
                errorMessage = errorMessage
            )
        }
    }
}

@Composable
fun TransactionInfo(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$label:",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(120.dp)
        )
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
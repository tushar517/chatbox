package com.example.chatbox.ui.screens

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.chatbox.R
import com.example.chatbox.data.model.ApiResult
import com.example.chatbox.data.model.SignUpRequest
import com.example.chatbox.navigation.AuthRoute
import com.example.chatbox.navigation.Home
import com.example.chatbox.ui.components.CustomLoader
import com.example.chatbox.ui.components.CustomTextField
import com.example.chatbox.ui.components.LoginSignUpButton
import com.example.chatbox.ui.theme.White
import com.example.chatbox.ui.theme.grey_7B
import com.example.chatbox.ui.theme.typography
import com.example.chatbox.ui.viewmodel.signup.SignUpEvent
import com.example.chatbox.ui.viewmodel.signup.SignUpState


@Composable
fun SignUpScreen(
    navHostController: NavHostController,
    onEvent: (SignUpEvent) -> Unit,
    state: SignUpState
) {
    val context = LocalContext.current
    var userName by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPassword by remember {
        mutableStateOf(true)
    }
    var fullName by remember {
        mutableStateOf("")
    }
    var gender by remember { mutableStateOf("") }

    val isButtonEnable = remember {
        derivedStateOf {
            userName.isNotBlank() && password.isNotBlank() && fullName.isNotBlank() && gender.isNotBlank()
        }
    }
    var profileBitMap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    var profileImg by remember { mutableStateOf("") }
    val genderList = listOf("Male", "Female")
    var isDropDownExpanded by remember { mutableStateOf(false) }

    var isAnimate by remember { mutableStateOf(false) }

    LaunchedEffect(state.signUpApiResult) {
        when (val response = state.signUpApiResult) {
            is ApiResult.Success -> {
                Toast.makeText(context, response.data.description, Toast.LENGTH_LONG).show()
                navHostController.navigate(Home){
                    popUpTo<AuthRoute> {
                        inclusive = true
                    }
                }
            }

            is ApiResult.Empty -> {
            }

            is ApiResult.Error -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                .show()
        }
        onEvent(SignUpEvent.EmptyApiCall)
    }
    val contentResolver = LocalContext.current.contentResolver
    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imgUri ->
            var bitmap: android.graphics.Bitmap? = null
            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imgUri)
            } else {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(contentResolver, imgUri)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            profileBitMap = bitmap
            bitmap?.let { onEvent(SignUpEvent.UploadImageApi(bitmap)) }
        }
    }
    LaunchedEffect(state.imageUploadResponse) {
        profileImg = state.imageUploadResponse

        onEvent(SignUpEvent.EmptyApiCall)
    }

    LaunchedEffect(Unit) {
        isAnimate = true
    }
    Scaffold(containerColor = White) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            AnimatedVisibility(
                isAnimate,
                enter = slideInVertically(
                    animationSpec = tween(
                        1500
                    )
                ),
                modifier = Modifier.weight(.05f)
            ) {

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_login_1),
                    contentDescription = "Landing page image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth()
                )

            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .weight(.85f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Create An Account",
                    style = typography.bodyLarge.copy(
                        color = Color.Black
                    )
                )
                Box(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .size(80.dp),
                ) {
                    if (profileImg.isNotBlank()) {
                        profileBitMap?.let { bitMap ->
                            Image(
                                painter = BitmapPainter(bitMap.asImageBitmap()), contentDescription = "Profile",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape) ,
                                contentScale = ContentScale.Crop,


                                )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(80.dp)
                                .border(
                                    shape = CircleShape,
                                    width = 1.dp,
                                    color = Color.Black
                                )
                                .pointerInput(
                                    Unit
                                ) {
                                    detectTapGestures {
                                        launcher.launch("image/*")
                                    }
                                }
                        ) {

                            Image(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_profile),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(60.dp)
                            )
                        }
                    }
                    Box(
                        Modifier
                            .align(Alignment.BottomEnd)
                            .size(25.dp)
                            .background(grey_7B, shape = CircleShape)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "",
                            tint = White,
                            modifier = Modifier.size(17.dp)
                        )
                    }
                }
                CustomTextField(
                    label = "Username",
                    value = userName,
                    keyBoardType = KeyboardType.Text,
                    onValueChange = { value ->
                        userName = value
                    },
                    modifier = Modifier.padding(top = 20.dp),
                    leadingIcon = R.drawable.ic_profile
                )
                CustomTextField(
                    label = "Full Name",
                    value = fullName,
                    keyBoardType = KeyboardType.Text,
                    onValueChange = { value ->
                        fullName = value
                    },
                    modifier = Modifier.padding(top = 20.dp),
                    leadingIcon = R.drawable.ic_profile
                )
                CustomTextField(
                    value = password,
                    onValueChange = { value ->
                        password = value
                    },
                    label = "Password",
                    keyBoardType = KeyboardType.Password,
                    modifier = Modifier.padding(top = 20.dp),
                    visualTransformation = if (isPassword) PasswordVisualTransformation('*') else {
                        VisualTransformation.None
                    },
                    icon = {
                        IconButton(
                            onClick = { isPassword = !isPassword },
                            modifier = Modifier.padding(0.dp)
                        ) {
                            if (isPassword) {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_password_off),
                                    contentDescription = "",
                                )
                            } else {
                                Image(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_password_on),
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    leadingIcon = R.drawable.ic_password
                )
                CustomTextField(
                    label = "Gender",
                    value = gender,
                    keyBoardType = KeyboardType.Text,
                    onValueChange = {},
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .pointerInput(Unit) {
                            detectTapGestures {
                                isDropDownExpanded = true
                            }
                        },
                    leadingIcon = R.drawable.ic_profile,
                    isEnable = false,
                    icon = {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "")
                    }
                )
                DropdownMenu(
                    expanded = isDropDownExpanded,
                    onDismissRequest = {
                        isDropDownExpanded = false
                    },
                    containerColor = grey_7B,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    genderList.forEachIndexed { index, selectedGender ->
                        DropdownMenuItem(
                            text = {
                                Text(selectedGender)
                            },
                            onClick = {
                                isDropDownExpanded = false
                                gender = selectedGender
                            },
                            colors = MenuDefaults.itemColors(textColor = White)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .weight(.1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    isAnimate,
                    enter = slideInHorizontally(
                        animationSpec = tween(
                            1500
                        )
                    ),
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_login_2),
                        contentDescription = ""
                    )
                }
                LoginSignUpButton(
                    modifier = Modifier.padding(end = 30.dp),
                    btnText = "Create", isEnable = isButtonEnable.value
                ) {
                    onEvent(
                        SignUpEvent.SignUpApiCAll(
                            SignUpRequest(
                                fullName = fullName,
                                userName = userName,
                                password = password,
                                gender = gender,
                                profileImg = profileImg
                            )
                        )
                    )
                }
            }


        }
    }
    if (state.isLoading) {
        CustomLoader()
    }
}

@Preview
@Composable
fun SignUpPreview(modifier: Modifier = Modifier) {
    SignUpScreen(
        rememberNavController(),
        onEvent = {},
        state = SignUpState()
    )
}
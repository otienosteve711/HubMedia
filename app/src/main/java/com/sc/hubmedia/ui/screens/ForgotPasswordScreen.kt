package com.sc.hubmedia.ui.screens

import androidx.collection.emptyLongSet
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.sc.hubmedia.navigation.Screen
import com.sc.hubmedia.ui.theme.MediaHubTheme

@Composable
fun ForgotPasswordScreen(navController: NavController){
    // data state def
    var email by remember {mutableStateOf("")}
    var sent by remember {mutableStateOf(false)}
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center){
        Column(modifier=Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = {navController.popBackStack()},modifier=Modifier.align(Alignment.Start)) { }

        }
        Spacer(Modifier.height(24.dp))
        Text("Enter your email and we will send you a reset link",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
        Spacer(Modifier.height(32.dp))
        if (sent){
            Text("Reset link has already been sent. Kindly check your inbox!!",
                color = MaterialTheme.colorScheme.onPrimaryContainer)

        }else(
                OutlinedTextField(
                    Value = email,
                    onValueChange={email= it},
                    label ={Text("Fullname")},
                    leadingIcon={
                        Icon(Icons.Default.Person,null)},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape =RoundedCornerShape(12.dp),
        ))
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = {sent=true},
            modifier=Modifier.fillMaxWidth().height
        ) { }

    }

}
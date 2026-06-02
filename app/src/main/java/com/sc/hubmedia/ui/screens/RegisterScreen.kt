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
fun RegisterScreen(navController: NavController){
    // data for the register screen i.e. state
    var fullname by remember{mutableStateOf("")}
    var email by remember {mutableStateOf("")}
    var password by remember {mutableStateOf("")}
    var confirmPassword by remember{mutableStateOf("")}
    var passwordVisible by remember {mutableStateOf(false)}
    var selectedRole by remember {mutableStateOf("Student")}
    var roles = listOf("Student", "Teacher")
    Box(modifier = Modifier.fillMaxSize().background(
        MaterialTheme.colorScheme.background
    )
        ){
        Column(modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal=24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(Icons.Default.VideoLibrary,
                contentDescription = null,
                tint=MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp))
            Text(text ="Create Account",
                style = MaterialTheme.typography.headlineMedium,
                color=MaterialTheme.colorScheme.background)
            Spacer(Modifier.height(8.dp))
            Text(text="Join MediaHub Today",
                style=MaterialTheme.typography.bodyLarge,
                color=MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.5f
                ))
            Spacer(Modifier.height(32.dp))
            // form inputs for registration
            OutlinedTextField(
                Value = fullname,
                onValueChange={fullname= it},
                label ={Text("Fullname")},
                leadingIcon={
                    Icon(Icons.Default.Person,null)},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape =RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType=KeyboardType.Email
                )



            )
            Spacer(modifier=Modifier.height(12.dp))
            OutlinedTextField(
                Value = email,
                onValueChange={email= it},
                label ={Text("Fullname")},
                leadingIcon={
                    Icon(Icons.Default.Person,null)},
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape =RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType=KeyboardType.Email
                )
            )
            Spacer(modifier=Modifier.height(12.dp))
            //  confirm password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {confirmPassword = it},
                leadingIcon = {
                    Icon(Icons.Default.Lock,null)
                },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer (Modifier.height(20.dp))
            // role selector : dropdown
            Text("select Role",
                style= MaterialTheme.typography.labelSmall,
                modifier = Modifier.align(Alignment.Start),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Spacer(Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // present options to the user
                roles.forEach { role ->
                    // capture what role user selects
                    val selected = selectedRole == role
                    // composable to present the options
                    FilterChip(
                        selected = selected,
                        onClick = {selectedRole = role},
                        modifier = Modifier.weight(1f),
                        leadingIcon = {
                            Icon(if role == "Teacher") Icons.Default.School else Icons.Default.Person, null
                            modifier = Modifier.size()
                        }
                    )

                }
            }
            Spacer(Modifier.height(28.dp))
            // register button
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp)

            ){
                Text(text = "Create Account",
                    style = MaterialTheme.typography.bodyLarge)

            }
            Spacer(Modifier.height(16.dp))
            // To link to the register string
            TextButton(
                onClick = {
                    navController.navigate(
                        Screen.Login.route
                    ){
                        popUpTo(Screen.Register.route){inclusive = true}
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Already have an account? Sign In",
                    color= MaterialTheme.colorScheme.primary)
            }



        }
    }

}
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview(){
    MediaHubTheme {
        RegisterScreen(rememberNavController())
    }
}
package com.example.order.components

import OrderViewModel
import android.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chatapp.LoginDataStore


@Composable
fun Popup(showpopup: MutableState<Boolean>, foodId: String, quantity: Int) {

    val context = LocalContext.current
    val name = LoginDataStore.getName(context).collectAsState(initial = "User Name").value
    val userId = LoginDataStore.getId(context).collectAsState(initial = "").value

    val orderViewModel: OrderViewModel = viewModel()
    val loading = orderViewModel.loading.value
    val success = orderViewModel.success.value
    val message = orderViewModel.popupMessage.value

    val closePopup = {
        orderViewModel.resetPopup()
        showpopup.value = false
    }



    if (showpopup.value) {
        Dialog(onDismissRequest = { closePopup() }) {

            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.85f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFF6F00))
            ) {
                if (success) {

                    Column (
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            text =message.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Button(onClick = { closePopup() }, modifier = Modifier.fillMaxWidth(),) {
                            Text("Back")
                        }
                    }
                }else{
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Confirm Order",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Quantity: $quantity",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { orderViewModel.submitOrder(foodId, quantity,userId,name) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF8F00)
                        )
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                    }else{
                            Text("Place Order", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = { showpopup.value = false }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                        Text("Cancel", color = Color.Gray)
                    }
                }
            }
        }
    }
}
}

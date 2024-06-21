package com.gog.task.screen

import Specification
import SpecificationItem
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.gog.task.R
import getJsonStringFromAssets

@SuppressLint("UnrememberedMutableState")
@Composable
fun MainScreen(viewModel: MyViewModel) {
    val data = viewModel.specifications.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        val str = getJsonStringFromAssets(context)
        viewModel.loadSpecifications(str)
    }
    val filterData =
        data.value?.specifications?.get(0)?.list?.filter { doc -> doc.is_default_selected === true }

    var selectedOption = remember {
        mutableStateOf("")
    }
    LaunchedEffect(key1 = filterData) {
        selectedOption.value = filterData?.get(0)?.name?.get(0).toString()
    }
    val checkData = remember {
        mutableStateOf(mapOf<String, Any>())
    }

    fun setData(){

    }

    Box(Modifier.zIndex(1f)) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "",
                tint = Color.Black,
                modifier = Modifier.size(30.dp)
            )
        }
    }
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding()
                .padding()
                .padding(bottom = 20.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Inside
            )
            Text(
                text = data.value?.name?.get(0) ?: "No Name",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 14.dp)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            Modifier
                .fillMaxWidth()

                .background(Color.White)
        ) {
            Text(
                text = data.value?.specifications?.get(0)?.name?.get(0) ?: "",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Choose 1",
                fontSize = 18.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            data.value?.specifications?.get(0)?.list?.forEachIndexed { index, option ->
                PriceRadio(selectedOption.value.toString(), option, onClick = { newValue ->
                    Log.d("TAG", "MainScreen2: ${newValue}")
                    selectedOption.value = newValue // Update the state variable with the new value
                })
            }


        }
        val filterBedRoomData: List<Specification>? =
            data?.value?.specifications?.subList(1, data?.value?.specifications!!.size)
                ?.filterIndexed { index, specification -> specification.modifierName == selectedOption.value }
        filterBedRoomData?.forEachIndexed { index, specification ->
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = specification.name.get(0),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Choose 1",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Light,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (filterBedRoomData!!.isNotEmpty()) {
                    specification?.list?.forEach { option ->
                        CheckboxWithSideName(option)

                    }
                }
            }


        }


    }
}

@Composable
fun PriceRadio(
    selectedOption:String,
    data:SpecificationItem,
    onClick: (String) -> Unit
){
//    val name = option?.name?.get(0)
    Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
        .fillMaxWidth()
        .padding(end = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (verticalAlignment = Alignment.CenterVertically){
            RadioButton(

                selected = selectedOption==data.name.get(0),
                onClick = {onClick(data.name.get(0)) }
            )
            Text(text = data.name.get(0))
        }
        Text(text ="${data.price}")
    }

}

@Composable
fun CheckboxWithSideName(specification: SpecificationItem) {

    val checkedState = remember { mutableStateOf(false) }


    Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Row(verticalAlignment = Alignment.CenterVertically){

            Checkbox(checked = checkedState.value, onCheckedChange = { checkedState.value = it })
            // Add spacing between checkbox and text
            Text(text = specification.name.get(0), style = TextStyle(lineBreak = LineBreak.Simple))
        }
        Text(text ="${specification.price}", fontSize = 14.sp)

    }
}
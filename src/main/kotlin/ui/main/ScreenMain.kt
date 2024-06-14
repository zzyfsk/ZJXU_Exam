package ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.onClick
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.exam.ScreenExam

class ScreenMain :Screen{

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = rememberScreenModel {
            ScreenModelMain()
        }

        screenModel.initFile()
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Button(modifier = Modifier.padding(top =  5.dp),onClick = {
                    navigator?.push(ScreenExam(screenModel.fileList[screenModel.currentSelect]))
                }){
                    Text("ToExam")
                }
                Button(modifier = Modifier.padding(top = 5.dp),onClick = {
                    screenModel.addFileList()
                }){
                    Text("读取文件")
                }

            }
            LazyColumn(modifier = Modifier
                .weight(1.5f)) {
                items(screenModel.fileList.size){
                    Row {
                        Text(modifier = Modifier.weight(2f),text = screenModel.fileList[it])
                        Text(modifier = Modifier
                            .width(15.dp)
                            .weight(0.3f)
                            .onClick {
                                screenModel.currentSelect = it
                            }
                            , text = "选择", color = if (screenModel.currentSelect == it) Color.Red else Color.Black)
                        Text(modifier = Modifier
                            .width(15.dp)
                            .weight(0.3f)
                            .onClick {
                                screenModel.removeFileList(it)
                            }
                            , text = "删除", color = if (screenModel.currentSelect == it) Color.Red else Color.Black)
                    }
                }
            }
        }
    }
}
package ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import util.SaveUtil

class ScreenModelMain:ScreenModel {
    val fileList = mutableStateListOf<String>()

    var currentSelect by mutableStateOf(0)

    fun initFile(){
        SaveUtil.saveUtil.checkFile()
        getFileList()
    }

    private fun getFileList(){
        fileList.clear()
        fileList.addAll(SaveUtil.saveUtil.readFile())
    }

    fun addFileList(){
        SaveUtil.saveUtil.addFile(
            SaveUtil.saveUtil.getPath()
        )
        getFileList()
    }

    fun removeFileList(index:Int){
        SaveUtil.saveUtil.removeFile(index)
        getFileList()
    }
}
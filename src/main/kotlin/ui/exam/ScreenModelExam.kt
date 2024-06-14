package ui.exam

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import dao.AnalysisWord
import util.WordUtil
import java.io.File

class ScreenModelExam : ScreenModel {
    val questionList = mutableStateListOf<Any>()

    private val currenSelectList = mutableStateListOf<Int>()

    var errorInfo by mutableStateOf("")

    fun readWord(route: String) {
        try {
            questionList.addAll(AnalysisWord.analysisWord.analysisDoc2(WordUtil.wordUtil.readWord(route)))
        } catch (e: Exception) {
            val relativePathFile = "error.txt"
            val currentPath = File("").absolutePath
            errorInfo = e.localizedMessage
            val newFilePath = "$currentPath/$relativePathFile"
            val newFile = File(newFilePath)

            if(!newFile.exists()){
                newFile.createNewFile()
            }
            val lines = newFile.readLines().toMutableList()
            lines.add(errorInfo)
            newFile.writeText(lines.joinToString("\n"))

//            SaveUtil.saveUtil.addFile(e.localizedMessage)
//            questionList.addAll(AnalysisWord.analysisWord.analysisDoc(WordUtil.wordUtil.readWord(route)))
        }
        for (i in 0 until questionList.size) {
            currenSelectList.add(-1)
        }
    }
}
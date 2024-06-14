package ui.exam

import androidx.compose.runtime.*
import cafe.adriel.voyager.core.model.ScreenModel
import dao.AnalysisWord
import util.SaveUtil
import util.WordUtil

class ScreenModelExam : ScreenModel {
    val questionList = mutableStateListOf<Any>()

    val currenSelectList = mutableStateListOf<Int>()

    var errorInfo by mutableStateOf("")

    fun readWord(route: String) {
        try {
            questionList.addAll(AnalysisWord.analysisWord.analysisDoc2(WordUtil.wordUtil.readWord(route)))
        } catch (e: Exception) {
            errorInfo = e.localizedMessage
//            SaveUtil.saveUtil.addFile(e.localizedMessage)
//            questionList.addAll(AnalysisWord.analysisWord.analysisDoc(WordUtil.wordUtil.readWord(route)))
        }
        for (i in 0 until questionList.size) {
            currenSelectList.add(-1)
        }
    }
}
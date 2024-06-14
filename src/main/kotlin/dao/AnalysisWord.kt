package dao

import bean.ChooseQuestion
import bean.JudgeQuestion
import bean.MultiQuestion
import util.WordUtil

class AnalysisWord {
    private var lines = mutableListOf<String>()
    private val chooseQuestion = mutableListOf<ChooseQuestion>()
    private val judgeQuestion = mutableListOf<JudgeQuestion>()
    private val multiQuestion = mutableListOf<MultiQuestion>()

    private var question = ""
    private var a = ""
    private var b = ""
    private var c = ""
    private var d = ""
    private var answer = ""

    val getChooseAnswer: (String) -> Int = {
        var index = it.indexOf("A")
        if (index == -1) index = it.indexOf("B")
        if (index == -1) index = it.indexOf("C")
        if (index == -1) index = it.indexOf("D")
        index
    }

    fun analysisDoc2(string: String): MutableList<Any> {
        chooseQuestion.clear()
        judgeQuestion.clear()
        multiQuestion.clear()

        lines.clear()
        lines.addAll(string.split("\n"))

        var choose = 0
        var multi = 0
        var judge = 0
        var last = 0
        lines.forEachIndexed { index, s ->
            if (s.contains("单项选择题")) choose = index
            if (s.contains("多项选择题")) multi = index
            if (s.contains("判断题")) judge = index
            if (judge != 0 && (s.isBlank() || s == "\r") && last == 0) last = index
        }
        println("$choose, $multi, $judge, $last")

        chooseQuestionAnalysis(choose+1,multi)

        chooseQuestion.forEach {
            println(it.toString())
        }
        multiQuestionAnalysis(multi+1,judge)
        judgeQuestion(judge+1,last)


        multiQuestion.forEach {
            println(it.toString())
        }

        judgeQuestion.forEach {
            println(it.toString())
        }

        val list = mutableListOf<Any>()
        list.addAll(chooseQuestion)
        list.addAll(multiQuestion)
        list.addAll(judgeQuestion)
        return list
    }

    private fun chooseQuestionAnalysis(start: Int,end: Int){
        var temp = 0
        var bool = false
        val regex = Regex("[ABCD]")
        for (i in start until end) {
            println(lines[i])
            println("$i ${i / 5} $temp")
            if (lines[i].isBlank())continue
            when (temp) {
                0 -> {
                    bool = regex.containsMatchIn(lines[i])
                    if (!bool) {
                        question = lines[i].substring(0, lines[i].length - 1)
                        println(question)
                    } else {
                        question = lines[i].substring(0, lines[i].length - 9)
                        answer = lines[i].substring(getChooseAnswer(lines[i]), getChooseAnswer(lines[i])+1)
                    }
                    temp++
                }

                1 -> {
                    if (!bool) answer = lines[i].substring(getChooseAnswer(lines[i]), getChooseAnswer(lines[i])+1)
                    else {
                        a = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                }

                2 -> {
                    if (!bool) a = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    else {
                        b = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                }

                3 -> {
                    if (!bool) b = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    else {
                        c = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                }

                4 -> {
                    if (!bool) {
                        c = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                        temp++
                    }
                    else {
                        d = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                        chooseQuestion.add(ChooseQuestion(question, a, b, c, d, answer))
                        temp = 0
                    }

                }

                5 -> {
                    if (!bool) d = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    chooseQuestion.add(ChooseQuestion(question, a, b, c, d, answer))
                    temp = 0

                }

            }
        }
    }

    private fun multiQuestionAnalysis(start: Int, end: Int){
        var temp = 0
        var bool = false
        val regex = Regex("[ABCD]")
        var answerIndex: Int
        for (i in start until end) {
            if (lines[i].isBlank())continue
            when (temp) {
                0 -> {
                    println("multi:" + lines[i])
                    bool = regex.containsMatchIn(lines[i])
                    if (!bool) {
                        question = lines[i].substring(0, lines[i].length - 1)
//                        println(question)
                    } else {
                        answerIndex = getChooseAnswer(lines[i])
                        println(lines[i])
                        question = lines[i].substring(0, answerIndex - 8)
                        answer = lines[i].substring(answerIndex, lines[i].length - 1)
                    }
                    temp++
                }

                1 -> {
                    answerIndex = getChooseAnswer(lines[i])
                    if (!bool) answer = lines[i].substring(answerIndex, lines[i].length - 1)
                    else {
                        a = lines[i].substring(0,if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                }

                2 -> {
                    if (!bool) a = lines[i].substring(0,if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    else {
                        b = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                }

                3 -> {
                    if (!bool) b = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    else {
                        c = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                }

                4 -> {
                    if (!bool) c = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    else {
                        d = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    }
                    temp++
                    if (!bool) d = lines[i].substring(0, if (lines[i].last()== '\r') lines[i].length - 1 else lines[i].length)
                    else {
                        multiQuestion.add(MultiQuestion(question, a, b, c, d, answer))
                        temp = 0
                    }
                }

                5 -> {
                    multiQuestion.add(MultiQuestion(question, a, b, c, d, answer))
                    temp = 0
                }
            }
        }
    }

    private fun judgeQuestion(start:Int, end:Int){
        for (i in start until end) {
            if (lines[i].isBlank())continue
            println(lines[i])
            question = lines[i].substring(0, lines[i].length - 10)
            var index = lines[i].indexOf("错")
            if (index == -1) index = lines[i].indexOf("对")
            answer = lines[i].substring(index, index+1)
            judgeQuestion.add(JudgeQuestion(question, if (answer == "对") 1 else 0))
        }
    }


    companion object {
        val analysisWord = AnalysisWord()
    }
}

fun main() {
    WordUtil.wordUtil.readWord("C:\\Users\\zzy\\Downloads\\《习近平新时代中国特色社会主义思想概论》复习题\\第1章复习题.doc")
}
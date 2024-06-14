package ui.exam

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.onClick
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bean.ChooseQuestion
import bean.JudgeQuestion
import bean.MultiQuestion
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.*
import ui.theme.LightColor

data class ScreenExam(val fileRoute: String) : Screen {

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val screenModel = rememberScreenModel {
            ScreenModelExam()
        }
        Column {
            Row {
                Text(modifier = Modifier.width(50.dp).onClick {
                    screenModel.readWord(fileRoute)
                }, text = "读取")
                Text(modifier = Modifier.width(50.dp).onClick {
                    navigator?.pop()
                }, text = "返回")
            }
            if (screenModel.errorInfo.isNotBlank()) {
                Text(screenModel.errorInfo)
            }
            ExamContent(modifier = Modifier.fillMaxSize())
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ExamContent(modifier: Modifier) {
        val screenModel = rememberScreenModel {
            ScreenModelExam()
        }


        val pageState = rememberPagerState(pageCount = { screenModel.questionList.size })
        HorizontalPager(
            state = pageState,
            modifier = modifier
                .fillMaxSize()
                .background(LightColor.Background)
        ) { page ->
            Column(modifier = Modifier.fillMaxSize()) {
                println(screenModel.questionList[page]::class.simpleName)
                when (screenModel.questionList[page]::class.simpleName) {
                    "ChooseQuestion" -> {
                        val getAnswerChoose: (String) -> Int = {
                            when (it) {
                                "A" -> 1
                                "B" -> 2
                                "C" -> 3
                                "D" -> 4
                                else -> 0
                            }
                        }
                        var chooseSelect by remember { mutableStateOf(0) }
                        Text(
                            text = (screenModel.questionList[page] as ChooseQuestion).question,
                            modifier = modifier.height(50.dp).weight(1f)
                        )
                        val onClickChoose: (Int) -> Unit = {
                            chooseSelect = it
                        }
                        val chooseColor: (Int) -> Color = {
                            if (chooseSelect != 0 && (getAnswerChoose((screenModel.questionList[page] as ChooseQuestion).answer) == it)) LightColor.AnswerWordCorrect
                            else if (getAnswerChoose((screenModel.questionList[page] as ChooseQuestion).answer) != it && chooseSelect == it) LightColor.AnswerWordError
                            else LightColor.OptionWordUnselect
                        }
                        val chooseBackGroundColor: (Int) -> Color = {
                            if (chooseSelect != 0 && (getAnswerChoose((screenModel.questionList[page] as ChooseQuestion).answer) == it)) LightColor.OptionBackgroundCorrect
                            else if (getAnswerChoose((screenModel.questionList[page] as ChooseQuestion).answer) != it && chooseSelect == it) LightColor.OptionBackgroundError
                            else LightColor.Background
                        }
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 1,
                            content = (screenModel.questionList[page] as ChooseQuestion).a,
                            color = chooseColor(1),
                            backgroundColor = chooseBackGroundColor(1),
                            onClick = onClickChoose
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 2,
                            content = (screenModel.questionList[page] as ChooseQuestion).b,
                            color = chooseColor(2),
                            backgroundColor = chooseBackGroundColor(2),
                            onClick = onClickChoose
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 3,
                            content = (screenModel.questionList[page] as ChooseQuestion).c,
                            color = chooseColor(3),
                            backgroundColor = chooseBackGroundColor(3),
                            onClick = onClickChoose
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 4,
                            content = (screenModel.questionList[page] as ChooseQuestion).d,
                            color = chooseColor(4),
                            backgroundColor = chooseBackGroundColor(4),
                            onClick = onClickChoose
                        )
                    }

                    "MultiQuestion" -> {
                        val getMultiCorrectAnswer: (Int) -> Boolean = {
                            when (it) {
                                1 -> (screenModel.questionList[page] as MultiQuestion).answer.contains("A")
                                2 -> (screenModel.questionList[page] as MultiQuestion).answer.contains("B")
                                3 -> (screenModel.questionList[page] as MultiQuestion).answer.contains("C")
                                4 -> (screenModel.questionList[page] as MultiQuestion).answer.contains("D")
                                else -> {
                                    false
                                }
                            }
                        }
                        var selectA by remember { mutableStateOf(0) }
                        var selectB by remember { mutableStateOf(0) }
                        var selectC by remember { mutableStateOf(0) }
                        var selectD by remember { mutableStateOf(0) }

                        var resultA by remember { mutableStateOf(0) }
                        var resultB by remember { mutableStateOf(0) }
                        var resultC by remember { mutableStateOf(0) }
                        var resultD by remember { mutableStateOf(0) }

                        val multiColor: (Int) -> Color = {
                            when (it) {
                                1 -> {
                                    if (resultA == 0) LightColor.OptionWordUnselect
                                    else if (getMultiCorrectAnswer(it)) LightColor.AnswerWordCorrect
                                    else LightColor.OptionWordError
                                }

                                2 -> {
                                    if (resultB == 0) LightColor.OptionWordUnselect
                                    else if (getMultiCorrectAnswer(it)) LightColor.AnswerWordCorrect
                                    else LightColor.OptionWordError
                                }

                                3 -> {
                                    if (resultC == 0) LightColor.OptionWordUnselect
                                    else if (getMultiCorrectAnswer(it)) LightColor.AnswerWordCorrect
                                    else LightColor.OptionWordError
                                }

                                4 -> {
                                    if (resultD == 0) LightColor.OptionWordUnselect
                                    else if (getMultiCorrectAnswer(it)) LightColor.AnswerWordCorrect
                                    else LightColor.OptionWordError
                                }

                                else -> Color.Black
                            }
                        }
                        val multiBackgroundColor: (Int) -> Color = {
                            when (it) {
                                1 -> {
                                    if (resultA == 0) LightColor.Background
                                    else if (getMultiCorrectAnswer(it)) LightColor.OptionBackgroundCorrect
                                    else LightColor.OptionBackgroundError
                                }

                                2 -> {
                                    if (resultB == 0) LightColor.Background
                                    else if (getMultiCorrectAnswer(it)) LightColor.OptionBackgroundCorrect
                                    else LightColor.OptionBackgroundError
                                }

                                3 -> {
                                    if (resultC == 0) LightColor.Background
                                    else if (getMultiCorrectAnswer(it)) LightColor.OptionBackgroundCorrect
                                    else LightColor.OptionBackgroundError
                                }

                                4 -> {
                                    if (resultD == 0) LightColor.Background
                                    else if (getMultiCorrectAnswer(it)) LightColor.OptionBackgroundCorrect
                                    else LightColor.OptionBackgroundError
                                }

                                else -> Color.Black
                            }
                        }

                        val onClickButton: (Int) -> Unit = { optionName ->
                            when (optionName) {
                                1 -> selectA = 1 - selectA
                                2 -> selectB = 1 - selectB
                                3 -> selectC = 1 - selectC
                                4 -> selectD = 1 - selectD
                            }
                        }
                        val commit: () -> Unit = {
                            resultA = if (selectA == 1) 1 else -1
                            resultB = if (selectB == 1) 2 else -1
                            resultC = if (selectC == 1) 3 else -1
                            resultD = if (selectD == 1) 4 else -1
                        }

                        Text(
                            text = (screenModel.questionList[page] as MultiQuestion).question,
                            modifier = modifier.height(50.dp).weight(1f)
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 1,
                            content = "${if (selectA == 1) "√" else ""}${(screenModel.questionList[page] as MultiQuestion).a}",
                            color = multiColor(1),
                            backgroundColor = multiBackgroundColor(1),
                            onClick = onClickButton
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 2,
                            content = "${if (selectB == 1) "√" else ""}${(screenModel.questionList[page] as MultiQuestion).b}",
                            color = multiColor(2),
                            backgroundColor = multiBackgroundColor(2),
                            onClick = onClickButton,
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 3,
                            content = "${if (selectC == 1) "√" else ""}${(screenModel.questionList[page] as MultiQuestion).c}",
                            color = multiColor(3),
                            backgroundColor = multiBackgroundColor(3),
                            onClick = onClickButton
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 4,
                            content = "${if (selectD == 1) "√" else ""}${(screenModel.questionList[page] as MultiQuestion).d}",
                            color = multiColor(4),
                            backgroundColor = multiBackgroundColor(4),
                            onClick = onClickButton
                        )
                        Button(onClick = {
                            commit()
                        }) {
                            Text("确定")
                        }
                    }

                    "JudgeQuestion" -> {
                        val getAnswerjudge: (String) -> Int = {
                            when (it) {
                                "对" -> 1
                                "错" -> 2
                                else -> 0
                            }
                        }
                        var chooseSelect by remember { mutableStateOf(0) }
                        Text(
                            text = (screenModel.questionList[page] as JudgeQuestion).question,
                            modifier = modifier.height(50.dp).weight(1f)
                        )
                        val onClickChoose: (Int) -> Unit = {
                            chooseSelect = it
                        }
                        val chooseColor: (Int) -> Color = {
                            if (chooseSelect == 0) LightColor.OptionWordUnselect
                            else if ((2 - (screenModel.questionList[page] as JudgeQuestion).answer) == it) LightColor.OptionWordCorrect
                            else if (chooseSelect != it) LightColor.OptionWordUnselect
                            else LightColor.OptionWordError
                        }
                        val chooseBackGroundColor: (Int) -> Color = {
                            if (chooseSelect == 0) LightColor.Background
                            else if ((2 - (screenModel.questionList[page] as JudgeQuestion).answer) == it) LightColor.OptionBackgroundCorrect
                            else if (chooseSelect != it) LightColor.Background
                            else LightColor.OptionBackgroundError
                        }
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 1,
                            content = "对",
                            color = chooseColor(1),
                            backgroundColor = chooseBackGroundColor(1),
                            onClick = onClickChoose
                        )
                        ChoiceButton(
                            modifier = modifier.weight(1f),
                            optionName = 2,
                            content = "错",
                            color = chooseColor(2),
                            backgroundColor = chooseBackGroundColor(2),
                            onClick = onClickChoose
                        )
                    }
                }
                val coroutineScope = rememberCoroutineScope()
                Row(modifier = modifier.weight(1f)) {
                    Button(onClick = {
                        coroutineScope.launch {
                            if (pageState.currentPage != 0)
                                pageState.scrollToPage(pageState.currentPage - 1)
                        }
                    }) {
                        Text("上一题")
                    }
                    Button(onClick = {
                        coroutineScope.launch {
                            if (pageState.currentPage != screenModel.questionList.size - 1)
                                pageState.scrollToPage(pageState.currentPage + 1)
                        }

                    }) {
                        Text("下一题")
                    }
                }
            }
        }
    }

    @Composable
    fun ChoiceButton(
        modifier: Modifier = Modifier,
        optionName: Int,
        content: String,
        color: Color,
        backgroundColor: Color,
        onClick: (Int) -> Unit = { _ ->

        }
    ) {

        TextButton(
            onClick = {
                onClick(optionName)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
                .padding(4.dp)
                .background(
                    backgroundColor,
                    shape = RoundedCornerShape(10.dp)
                ),
            interactionSource = remember {
                MutableInteractionSource()
            }
        ) {
            Text(
                text = content,
                modifier = Modifier
                    .fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Left,
                    color = color,
                ),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}
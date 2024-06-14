package bean

data class JudgeQuestion(
    val question: String,
    val answer: Int
){
    override fun toString(): String {
        return "question:$question, answer:${if (answer == 0) "错" else "对"}}"
    }
}

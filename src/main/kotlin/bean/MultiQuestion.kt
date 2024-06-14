package bean

data class MultiQuestion(
    val question: String,
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val answer: String
){
    override fun toString(): String {
        return "question:$question a:$a b:$b c:$c d:$d answer:$answer"
    }
}

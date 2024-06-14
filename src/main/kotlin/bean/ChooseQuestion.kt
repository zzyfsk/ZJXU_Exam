package bean

data class ChooseQuestion(
    val question: String,
    val a: String,
    val b: String,
    val c: String,
    val d: String,
    val answer: String
){
    override fun toString(): String {
        return "$question,$a,$b,$c,$d,$answer"
    }
}

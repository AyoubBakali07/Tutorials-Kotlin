package tuto
fun main() {

    val trickFunction = trickOrTreat(false) {"$it quarters"}
    val treatFunction = trickOrTreat(true, null)
    treatFunction()
    repeat(3){
        trickFunction()
    }

}
fun trickOrTreat(isTrick: Boolean, extraTreat:((Int)->String)?): () -> Unit {
    if (isTrick){
        return ::trick
    }else{
        if(extraTreat != null){
            println(extraTreat(5))
        }
        return  treat
    }

}

fun trick() {
    println("No treats!")
}
val treat: () -> Unit = {
    println("Have a trick!")
}

package tuto

fun main() {
    val trafficLightColor = "Red"

    if (trafficLightColor == "Red") {
        println("Stop")
    }
}
//fun main() {
//    val trafficLightColor = "Black"
//
//    when (trafficLightColor) {
//        "Red" -> println("Stop")
//    }
//}
//fun main() {
//    val trafficLightColor = "orange"
//
//   when (trafficLightColor){
//       "Red"->println("hello")
//       "orange"->println("Go")
//else->println("viva")
//   }
//}
//fun main() {
//    val x:Any = 11
//
//   when (x){
//       1,3,5->println("hello")
//       in 8..10 ->println("biba")
//       is Int -> println(" $x is a number Not between 8..10")
//       else->println("viva")
//   }
//}
//fun main() {
//    val trafficLightColor = "Green"
//    val message =  when (trafficLightColor) {
//        "Red" -> "Stop"
//        "Yellow", "Amber" -> "Slow"
//        "Green" -> "Go"
//        else -> "Invalid traffic-light color"
//    }
//    println(message)
//}
//fun main() {
//    val trafficLightColor = "Green"
//    val message =  if (trafficLightColor == "Red") "Stop"
//    else if (trafficLightColor=="Orange")"GO"
//    else"Invalid"
//    println(message)
//}

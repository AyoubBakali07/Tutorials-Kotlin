fun birthdayGreeting(name: String, age: Int): String {
    val nameGreeting = "Happy Birthday, $name!" // Use the name parameter
    val ageGreeting = "You are now $age years old!" // Use the age parameter
    return "$nameGreeting\n$ageGreeting" // Return the formatted greeting
}

fun main() {
    // Call the function with the correct number of parameters
    println(birthdayGreeting("Ahmad", 5))
    println(birthdayGreeting("Krim", 3))
}
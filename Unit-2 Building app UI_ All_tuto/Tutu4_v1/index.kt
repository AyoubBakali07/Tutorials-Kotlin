// Kotlin permet d’écrire des conditions de manière concise et intuitive grâce à l’utilisation
//  d’expressions if et de l’opérateur when.

val age = 18
val status = if (age>18)"adulte" else "mineur"
fun main (){
println(status)
}
// when example
val grade = 99
val status = when {
    grade >= 90 -> "A"
    grade >= 80 -> "B"
    grade >= 70 -> "C"
    grade >= 60 -> "D"
    else -> "F"
}
// 2. Nullabilité
var name: String? =null
fun main (){
println(name?.length)
}
// output null
val name = "Solicode"

fun main (){
println(name?.length?:0)

}
// 3. Classes et Objets
// b) Constructeurs
// Kotlin prend en charge les constructeurs primaires et secondaires pour faciliter 
// l’initialisation des objets.
class Person(val name: String, val age: Int) {
    init {
        println("Création d'une personne : $name, $age ans")
    }

    // Constructeur secondaire
    constructor(name: String) : this(name, 0) {
        println("Création d'une personne avec un âge inconnu.")
    }
}


fun main (){
val person1 = Person("Alice", 25)
val person2 = Person("Bob")
println("hello ${person1.name}")  
}
// c) Propriétés et accesseurs
class Circle(val radius: Double){
    val area: Double 
    get() = Math.PI * radius * radius
}
fun main() {
    val circle = Circle(5.0)
    println("Aire du circle : ${circle.area}")
}
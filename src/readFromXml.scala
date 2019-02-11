/**
  * Created by Own on 1/25/2019.
  */
import xml._

case class Grade(value:Int, comment: String)
case class Student(fname:String, lname:String, quizzes:List[Grade], assigns:List[Grade], tests:List[Grade])

def gradeFromNode(n:Node):Grade={
  Grade((n \ "@grade").text.toInt, n.text)
}

def studentFromNode(n:Node):Student={
  val fname = (n \ "@fname").text
  val lname = (n \ "@lname").text
  val quizzes = (n \ "quiz").map(gradeFromNode).toList
  val assigns = (n \ "assignment").map(gradeFromNode).toList
  val tests = (n \ "test").map(gradeFromNode).toList

  Student(fname, lname , quizzes, assigns, tests )
}

val xmlData = XML.loadFile("gradebook.xml")
val course = (xmlData \ "courseName").text
println(course)

val students = (xmlData \ "student").map(studentFromNode).toArray
students foreach println
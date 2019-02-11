/**
  * Created by Own on 1/26/2019.
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

def nodeFromStudent(s:Student):Node={
  <student fname={s.fname.toString} lname={s.lname.toString}>
  {s.quizzes.map(g => <quiz grade={g.value.toString}>{g.comment}</quiz>)}
  {s.assigns.map(g => <assignment grade={g.value.toString}>{g.comment}</assignment>)}
  {s.tests.map(g=> <test grade ={g.value.toString}>{g.comment.toString}</test> )}
  </student>
}

val xmlData = XML.loadFile("gradebook.xml")
val courseName =(xmlData \"courseName").text

val students = (xmlData \ "student").map(studentFromNode).toArray

students(0) = students(0).copy(tests = Grade(95,"Well done")::students(0).tests)
students(1) = students(1).copy(tests = Grade(82,"Could have been better")::students(1).tests)
students(2) = students(2).copy(tests = Grade(75,"Need Practice")::students(2).tests)

XML.save("GradebookRevised.xml", <gradebook>
  <courseName>{courseName}</courseName>
  {students.map(nodeFromStudent)}
</gradebook>)

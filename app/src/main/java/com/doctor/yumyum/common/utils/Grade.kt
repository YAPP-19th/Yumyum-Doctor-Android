package com.doctor.yumyum.common.utils


val gradePoint:Map<String, Int> = mapOf(
    "학사" to 300,
    "석사" to 700,
    "박사" to 1200,
    "교수" to 1800
)

fun gradeEnToKo(grade: String):String {
    return when(grade) {
        "STUDENT" -> "학생"
        "BACHELOR" -> "학사"
        "MASTER" -> "석사"
        "EXPERT" -> "박사"
        "PROFESSOR" ->"교수"
        else ->  "학생"
    }
}
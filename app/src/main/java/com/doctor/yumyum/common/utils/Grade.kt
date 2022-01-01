package com.doctor.yumyum.common.utils

import com.doctor.yumyum.R

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
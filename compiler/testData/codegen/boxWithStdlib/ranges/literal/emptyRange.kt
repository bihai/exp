// Auto-generated by org.jetbrains.kotlin.generators.tests.GenerateRangesCodegenTestData. DO NOT EDIT!
import java.util.ArrayList
import java.lang as j

fun box(): String {
    val list1 = ArrayList<Int>()
    for (i in 10..5) {
        list1.add(i)
        if (list1.size() > 23) break
    }
    if (list1 != listOf<Int>()) {
        return "Wrong elements for 10..5: $list1"
    }

    val list2 = ArrayList<Byte>()
    for (i in 10.toByte()..(-5).toByte()) {
        list2.add(i)
        if (list2.size() > 23) break
    }
    if (list2 != listOf<Byte>()) {
        return "Wrong elements for 10.toByte()..(-5).toByte(): $list2"
    }

    val list3 = ArrayList<Short>()
    for (i in 10.toShort()..(-5).toShort()) {
        list3.add(i)
        if (list3.size() > 23) break
    }
    if (list3 != listOf<Short>()) {
        return "Wrong elements for 10.toShort()..(-5).toShort(): $list3"
    }

    val list4 = ArrayList<Long>()
    for (i in 10.toLong()..-5.toLong()) {
        list4.add(i)
        if (list4.size() > 23) break
    }
    if (list4 != listOf<Long>()) {
        return "Wrong elements for 10.toLong()..-5.toLong(): $list4"
    }

    val list5 = ArrayList<Char>()
    for (i in 'z'..'a') {
        list5.add(i)
        if (list5.size() > 23) break
    }
    if (list5 != listOf<Char>()) {
        return "Wrong elements for 'z'..'a': $list5"
    }

    val list6 = ArrayList<Double>()
    for (i in 5.0..-1.0) {
        list6.add(i)
        if (list6.size() > 23) break
    }
    if (list6 != listOf<Double>()) {
        return "Wrong elements for 5.0..-1.0: $list6"
    }

    val list7 = ArrayList<Float>()
    for (i in 5.0.toFloat()..-1.0.toFloat()) {
        list7.add(i)
        if (list7.size() > 23) break
    }
    if (list7 != listOf<Float>()) {
        return "Wrong elements for 5.0.toFloat()..-1.0.toFloat(): $list7"
    }

    return "OK"
}

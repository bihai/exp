// "Create function 'foo' from usage" "true"

import kotlin.properties.ReadOnlyProperty

class A<T>(val t: T) {
    val x: A<Int> by foo(t, "")

    fun <T> foo(t: T, s: String): ReadOnlyProperty<A<T>, A<Int>> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
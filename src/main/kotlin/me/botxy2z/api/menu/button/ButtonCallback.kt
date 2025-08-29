package me.botxy2z.api.menu.button

fun interface ButtonCallback<T> {
    fun callback(value: T)
}

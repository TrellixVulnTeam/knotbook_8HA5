package krangl.util

//
// Functional Helpers (missing in kotlin-stdlib)
//

// todo should they go into separate artifact?

// adopted from https://stackoverflow.com/questions/44429419/what-is-basic-difference-between-fold-and-reduce-in-kotlin-when-to-use-which
// desc from https://stackoverflow.com/questions/17408880/reduce-fold-or-scan-left-right
/** scanLeft and scanRight cumulate a collection of intermediate cumulative results using a start value. */
fun <T, R> Sequence<T>.scanLeft(initial: R, operation: (R, T) -> R) =
        fold(listOf(initial), { list, curVal -> list + operation(list.last(), curVal) })

fun <T, R> Sequence<T>.foldWhile(initial: R, operation: (R, T) -> R, predicate: (R) -> Boolean): R =
        scanLeft(initial, operation).takeWhile { predicate(it) }.last()

fun <T, R> Sequence<T>.foldUntil(initial: R, operation: (R, T) -> R, predicate: (R) -> Boolean): R? =
        scanLeft(initial, operation).dropWhile { predicate(it) }.firstOrNull()

fun <T, R> Sequence<T>.reduceUntil(operation: (R?, T) -> R, predicate: (R) -> Boolean): R? =
        drop(1).scanLeft(operation(null, first()), operation).dropWhile { predicate(it) }.firstOrNull()


// Misc helpers

internal fun Sequence<*>.joinToMaxLengthString(
        maxLength: Int = 80,
        separator: CharSequence = ", ",
        truncated: CharSequence = "...",
        transform: ((Any?) -> String) = { it?.toString() ?: "" }
): String =
        reduceUntil({ a: String?, b ->
            (a?.let { it + separator } ?: "") + transform(b)
        }, { it.length < maxLength })?.let {
            when {
                it.length < maxLength -> it
                else -> it.substring(0, maxLength) + truncated
            }
        } ?: joinToString(transform = { transform(it) })

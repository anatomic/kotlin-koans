package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.compareTo(other: MyDate): Int {
    return when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}
operator fun MyDate.plus(interval: TimeInterval): MyDate {
    return addTimeIntervals(interval, 1)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval): MyDate {
    return addTimeIntervals(repeatedTimeInterval.interval, repeatedTimeInterval.repeats)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(i: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, i)
}

class RepeatedTimeInterval(val interval: TimeInterval, val repeats: Int)
class DateRange(val start: MyDate, val endInclusive: MyDate)

operator fun DateRange.iterator() = DateIterator(this)
operator fun DateRange.contains(date: MyDate): Boolean {
    return start <= date && endInclusive >= date
}

class DateIterator(private val dateRange: DateRange): Iterator<MyDate> {
    var current: MyDate = dateRange.start

    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }

    override fun hasNext(): Boolean {
        return current <= dateRange.endInclusive
    }
}

package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate>{
    override fun compareTo(other: MyDate): Int {
        return when{
            this.year != other.year -> this.year - other.year
            this.month != other.month -> this.month - other.month
            else -> this.dayOfMonth - other.dayOfMonth
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun contains(value: MyDate): Boolean {
        return this.start <= value && value <= this.endInclusive
    }

    override fun iterator(): Iterator<MyDate> = DateIterator(this)
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var current: MyDate = dateRange.start
    override fun next(): MyDate {
        val result = current
        current = current.nextDay()
        return result
    }
    override fun hasNext(): Boolean = current <= dateRange.endInclusive
}

class RepeatedTimeIntervals(val timeInterval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) = RepeatedTimeIntervals(this, number)

operator fun MyDate.plus(value: TimeInterval) = addTimeIntervals(value, 1)
operator fun MyDate.plus(value: RepeatedTimeIntervals) = addTimeIntervals(value.timeInterval, value.number)

package iii_conventions

import java.util.*

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (this.year != other.year) return this.year - other.year
        if (this.month != other.month) return this.month - other.month
        if (this.dayOfMonth != other.dayOfMonth) return this.dayOfMonth - other.dayOfMonth
        return 0
    }
}

operator fun MyDate.plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)
operator fun MyDate.plus(intervals: MultipleTimeIntervals): MyDate = this.addTimeIntervals(intervals.interval, intervals.number)
operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}
data class MultipleTimeIntervals(val interval: TimeInterval, val number: Int);

operator fun TimeInterval.times(number: Int): MultipleTimeIntervals = MultipleTimeIntervals(this, number)


class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = object: Iterator<MyDate> {
        var current = start

        override fun hasNext(): Boolean {
            return current <= endInclusive
        }

        override fun next(): MyDate {
            if (isEmpty() || !hasNext()) {
                throw NoSuchElementException()
            }

            val result = current
            current = current.nextDay()
            return result
        }
    }
}

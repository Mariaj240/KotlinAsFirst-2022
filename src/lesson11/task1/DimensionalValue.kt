@file:Suppress("UNUSED_PARAMETER")

package lesson11.task1


import java.lang.reflect.Array.set
import kotlin.math.min

fun main() {
    val convertedValue: CheckValue = CheckValue()
    val kilo: DimensionalValue = DimensionalValue(30.0, "Kg")
    val mili: DimensionalValue = DimensionalValue(15.0, "mg")
    val sum = kilo.plus(mili)
    val uMinus = kilo.unaryMinus()
    val minus = kilo.minus(mili)
    val multiplication = kilo.times(mili.value)
    val division = kilo.div(mili)
    val equal = kilo.equals(mili)
    val compare = kilo.compareTo(mili)
    println(compare)
//    println(equal)
//    println(division)
//    println(multiplication.vlue)
//    println(minus.vlue)
//    println(uMinus.vlue)
//    println(sum.vlue)
//    println(kilo.dim)
//    println(kilo.vlue)
//    println(mili.dim)
//    println(mili.vlue)
}

class CheckValue() {

}


/**
 * Класс "Величина с размерностью".
 *
 * Предназначен для представления величин вроде "6 метров" или "3 килограмма"
 * Общая сложность задания - средняя, общая ценность в баллах -- 18
 * Величины с размерностью можно складывать, вычитать, делить, менять им знак.
 * Их также можно умножать и делить на число.
 *
 * В конструктор передаётся вещественное значение и строковая размерность.
 * Строковая размерность может:
 * - либо строго соответствовать одной из abbreviation класса Dimension (m, g)
 * - либо соответствовать одной из приставок, к которой приписана сама размерность (Km, Kg, mm, mg)
 * - во всех остальных случаях следует бросить IllegalArgumentException
 */
class DimensionalValue(value: Double, dimension: String) : Comparable<DimensionalValue> {
    val sourceValue: Double
    val sourceDimension: String

    init {
        sourceValue = value
        sourceDimension = dimension
    }

    /**
     * Величина с БАЗОВОЙ размерностью (например для 1.0Kg следует вернуть результат в граммах -- 1000.0)
     */
    val value = value
        get() {
            if (sourceDimension == "Kg" || sourceDimension == "Km") {
                return field * DimensionPrefix.KILO.multiplier
            } else if (sourceDimension == "mg" || sourceDimension == "mm") {
                return field * DimensionPrefix.MILLI.multiplier
            }
            return field
        }

    /**
     * БАЗОВАЯ размерность (опять-таки для 1.0Kg следует вернуть GRAM)
     */
    val dimension: Dimension = Dimension.METER
        get() {
            if (sourceDimension == Dimension.GRAM.abbreviation || sourceDimension == Dimension.GRAM.name) {
                return Dimension.GRAM
            } else if (sourceDimension == Dimension.METER.abbreviation || sourceDimension == Dimension.METER.name) {
                return Dimension.METER
            } else {
                if (sourceDimension[0].toString() == DimensionPrefix.KILO.abbreviation && sourceDimension[1].toString() == Dimension.GRAM.abbreviation) {
                    return Dimension.GRAM
                }
                if (sourceDimension[0].toString() == DimensionPrefix.MILLI.abbreviation && sourceDimension[1].toString() == Dimension.GRAM.abbreviation) {
                    return Dimension.GRAM
                }
            }
            if (sourceDimension[0].toString() == DimensionPrefix.KILO.abbreviation && sourceDimension[1].toString() == Dimension.METER.abbreviation) {
                return Dimension.METER
            }
            if (sourceDimension[0].toString() == DimensionPrefix.MILLI.abbreviation && sourceDimension[1].toString() == Dimension.METER.abbreviation) {
                return Dimension.METER
            }
            return field
        }


    /**
     * Конструктор из строки. Формат строки: значение пробел размерность (1 Kg, 3 mm, 100 g и так далее).
     */
    constructor(s: String) : this(s.split(" ")[0].toDouble(), s.split(" ")[1])


    /**
     * Сложение с другой величиной. Если базовая размерность разная, бросить IllegalArgumentException
     * (нельзя складывать метры и килограммы)
     */
    operator fun plus(other: DimensionalValue): DimensionalValue {
        if (dimension != other.dimension) throw IllegalArgumentException()
        val sum = value + other.value
        val newDimension: DimensionalValue = DimensionalValue(sum, this.dimension.toString())
        return newDimension
    }

    /**
     * Смена знака величины
     */
    operator fun unaryMinus(): DimensionalValue {
        val uValue = value.unaryMinus()
        val newDimension = DimensionalValue(uValue, dimension.name)
        return newDimension
    }

    /**
     * Вычитание другой величины. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun minus(other: DimensionalValue): DimensionalValue {
        if (dimension != other.dimension) throw IllegalArgumentException()
        val minus = value - other.value
        val newDimension = DimensionalValue(minus, dimension.name)
        return newDimension
    }

    /**
     * Умножение на число
     */
    operator fun times(other: Double): DimensionalValue {
        val multiplication = value * other
        val newDimension = DimensionalValue(multiplication, dimension.name)
        return newDimension
    }

    /**
     * Деление на число
     */
    operator fun div(other: Double): DimensionalValue {
        val division = value / other
        val newDimension = DimensionalValue(division, dimension.name)
        return newDimension
    }

    /**
     * Деление на другую величину. Если базовая размерность разная, бросить IllegalArgumentException
     */
    operator fun div(other: DimensionalValue): Double {
        val division = value / other.value
        if (dimension != other.dimension) throw IllegalArgumentException()
        return division
    }

    /**
     * Сравнение на равенство
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (value == (other as DimensionalValue).value && dimension == (other as DimensionalValue).dimension) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        return value.hashCode() + dimension.hashCode()
    }

    /**
     * Сравнение на больше/меньше. Если базовая размерность разная, бросить IllegalArgumentException
     */
    override fun compareTo(other: DimensionalValue): Int {
        if (dimension != other.dimension) throw IllegalArgumentException()
        if (value > other.value) {
            return 1
        } else if (value == other.value) {
            return 0
        }
        return -1
    }
}

/**
 * Размерность. В этот класс можно добавлять новые варианты (секунды, амперы, прочие), но нельзя убирать
 */
enum class Dimension(val abbreviation: String) {
    METER("m"),
    GRAM("g");
}

/**
 * Приставка размерности. Опять-таки можно добавить новые варианты (деци-, санти-, мега-, ...), но нельзя убирать
 */
enum class DimensionPrefix(val abbreviation: String, val multiplier: Double) {
    KILO("K", 1000.0),
    MILLI("m", 0.001);
}
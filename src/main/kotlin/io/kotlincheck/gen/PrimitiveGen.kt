package io.kotlincheck.gen

import io.kotlincheck.Random
import java.math.BigDecimal
import java.math.BigInteger

class LongGen(val origin: Long, val bound: Long) : Gen<Long> {
    override fun generate(): Long = Random.nextLong(origin, bound)
}

class IntGen(val origin: Int, val bound: Int) : Gen<Int> {
    override fun generate(): Int = Random.nextInt(origin, bound)
}

class ShortGen(val origin: Short, val bound: Short) : Gen<Short> {
    override fun generate(): Short = Random.nextShort(origin, bound)
}

class ByteGen(val origin: Byte, val bound: Byte) : Gen<Byte> {
    override fun generate(): Byte = Random.nextByte(origin, bound)
}

class CharGen(val origin: Char, val bound: Char) : Gen<Char> {
    override fun generate(): Char = Random.nextChar(origin, bound)
}

class BooleanGen : Gen<Boolean> {
    override fun generate(): Boolean = Random.nextBoolean()
}

class DoubleGen(val origin: Double, val bound: Double) : Gen<Double> {
    override fun generate(): Double = Random.nextDouble(origin, bound)
}

class FloatGen(val origin: Float, val bound: Float) : Gen<Float> {
    override fun generate(): Float = Random.nextFloat(origin, bound)
}

class BigDecimalGen(val origin: BigDecimal, val bound: BigDecimal) : Gen<BigDecimal> {
    override fun generate(): BigDecimal = Random.nextBigDecimal(origin, bound)
}

class BigIntegerGen(val origin: BigInteger, val bound: BigInteger) : Gen<BigInteger> {
    override fun generate(): BigInteger = Random.nextBigInteger(origin, bound)
}

class OneOfGen<out T>(val values: List<T>) : Gen<T> {
    override fun generate(): T = values[Random.nextInt(0, values.size)]
}

class OneOfGenGen<out T>(val gens: List<Gen<T>>) : Gen<T> {
    override fun generate(): T = gens[Random.nextInt(0, gens.size)].generate()
}

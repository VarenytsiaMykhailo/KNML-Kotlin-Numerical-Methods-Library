//[knml](../../../index.md)/[com.github.varenytsiamykhailo.knml.util](../index.md)/[Vector](index.md)

# Vector

[jvm]\
class [Vector](index.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))

Vector implementation.

This class implements 'linear vector' with it's elements (coordinates).

## Constructors

| | |
|---|---|
| [Vector](-vector.md) | [jvm]<br>fun [Vector](-vector.md)(elems: [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html))<br>This constructor initializes the Vector by the input Array<Double> elems (coordinates). |
| [Vector](-vector.md) | [jvm]<br>fun [Vector](-vector.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>This constructor sets default Double type zero values *0.0* to each element of the vector. |

## Functions

| Name | Summary |
|---|---|
| [equals](equals.md) | [jvm]<br>open operator override fun [equals](equals.md)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getElem](get-elem.md) | [jvm]<br>fun [getElem](get-elem.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Returns elem (coordinate). |
| [getElems](get-elems.md) | [jvm]<br>fun [getElems](get-elems.md)(): [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html)<br>Returns Array<Double> elems (coordinates) of the vector. |
| [getN](get-n.md) | [jvm]<br>fun [getN](get-n.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Returns n - the size (dimension/index) of the vector, starting from 1. |
| [hashCode](hash-code.md) | [jvm]<br>open override fun [hashCode](hash-code.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [setElem](set-elem.md) | [jvm]<br>fun [setElem](set-elem.md)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), elem: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))<br>Set elem (coordinate). |
| [setElems](set-elems.md) | [jvm]<br>fun [setElems](set-elems.md)(elems: [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html))<br>Sets Array<Double> elems (coordinates) into the vector. |
| [toString](to-string.md) | [jvm]<br>open override fun [toString](to-string.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |

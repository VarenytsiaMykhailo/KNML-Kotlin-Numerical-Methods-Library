---
title: Vector
---
//[knml](../../../index.html)/[com.github.varenytsiamykhailo.knml.util](../index.html)/[Vector](index.html)



# Vector



[jvm]\
class [Vector](index.html)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))

Vector implementation.



This class implements 'linear vector' with it's elements (coordinates).



## Constructors


| | |
|---|---|
| [Vector](-vector.html) | [jvm]<br>fun [Vector](-vector.html)(elems: [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html))<br>This constructor initializes the Vector by the input Array<Double> elems (coordinates). |
| [Vector](-vector.html) | [jvm]<br>fun [Vector](-vector.html)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>This constructor sets default Double type zero values *0.0* to each element of the vector. |


## Functions


| Name | Summary |
|---|---|
| [equals](equals.html) | [jvm]<br>open operator override fun [equals](equals.html)(other: [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/index.html)?): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [getElem](get-elem.html) | [jvm]<br>fun [getElem](get-elem.html)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Returns elem (coordinate). |
| [getElems](get-elems.html) | [jvm]<br>fun [getElems](get-elems.html)(): [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html)<br>Returns Array<Double> elems (coordinates) of the vector. |
| [getN](get-n.html) | [jvm]<br>fun [getN](get-n.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Returns n - the size (dimension/index) of the vector, starting from 1. |
| [hashCode](hash-code.html) | [jvm]<br>open override fun [hashCode](hash-code.html)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [setElem](set-elem.html) | [jvm]<br>fun [setElem](set-elem.html)(n: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), elem: [Double](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html))<br>Set elem (coordinate). |
| [setElems](set-elems.html) | [jvm]<br>fun [setElems](set-elems.html)(elems: [DoubleArray](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double-array/index.html))<br>Sets Array<Double> elems (coordinates) into the vector. |
| [toString](to-string.html) | [jvm]<br>open override fun [toString](to-string.html)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |


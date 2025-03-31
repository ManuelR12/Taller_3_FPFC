import ManiobraTrenes._
val t1 = List("a", "b", "c", "d", "g", "f")
val t2 = List("d", "b", "c", "a","f", "g")
val maniobra = definirManiobra(t1, t2)
val estados = aplicarMovimientos((t1, Nil, Nil), maniobra)

// Resultado final:
// estados.last = (List(d, b, c, a), Nil, Nil)
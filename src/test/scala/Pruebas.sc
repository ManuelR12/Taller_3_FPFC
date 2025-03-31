import ManiobraTrenes._
val t1 = List("k", "l", "m", "n")
val t2 = List("m", "l", "n", "k")
val maniobra = definirManiobra(t1, t2)
val estados = aplicarMovimientos((t1, Nil, Nil), maniobra)

// Resultado final:
// estados.last = (List(d, b, c, a), Nil, Nil)
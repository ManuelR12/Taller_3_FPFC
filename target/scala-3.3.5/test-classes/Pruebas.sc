import ManiobraTrenes._
val e1 = (List("a", "b", "c", "d"), Nil, Nil)
val e2 = aplicarMovimiento(e1, Uno(2))

definirManiobra(List("a","b","c","d"), List("d","b","c","a"))
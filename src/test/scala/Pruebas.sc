import ManiobraTrenes._

println("=============================================")
println("===      Pruebas para aplicarMovimiento   ===")
println("=============================================")

// --- Caso 1: Mover un solo vagón de Pista Principal a Via Uno ---
val estadoInicial1 = (List(10, 20, 30), List(), List())
val movimiento1 = Uno(1)
val estadoResultado1 = aplicarMovimiento(estadoInicial1, movimiento1)
println(s"Test 1: Mover 1 de P a U")
println(s"  Inicial:  $estadoInicial1")
println(s"  Movimiento: $movimiento1")
println(s"  Resultado: $estadoResultado1") // Resultado Esperado: (List(10, 20), List(30), List())
println("-" * 20)

// --- Caso 2: Mover múltiples vagones de Pista Principal a Via Dos ---
val estadoInicial2 = (List('x', 'y', 'z', 'w'), List('a'), List())
val movimiento2 = Dos(2)
val estadoResultado2 = aplicarMovimiento(estadoInicial2, movimiento2)
println(s"Test 2: Mover 2 de P a D")
println(s"  Inicial:  $estadoInicial2")
println(s"  Movimiento: $movimiento2")
println(s"  Resultado: $estadoResultado2") // Resultado Esperado: (List('x', 'y'), List('a'), List('z', 'w'))
println("-" * 20)

// --- Caso 3: Mover un vagón de Via Uno a Pista Principal ---
val estadoInicial3 = (List(5), List(15, 25), List(35))
val movimiento3 = Uno(-1)
val estadoResultado3 = aplicarMovimiento(estadoInicial3, movimiento3)
println(s"Test 3: Mover 1 de U a P")
println(s"  Inicial:  $estadoInicial3")
println(s"  Movimiento: $movimiento3")
println(s"  Resultado: $estadoResultado3") // Resultado Esperado: (List(5, 15), List(25), List(35))
println("-" * 20)

// --- Caso 4: Mover múltiples vagones de Via Dos a Pista Principal (Pista Principal vacía inicialmente) ---
val estadoInicial4 = (List(), List('p'), List('q', 'r', 's'))
val movimiento4 = Dos(-2)
val estadoResultado4 = aplicarMovimiento(estadoInicial4, movimiento4)
println(s"Test 4: Mover 2 de D a P (P inicial vacía)")
println(s"  Inicial:  $estadoInicial4")
println(s"  Movimiento: $movimiento4")
println(s"  Resultado: $estadoResultado4") // Resultado Esperado: (List('q', 'r'), List('p'), List('s'))
println("-" * 20)

// --- Caso 5: Intento de movimiento inválido (mover desde Pista Principal vacía) ---
val estadoInicial5 = (List(), List(100), List(200))
val movimiento5 = Uno(1) // Intentar mover 1 desde P (vacía)
val estadoResultado5 = aplicarMovimiento(estadoInicial5, movimiento5)
println(s"Test 5: Movimiento inválido (Mover desde P vacía)")
println(s"  Inicial:  $estadoInicial5")
println(s"  Movimiento: $movimiento5")
println(s"  Resultado: $estadoResultado5") // Resultado Esperado: (List(), List(100), List(200))
println("-" * 20)


println("\n=============================================")
println("===     Pruebas para aplicarMovimientos   ===")
println("=============================================")

// --- Caso 1: Secuencia simple alternando vías ---
val estadoBase1 = (List(1, 2, 3, 4), Nil, Nil)
val maniobra1 = List(Uno(1), Dos(1), Uno(1), Dos(1))
val historialEstados1 = aplicarMovimientos(estadoBase1, maniobra1)
println(s"Test 1: Secuencia alternando vías U y D")
println(s"  Estado Inicial: $estadoBase1")
println(s"  Maniobra: $maniobra1")
println(s"  Historial de Estados:")
historialEstados1.foreach(e => println(s"    $e"))
// Resultado Esperado: List((List(1, 2, 3, 4),List(),List()), (List(1, 2, 3),List(4),List()), (List(1, 2),List(4),List(3)), (List(1),List(2, 4),List(3)), (List(),List(2, 4),List(1, 3)))
println("-" * 20)

// --- Caso 2: Vaciar Pista Principal y luego regresarle vagones ---
val estadoBase2 = (List('a', 'b'), Nil, Nil)
val maniobra2 = List(Uno(1), Dos(1), Uno(-1), Dos(-1)) // P->U, P->D, U->P, D->P
val historialEstados2 = aplicarMovimientos(estadoBase2, maniobra2)
println(s"Test 2: Vaciar P y luego rellenar desde U y D")
println(s"  Estado Inicial: $estadoBase2")
println(s"  Maniobra: $maniobra2")
println(s"  Historial de Estados:")
historialEstados2.foreach(e => println(s"    $e")) // Resultado Esperado: List((List(a, b),List(),List()), (List(a),List(b),List()), (List(),List(b),List(a)), (List(b),List(),List(a)), (List(b, a),List(),List()))
println("-" * 20)

// --- Caso 3: Maniobra vacía ---
val estadoBase3 = (List(7, 8, 9), Nil, Nil)
val maniobra3 = List()
val historialEstados3 = aplicarMovimientos(estadoBase3, maniobra3)
println(s"Test 3: Maniobra vacía")
println(s"  Estado Inicial: $estadoBase3")
println(s"  Maniobra: $maniobra3")
println(s"  Historial de Estados:")
historialEstados3.foreach(e => println(s"    $e"))
// Resultado Esperado: List((List(7, 8, 9), Nil, Nil))
println("-" * 20)

// --- Caso 4: Secuencia con movimiento inválido intermedio ---
val estadoBase4 = (List(10), List(20), Nil)
val maniobra4 = List(Uno(-1), Dos(2), Uno(1)) // U->P, Intento P->D (inválido, solo hay 10,20 en P), P->U
val historialEstados4 = aplicarMovimientos(estadoBase4, maniobra4)
println(s"Test 4: Secuencia con movimiento inválido")
println(s"  Estado Inicial: $estadoBase4")
println(s"  Maniobra: $maniobra4")
println(s"  Historial de Estados:")
historialEstados4.foreach(e => println(s"    $e"))
// Resultado Esperado: List((List(10),List(20),List()), (List(10, 20),List(),List()), (List(),List(),List(10, 20)), (List(),List(),List(10, 20)))
println("-" * 20)

// --- Caso 5: Reorganización compleja ---
val estadoBase5 = (List(1, 2, 3, 4, 5), Nil, Nil)
val maniobra5 = List(Uno(2), Dos(2), Uno(-1), Dos(-1), Uno(1))
val historialEstados5 = aplicarMovimientos(estadoBase5, maniobra5)
println(s"Test 5: Reorganización más compleja")
println(s"  Estado Inicial: $estadoBase5")
println(s"  Maniobra: $maniobra5")
println(s"  Historial de Estados:")
historialEstados5.foreach(e => println(s"    $e"))
// Resultado Esperado:  List((List(1, 2, 3, 4, 5),List(),List()), (List(1, 2, 3),List(4, 5),List()), (List(1),List(4, 5),List(2, 3)), (List(1, 4),List(5),List(2, 3)), (List(1, 4, 2),List(5),List(3)), (List(1, 4),List(2, 5),List(3)))

println("\n=============================================")
println("===     Pruebas para definirManiobra      ===")
println("=============================================")

// --- Caso 1: Invertir un tren de 2 vagones ---
val trenInicial1 = List(1, 2)
val trenFinal1 = List(2, 1)
println(s"Test 1: Invertir ${trenInicial1} a ${trenFinal1}")
try {
  val maniobraRes1 = definirManiobra(trenInicial1, trenFinal1)
  println(s"  Maniobra encontrada: $maniobraRes1")
  // Verificar aplicando la maniobra:
  val estadoVerif1 = aplicarMovimientos((trenInicial1, Nil, Nil), maniobraRes1).last
  println(s"  Resultado aplicación: $estadoVerif1") // Resultado Esperado: (List(2, 1), Nil, Nil)
} catch {
  case e: Exception => println(s"  Error: ${e.getMessage}")
}

// --- Caso 2: Invertir un tren de 3 vagones ---
val trenInicial2 = List('a', 'b', 'c')
val trenFinal2 = List('c', 'b', 'a')
println(s"Test 2: Invertir ${trenInicial2} a ${trenFinal2}")
try {
  val maniobraRes2 = definirManiobra(trenInicial2, trenFinal2)
  println(s"  Maniobra encontrada: $maniobraRes2")
  val estadoVerif2 = aplicarMovimientos((trenInicial2, Nil, Nil), maniobraRes2).last
  println(s"  Resultado aplicación: $estadoVerif2") // Resultado Esperado: (List('c', 'b', 'a'), Nil, Nil)
} catch {
  case e: Exception => println(s"  Error: ${e.getMessage}")
}

// --- Caso 3: Tren inicial y final idénticos ---
val trenInicial3 = List("tren", "rapido")
val trenFinal3 = List("tren", "rapido")
println(s"Test 3: Tren inicial idéntico al final ${trenInicial3}")
try {
  val maniobraRes3 = definirManiobra(trenInicial3, trenFinal3)
  println(s"  Maniobra encontrada: $maniobraRes3") // Resultado Esperado: List()
  val estadoVerif3 = aplicarMovimientos((trenInicial3, Nil, Nil), maniobraRes3).last
  println(s"  Resultado aplicación: $estadoVerif3") // Resultado Esperado: (List("tren", "rapido"), Nil, Nil)
} catch {
  case e: Exception => println(s"  Error: ${e.getMessage}")
}

// --- Caso 4: Mover el último vagón al principio ---
val trenInicial4 = List(10, 20, 30, 40)
val trenFinal4 = List(40, 10, 20, 30)
println(s"Test 4: Mover último al principio ${trenInicial4} a ${trenFinal4}")
try {
  val maniobraRes4 = definirManiobra(trenInicial4, trenFinal4)
  println(s"  Maniobra encontrada: $maniobraRes4")
  val estadoVerif4 = aplicarMovimientos((trenInicial4, Nil, Nil), maniobraRes4).last
  println(s"  Resultado aplicación: $estadoVerif4") // Resultado Esperado: (List(40, 10, 20, 30), Nil, Nil)
} catch {
  case e: Exception => println(s"  Error: ${e.getMessage}")
}

// --- Caso 5: Reordenamiento complejo ---
val trenInicial5 = List('P', 'r', 'u', 'e', 'b', 'a')
val trenFinal5 = List('a', 'e','P', 'u','b', 'r')
println(s"Test 5: Reordenamiento complejo ${trenInicial5} a ${trenFinal5}")
try {
  val maniobraRes5 = definirManiobra(trenInicial5, trenFinal5)
  println(s"  Maniobra encontrada: $maniobraRes5")
  val estadoVerif5 = aplicarMovimientos((trenInicial5, Nil, Nil), maniobraRes5).last
  println(s"  Resultado aplicación: $estadoVerif5") // Resultado Esperado: (List('a', 'b', 'e', 'u', 'r', 'P'), Nil, Nil)
} catch {
  case e: Exception => println(s"  Error: ${e.getMessage}")
}

println("\n=============================================")
println("===        Fin de las Pruebas           ===")
println("=============================================")
package object ManiobraTrenes {
  type Vagon = Any
  type Tren = List[Vagon]
  type Estado = (Tren, Tren, Tren)

  trait Movimiento

  case class Uno(n: Int) extends Movimiento

  case class Dos(n: Int) extends Movimiento

  type Maniobra = List[Movimiento]

  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = (e, m) match {

    case ((p, u, d), Uno(n)) if n > 0 =>
      val quedan = p.take(p.length - n)
      val mover = p.drop(p.length - n)
      (quedan, mover ++ u, d)

    case ((p, u, d), Dos(n)) if n > 0 =>
      val quedan = p.take(p.length - n)
      val mover = p.drop(p.length - n)
      (quedan, u, mover ++ d)

    case ((p, u, d), Uno(n)) if n < 0 =>
      val mover = u.take(-n)
      val quedan = u.drop(-n)
      (p ++ mover, quedan, d)

    case ((p, u, d), Dos(n)) if n < 0 =>
      val mover = d.take(-n)
      val quedan = d.drop(-n)
      (p ++ mover, u, quedan)

    case _ => e
  }

  def aplicarMovimientos(e: Estado, movs: Maniobra): List[Estado] = {
    movs.foldLeft(List(e)) { (estados, movimiento) =>
      aplicarMovimiento(estados.head, movimiento) :: estados
    }
  }.reverse

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    require(t1.toSet == t2.toSet, "Los trenes deben contener los mismos vagones")

    type Estado = (Tren, Tren, Tren)
    type Nodo = (Estado, Maniobra)

    @annotation.tailrec
    def buscarSolucion(frontera: List[Nodo], visitados: Set[Estado]): Maniobra = frontera match {
      case Nil =>
        throw new Exception("No se encontró solución")

      case ((principal, uno, dos), movimientos) :: tail =>
        (principal, uno, dos) match {
          case (`t2`, Nil, Nil) =>
            movimientos.reverse

          case _ if visitados.contains((principal, uno, dos)) =>
            buscarSolucion(tail, visitados)

          case _ =>
            val nuevosNodos = generarMovimientosValidos((principal, uno, dos))
              .map { case (estado, mov) => (estado, mov :: movimientos) }

            buscarSolucion(tail ++ nuevosNodos, visitados + ((principal, uno, dos)))
        }
    }

    def generarMovimientosValidos(estado: Estado): List[(Estado, Movimiento)] = estado match {
      case (Nil, u, d) =>
        List(
          if (u.nonEmpty) Some((List(u.head), u.tail, d), Uno(-1)) else None,
          if (d.nonEmpty) Some((List(d.head), u, d.tail), Dos(-1)) else None
        ).flatten

      case (p, u, d) =>
        List(
          Some((p.init, p.last :: u, d), Uno(1)),
          if (u.nonEmpty) Some((p :+ u.head, u.tail, d), Uno(-1)) else None,
          Some((p.init, u, p.last :: d), Dos(1)),
          if (d.nonEmpty) Some((p :+ d.head, u, d.tail), Dos(-1)) else None
        ).flatten
    }

    buscarSolucion(List(((t1, Nil, Nil), Nil)), Set.empty)
  }
}
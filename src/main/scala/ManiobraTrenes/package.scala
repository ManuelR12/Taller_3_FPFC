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

    def buscarSolucion(frontera: List[Nodo], visitados: Set[Estado]): Maniobra = frontera match {
      case Nil =>
        throw new Exception("No se encontró solución")

      case ((`t2`, Nil, Nil), movimientos) :: _ =>
        movimientos.reverse

      case ((estado, movimientos) :: tail) if visitados.contains(estado) =>
        buscarSolucion(tail, visitados)

      case (((p, u, d), movimientos) :: tail) =>
        val nuevosNodos = generarMovimientosValidos(p, u, d)
          .map { case ((np, nu, nd), mov) => ((np, nu, nd), mov :: movimientos) }

        buscarSolucion(tail ++ nuevosNodos, visitados + ((p, u, d)))
    }

    def generarMovimientosValidos(p: Tren, u: Tren, d: Tren): List[(Estado, Movimiento)] = (p, u, d) match {
      case (Nil, u, d) =>
        (u match {
          case h :: t => List((List(h), t, d) -> Uno(-1))
          case Nil => Nil
        }) ::: (d match {
          case h :: t => List((List(h), u, t) -> Dos(-1))
          case Nil => Nil
        })

      case (p, u, d) =>
        List(
          (p.init, p.last :: u, d) -> Uno(1),
          (p.init, u, p.last :: d) -> Dos(1)
        ) ::: (u match {
          case h :: t => List((p :+ h, t, d) -> Uno(-1))
          case Nil => Nil
        }) ::: (d match {
          case h :: t => List((p :+ h, u, t) -> Dos(-1))
          case Nil => Nil
        })
    }
    buscarSolucion(List(((t1, Nil, Nil), Nil)), Set.empty)
  }
}
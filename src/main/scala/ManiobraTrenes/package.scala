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
    }.reverse
  }

 /* def definirManiobra(t1: Tren, t2: Tren): Maniobra = {

  }*/
}


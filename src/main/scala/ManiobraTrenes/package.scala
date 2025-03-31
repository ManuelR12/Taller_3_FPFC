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

    import scala.collection.mutable.Queue

    case class Nodo(estado: Estado, movimientos: Maniobra, depth: Int)

    def bfs(): Maniobra = {
      val queue = Queue(Nodo((t1, Nil, Nil), Nil, 0))
      val visited = scala.collection.mutable.Set[Estado]()
      val maxDepth = t1.length * 10

      while (queue.nonEmpty) {
        val current = queue.dequeue()
        val (principal, uno, dos) = current.estado
        val movimientos = current.movimientos
        val depth = current.depth

        if (depth > maxDepth) throw new Exception("No se encontró solución en el límite de pasos")
        if (visited.contains(current.estado)) ()
        else {
          visited += current.estado

          if (principal == t2 && uno.isEmpty && dos.isEmpty) {
            return movimientos.reverse
          } else {
            // Generar movimientos válidos
            val posiblesMovimientos = List(
              if (principal.nonEmpty) Some(Uno(principal.length)) else None,
              if (principal.nonEmpty) Some(Dos(principal.length)) else None,
              if (uno.nonEmpty) Some(Uno(-1)) else None,
              if (dos.nonEmpty) Some(Dos(-1)) else None
            ).flatten

            // Añadir nuevos nodos a la cola
            posiblesMovimientos.foreach { movimiento =>
              val nuevoEstado = aplicarMovimiento(current.estado, movimiento)
              if (!visited.contains(nuevoEstado)) {
                queue.enqueue(Nodo(nuevoEstado, movimiento :: movimientos, depth + 1))
              }
            }
          }
        }
      }
      throw new Exception("No se encontró solución")
    }

    bfs()
  }
}
package server.interfaces;

import rmi.interfaces.Mover;
import rmi.structs.Caminho;

public interface Rastreador {

	public Caminho findPath(Mover mover, int sx, int sy, int tx, int ty);
}

package server.interfaces;

import com.structs.Caminho;

import rmi.interfaces.Mover;

public interface Rastreador {

	public Caminho findPath(Mover mover, int sx, int sy, int tx, int ty,boolean allowDiagMovement);
}

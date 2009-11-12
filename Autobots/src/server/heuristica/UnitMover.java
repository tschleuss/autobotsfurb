package server.heuristica;

import java.io.Serializable;

import rmi.interfaces.Mover;

public class UnitMover implements Mover, Serializable {

	private int type;
	
	public UnitMover(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
}

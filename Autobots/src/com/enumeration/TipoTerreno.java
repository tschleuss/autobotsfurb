package com.enumeration;

public enum TipoTerreno {

	GRASS (0),
	WATER (1),
	TREES (2), 
	ROBOT (3),
	BOX   (4),	
	GOAL  (5),
	ROBOT_BOX(6);
	
	private int type;
	
	TipoTerreno(int c){
		setType(c);
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
}

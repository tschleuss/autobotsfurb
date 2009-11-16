package server.heuristica.busca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.enumeration.TipoTerreno;

import rmi.interfaces.Map;
import rmi.structs.Passo;

public class BuscaElemento {

	public BuscaElemento() {
		super();
	}
	
	/**
	 * Busca pelo elemento passado por parametro, dentro do mapa
	 * passado por parametro.
	 * @param type
	 * @param map
	 * @param x
	 * @param y
	 * @return
	 */
	public Passo buscaElemento(TipoTerreno type, Map map, int x, int y) {

		List<Passo> locations = new ArrayList<Passo>();
		List<Passo> okLocation = new ArrayList<Passo>();
		
		for( int mX = 0; mX < map.getWidthInTiles(); mX ++ )
		{
			for( int mY = 0; mY < map.getHeightInTiles(); mY ++ )
			{
				if( map.getTerrain(mX, mY) == type.getType() ) 
				{
					locations.add( new Passo( mX , mY ) );
				}
			}			
		}

		for( Passo s : locations )
		{
			Passo up = checkUp(TipoTerreno.GRASS, map, s);
			Passo bo = checkBottom(TipoTerreno.GRASS, map, s);
			Passo lf = checkLeft(TipoTerreno.GRASS, map, s);
			Passo rg = checkRight(TipoTerreno.GRASS, map, s);
			
			if( up != null ) {
				okLocation.add(up);
			} else if( bo != null ) {
				okLocation.add(bo);
			} else if( lf != null ) {
				okLocation.add(lf);
			} else if ( rg != null ) {
				okLocation.add(rg);
			}
		}

		locations.clear();					//Limpa os elementos inalcansaveis
		Collections.shuffle(okLocation);	//Embaralha
		return okLocation.get(0);			//retorna o elemento na poscai 0
	}
	
	/**
	 * Verifica se existe o tipo de terreno passado
	 * por parametro, a cima do tile existente na
	 * posicao passada pelo Objecto Step.
	 * @param type
	 * @param map
	 * @param s
	 * @return
	 */
	private Passo checkUp( TipoTerreno type, Map map, Passo s ) 
	{
		if( s.getY() == 0 )
		{
			return null;
		}
		else {
			
			if( map.getTerrain( s.getX() , s.getY() - 1 ) == type.getType() )
			{
				return new Passo( s.getX() , s.getY() - 1 );
			}
		}
		
		return null;
	}
	
	/**
	 * Verifica se existe o tipo de terreno passado
	 * por parametro, a baixo do tile existente na
	 * posicao passada pelo Objecto Step.
	 * @param type
	 * @param map
	 * @param s
	 * @return
	 */
	private Passo checkBottom( TipoTerreno type, Map map, Passo s ) 
	{
		if( s.getY() == map.getHeightInTiles()-1 )
		{
			return null;
		}
		else {
			
			if( map.getTerrain( s.getX() , s.getY() + 1 ) == type.getType() )
			{
				return new Passo( s.getX() , s.getY() + 1 );
			}
		}
		
		return null;
	}
	
	/**
	 * Verifica se existe o tipo de terreno passado
	 * por parametro, a esquerda do tile existente na
	 * posicao passada pelo Objecto Step.
	 * @param type
	 * @param map
	 * @param s
	 * @return
	 */
	private Passo checkLeft( TipoTerreno type, Map map, Passo s ) 
	{
		if( s.getX() == 0 )
		{
			return null;
		}
		else {
			
			if( map.getTerrain( s.getX() - 1 , s.getY() ) == type.getType() )
			{
				return new Passo( s.getX() - 1 , s.getY() );
			}
		}
		
		return null;
	}
	
	/**
	 * Verifica se existe o tipo de terreno passado
	 * por parametro, a direita do tile existente na
	 * posicao passada pelo Objecto Step.
	 * @param type
	 * @param map
	 * @param s
	 * @return
	 */
	private Passo checkRight( TipoTerreno type, Map map, Passo s ) 
	{
		if( s.getX() == map.getWidthInTiles()-1 )
		{
			return null;
		}
		else {
			
			if( map.getTerrain( s.getX() + 1 , s.getY() ) == type.getType() )
			{
				return new Passo( s.getX() + 1 , s.getY() );
			}
		}
		
		return null;
	}
}

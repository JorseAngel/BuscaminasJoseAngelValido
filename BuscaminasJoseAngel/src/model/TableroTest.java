package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import utiles.Utiles;

class TableroTest {

	private int lado;
	private int porcentaje;
	private int minas;
	private Tablero tablero;

	@BeforeEach
	void setUp() {
		lado = 4;
		porcentaje = 10;
		minas = Utiles.calculaMinas(lado, porcentaje);
		tablero = new Tablero(lado, minas);
	}

	@Test
	void testGetNumeroMinas() {
		Tablero tablero = new Tablero(lado, minas);
		
		assertEquals(minas, tablero.getNumeroMinas());
	}
	
//	@Test
//	void testIncrementarMinasAlrededor() {
//		int posX=0;
//		int posY=0;
//		int lado=4;
//		Coordenada miMinaCoordenada=new Coordenada(posX, posY);
//		Tablero tablero=new Tablero(lado);
//		tablero.getCasilla(miMinaCoordenada).setMina(true);
//		tablero.establecerMinasAlrededor(miMinaCoordenada);
//		int resultado[][]= {
//				{0,1,0,0},
//				{1,1,0,0},
//				{0,0,0,0},
//				{0,0,0,0}};
//		probando(tablero, resultado);
//		//Si coloco la segundaMina;
//		posX=3;
//		posY=3;
//		miMinaCoordenada=new Coordenada(posX, posY);
//		tablero.getCasilla(miMinaCoordenada).setMina(true);
//		tablero.establecerMinasAlrededor(miMinaCoordenada);
//		int resultadoDos[][]= {
//				{0,1,0,0},
//				{1,1,0,0},
//				{0,0,1,1},
//				{0,0,1,0}};
//		probando(tablero, resultadoDos);
//		
//	}
//
//	private void probando(Tablero tablero, int[][] resultado) {
//		Casilla[][] casillas = tablero.getCasillas();
//		for (int i = 0; i < casillas.length; i++) {
//			for (int j = 0; j < casillas.length; j++) {
//				assertEquals(resultado[i][j],casillas[i][j].getMinasAlrededor());
//			}
//		}
//	}

	@Test
	void testTableroColocarMinas() {
		Tablero tablero = new Tablero(lado, minas);
		int contadorMinas = 0;

		for (int i = 0; i < lado; i++) {
			for (int j = 0; j < lado; j++) {
				if (tablero.getCasilla(new Coordenada(i, j)).isMina()) {
					contadorMinas++;
				}
			}
		}
		assertEquals(minas, contadorMinas);
	}

	@Test
	void testDesvelarCasilla() {
		// Hay que probar que se desvela si no esta marcada
		// Si hay 0 minas debe comenzar un proceso recursivo y
		// debéis probar que desvela las casillas contiguas que tb
		// son cero
		System.out.println();
	}

	@Test
	void testTableroIncrementaMinas() {
		for (int i = 0; i < lado; i++) {
			for (int j = 0; j < lado; j++) {
				Coordenada posicion = new Coordenada(i, j);
				Casilla actual = tablero.getCasilla(posicion);
				int minasAlrededor = actual.getMinasAlrededor();
				// necesito algo que cuente las minas aldedor
				assertEquals(minasAlrededor, contarMinasAlrededor(tablero, posicion));
			}
		}
	}

	// Deberia probar este codigo
	private int contarMinasAlrededor(Tablero tablero, Coordenada posicion) {
		int bombasAlrededor = 0;
		int x = posicion.getPosX();
		int y = posicion.getPosY();
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				Coordenada alrededor = new Coordenada(i, j);
				// No tengo en cuenta la posicion que estoy comprobando
				if (!alrededor.equals(posicion)) {
					if (isDentroLimites(alrededor, lado) && tablero.getCasilla(alrededor).isMina()) {
						bombasAlrededor++;
					}
				}
			}
		}
		return bombasAlrededor;
	}

	private boolean isDentroLimites(Coordenada alrededor, int lado) {
		return alrededor.getPosX() >= 0 && alrededor.getPosX() < lado && alrededor.getPosY() >= 0
				&& alrededor.getPosY() < lado;
	}

	@Test
	void testTableroMarcarDesmarcarCasilla() {
		Coordenada coordenadasCasillaMarcar = new Coordenada(Utiles.dameNumero(lado), Utiles.dameNumero(lado));

		boolean primerMarcado = tablero.marcarCasilla(coordenadasCasillaMarcar);
		assertTrue(tablero.getCasilla(coordenadasCasillaMarcar).isMarcada());

		boolean segundoMarcado = tablero.marcarCasilla(coordenadasCasillaMarcar);
		assertFalse(tablero.getCasilla(coordenadasCasillaMarcar).isMarcada());

		boolean primerDesmarcado = tablero.marcarCasilla(coordenadasCasillaMarcar);
		assertTrue(tablero.getCasilla(coordenadasCasillaMarcar).isMarcada());

		boolean segundoDesmarcado = tablero.marcarCasilla(coordenadasCasillaMarcar);
		assertFalse(tablero.getCasilla(coordenadasCasillaMarcar).isMarcada());

	}

}

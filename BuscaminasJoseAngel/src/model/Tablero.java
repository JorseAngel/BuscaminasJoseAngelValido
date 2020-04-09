package model;

import utiles.Utiles;

public class Tablero {

	private Casilla[][] casillas;

	public Tablero(int lado, int numeroBombas) {
		super();
		crearTablero(lado);
		colocarMinas(lado, numeroBombas);

	}

	public boolean marcarCasilla(Coordenada coordenada) {
		Casilla casilla = getCasilla(coordenada);
		return casilla.marcar();
	}

	private boolean inRango(Coordenada coordenada) {
		return (coordenada.getPosX() >= 0 && coordenada.getPosX() < this.casillas.length)
				&& (coordenada.getPosY() >= 0 && coordenada.getPosY() < this.casillas.length);
	}

	private void colocarMinas(int lado, int numeroBombas) {
		int tamano = 2;
		int posicionesAleatorias[][] = new int[numeroBombas][tamano];
		rellenarPosicionesAleatorias(lado, posicionesAleatorias);

		for (int i = 0; i < posicionesAleatorias.length; i++) {
			for (int j = 0; j < posicionesAleatorias[i].length - 1; j++) {
				Coordenada miCoordenada = new Coordenada(posicionesAleatorias[i][j], posicionesAleatorias[i][j + 1]);

				if (getCasilla(miCoordenada).isMina()) {
					do {
						otraPosicionAleatoria(lado, posicionesAleatorias, i);
						miCoordenada = new Coordenada(posicionesAleatorias[i][j], posicionesAleatorias[i][j + 1]);
					} while (getCasilla(miCoordenada).isMina());
				}
				getCasilla(miCoordenada).setMina(true);
				establecerMinasAlrededor(miCoordenada);
			}
		}
	}

	private void establecerMinasAlrededor(Coordenada coordenadaMinaActual) {
		int incremento = 1, lado = this.casillas.length;
		int posX = coordenadaMinaActual.getPosX();
		int posY = coordenadaMinaActual.getPosY();

		for (int i = posX - 1; i <= posX + 1; i++) {
			for (int j = posY - 1; j <= posY + 1; j++) {
				Coordenada alrededor = new Coordenada(i, j);
				if (!alrededor.equals(coordenadaMinaActual) && isDentroLimites(alrededor)) {
					Casilla casillaAlrededor = getCasilla(alrededor);
					if (!casillaAlrededor.isMina()) {
						casillaAlrededor.setMinasAlrededor(casillaAlrededor.getMinasAlrededor() + incremento);
					}
				}
			}
		}

	}

	private boolean isDentroLimites(Coordenada alrededor) {
		return alrededor.getPosX() >= 0 && alrededor.getPosX() < casillas.length && alrededor.getPosY() >= 0
				&& alrededor.getPosY() < casillas.length;
	}

	private void otraPosicionAleatoria(int lado, int[][] posicionesAleatorias, int posicion) {
		int longitud = 2;
		for (int i = 0; i < longitud; i++) {
			posicionesAleatorias[posicion][i] = Utiles.dameNumero(lado);
		}
	}

	private void rellenarPosicionesAleatorias(int lado, int[][] posicionesAleatorias) {
		int aletorio = 0;

		for (int i = 0; i < posicionesAleatorias.length; i++) {
			for (int j = 0; j < posicionesAleatorias[i].length; j++) {
				posicionesAleatorias[i][j] = Utiles.dameNumero(lado);
			}
		}
	}

	private void crearTablero(int lado) {
		this.casillas = new Casilla[lado][lado];

		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas.length; j++) {
				this.casillas[i][j] = new Casilla();
			}
		}
	}

	public Casilla[][] getCasillas() {
		return casillas;
	}

	public int getNumeroMinas() {
		int numeroMinas = 0;

		for (int i = 0; i < casillas.length; i++) {
			for (int j = 0; j < casillas.length; j++) {
				if (casillas[i][j].isMina()) {
					numeroMinas++;
				}
			}
		}

		return numeroMinas;
	}

	// TODO antes era private
	public Casilla getCasilla(Coordenada posicion) {
		return casillas[posicion.getPosX()][posicion.getPosY()];
	}

	private void setMina(Coordenada posicion, boolean bandera) {
		getCasilla(posicion).setMina(bandera);
	}

	private boolean isMina(Coordenada posicion) {
		return getCasilla(posicion).isMina();
	}

	private boolean isVelada(Coordenada coordenada) {
		return getCasilla(coordenada).isVelada();
	}

	private boolean isMarcada(Coordenada coordenada) {
		return getCasilla(coordenada).isMarcada();
	}

	public void desvelarCasilla(Coordenada coordenada) {

		Casilla casillaActual = getCasilla(coordenada);
		int posX = coordenada.getPosX(), posY = coordenada.getPosY();

		desvelarCasillaVelada(coordenada, casillaActual, posX, posY);
		desvelarCasillasAlrededorDesvelada(coordenada, casillaActual, posX, posY);

	}

	private void desvelarCasillasAlrededorDesvelada(Coordenada coordenada, Casilla casillaActual, int posX, int posY) {
		if (!casillaActual.isVelada() && !casillaActual.isMarcada()) {
			int casillasBienMarcadas = 0;

			for (int i = posX - 1; i <= posX + 1; i++) {
				for (int j = posY - 1; j <= posY + 1; j++) {
					Coordenada coordenadaRecorrido = new Coordenada(i, j);
					if (isDentroLimites(coordenadaRecorrido) && !coordenada.equals(coordenadaRecorrido)
							&& getCasilla(coordenadaRecorrido).isMarcada()) {
						casillasBienMarcadas++;
					}
				}
			}

			if (casillasBienMarcadas == casillaActual.getMinasAlrededor()) {
				for (int i = posX - 1; i <= posX + 1; i++) {
					for (int j = posY - 1; j <= posY + 1; j++) {
						Coordenada coordenadaRecorrido = new Coordenada(i, j);
						if (isDentroLimites(coordenadaRecorrido) && getCasilla(coordenadaRecorrido).isVelada()
								&& !coordenada.equals(coordenadaRecorrido)) {
//							getCasilla(coordenadaRecorrido).setVelada(false);
							desvelarCasilla(coordenadaRecorrido);
						}
					}
				}
			}

		}
	}

	private void desvelarCasillaVelada(Coordenada coordenada, Casilla casillaActual, int posX, int posY) {
		if (casillaActual.isVelada() && !casillaActual.isMarcada()) {
			casillaActual.setVelada(false);

			for (int i = posX - 1; i <= posX + 1; i++) {
				for (int j = posY - 1; j <= posY + 1; j++) {
					Coordenada coordenadaRecorrido = new Coordenada(i, j);
					if (isDentroLimites(coordenadaRecorrido) && casillaActual.getMinasAlrededor() == 0
							&& !coordenada.equals(coordenadaRecorrido) && !casillaActual.isMina()) {
						desvelarCasilla(coordenadaRecorrido);
					}
				}
			}
		}
	}

	public boolean ganarPartida() {
		boolean bandera = true;

		for (int i = 0; i < getCasillas().length; i++) {
			for (int j = 0; j < getCasillas().length; j++) {
				Coordenada cordenadaActual = new Coordenada(i, j);
				if (getCasilla(cordenadaActual).isVelada() && getCasilla(cordenadaActual).isMina()
						&& !getCasilla(cordenadaActual).isMarcada()) {
					bandera = false;
				}
				
				if (getCasilla(cordenadaActual).isVelada() && !getCasilla(cordenadaActual).isMina()) {
					bandera = false;
				}
				
				if (!getCasilla(cordenadaActual).isVelada() && getCasilla(cordenadaActual).isMina()) {
					bandera = false;
				}
			}
		}

		return bandera;
	}

	public boolean perderPartida() {
		boolean bandera = false;

		for (int i = 0; i < casillas.length && !bandera; i++) {
			for (int j = 0; j < casillas.length && !bandera; j++) {
				Coordenada coordenadaActual = new Coordenada(i, j);
				if (this.getCasilla(coordenadaActual).isMina() && !this.getCasilla(coordenadaActual).isVelada()) {
					bandera = true;
				}
			}
		}

		if (bandera) {
			for (int k = 0; k < casillas.length; k++) {
				for (int h = 0; h < casillas.length; h++) {
					getCasilla(new Coordenada(k, h)).setVelada(false);
				}
			}
		}

		return bandera;
	}
}

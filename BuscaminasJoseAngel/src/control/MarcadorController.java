package control;

import vista.Botonera;
import model.Coordenada;
import model.Tablero;

public class MarcadorController {

	private Tablero tablero;

	public MarcadorController(Tablero tablero) {
		super();
		this.tablero = tablero;
	}
	public boolean marcarCasilla(String name) {
		Coordenada coordenada = Botonera.obtenCoordenada(name);
		return tablero.marcarCasilla(coordenada);
	}
}

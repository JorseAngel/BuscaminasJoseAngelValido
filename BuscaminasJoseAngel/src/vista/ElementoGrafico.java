package vista;

import javax.swing.Icon;

public class ElementoGrafico {
	private boolean ocultado;
	private boolean senalado;
	private int valor;
	private boolean mina;
	
	public ElementoGrafico(boolean mostrada, boolean senalada, int valor, boolean mina) {
		super();
		this.ocultado = mostrada;
		this.senalado = senalada;
		this.valor = valor;
		this.mina = mina;
	}
	public boolean isOcultado() {
		return ocultado;
	}
	public boolean isSenalada() {
		return senalado;
	}
	public int getValor() {
		return valor;
	}
	public boolean isMina() {
		return mina;
	}
	
	
}

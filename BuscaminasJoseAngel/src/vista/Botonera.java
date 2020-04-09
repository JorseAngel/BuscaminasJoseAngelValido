package vista;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import vista.ElementoGrafico;
import control.MarcadorController;
import control.DesveladorController;
import model.Coordenada;
import model.Tablero;

public class Botonera extends JPanel {
	DesveladorController desveladorController;
	MarcadorController marcadorController;
	MouseAdapter miMouseAdapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			JButton boton = ((JButton) e.getSource());

			if (SwingUtilities.isLeftMouseButton(e)) {
				desveladorController.desvelarCasilla(boton.getName());
			}
			if (SwingUtilities.isRightMouseButton(e)) {
				marcadorController.marcarCasilla(boton.getName());
			}
			if (getTablero().perderPartida()) {
				String valor = "MINA";
				mostrarTableroDesvelado(desveladorController.getEntornoGrafico(), valor);
			} else if (getTablero().ganarPartida()) {
				String valor = "X";
				mostrarTableroDesvelado(desveladorController.getEntornoGrafico(), valor);
			} else {
				actualizaBotonera(desveladorController.getEntornoGrafico());
			}
			// Al estar dentro de la botonera (el objeto)
		}

		private Tablero getTablero() {
			return desveladorController.getTablero();
		}
	};

	public Botonera(int lado, DesveladorController desveladorController, MarcadorController marcadorController) {
		this.marcadorController = marcadorController;
		this.desveladorController = desveladorController;
		boolean minaDesvelada = false;
		// el nombre para cuando hay mas de 10 de lado.
		// debe ser de dos digitos por coordenada aunque el valor<10
		// es decir la coordenada 6:11 debe ser 06:11, por ejemplo.
		setLayout(new GridLayout(lado, lado, 0, 0));
		for (int filas = 0; filas < lado; filas++) {
			for (int columnas = 0; columnas < lado; columnas++) {
				JButton boton = new JButton();
				// String nombre = Integer.toString(filas) + Integer.toString(columnas);
				// boton.setName(nombre);
				darNombre(boton, filas, columnas);
				add(boton);
				// Esta linea ahora usar el adapter interno
				boton.addMouseListener(miMouseAdapter);
			}
		}

	}

	private void darNombre(JButton boton, int filas, int columnas) {
		String nombre = "";
		int maximo = 10;

		if (filas >= maximo && columnas < maximo) {
			nombre = Integer.toString(filas) + "0" + Integer.toString(columnas);
		}

		if (filas < maximo && columnas >= maximo) {
			nombre = "0" + Integer.toString(filas) + Integer.toString(columnas);
		}

		if (filas < maximo && columnas < maximo || filas >= maximo && columnas >= maximo) {
			nombre = Integer.toString(filas) + Integer.toString(columnas);
		}
		boton.setName(nombre);
	}

	private void mostrarTableroDesvelado(ElementoGrafico[][] elementos, String valor) {
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			JButton boton = (JButton) components[i];
			Coordenada coordenada = obtenCoordenada(boton.getName());
			ElementoGrafico elementoGrafico = elementos[coordenada.getPosX()][coordenada.getPosY()];
			if (elementoGrafico.isMina()) {
				boton.setText(valor);
			} else {
				boton.setText(String.valueOf(elementoGrafico.getValor()));
			}
			boton.setEnabled(false);
		}
	}

	public void actualizaBotonera(ElementoGrafico[][] elementos) {
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			JButton boton = (JButton) components[i];
			Coordenada coordenada = obtenCoordenada(boton.getName());
			ElementoGrafico elementoGrafico = elementos[coordenada.getPosX()][coordenada.getPosY()];
			if (!elementoGrafico.isOcultado() && !elementoGrafico.isMina()) {
				boton.setText(String.valueOf(elementoGrafico.getValor()));
			} else if (!elementoGrafico.isOcultado() && elementoGrafico.isMina()) {
				boton.setText("MINA");
			} else if (elementoGrafico.isSenalada()) {
				boton.setText("X");
			} else {
				boton.setText("");
			}

		}
	}

	public static Coordenada obtenCoordenada(String name) {
		int pos = name.length() / 2;
		Coordenada coordenada;
		if (name.length() % 2 == 0) {
			return coordenada = new Coordenada(Integer.valueOf(name.substring(0, pos)),
					Integer.valueOf(name.substring(pos, name.length())));
		} else {
			return coordenada = null;
		}
	}

}

package app;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
	static int contadorV = 0, contadorD = 0;

	public static void main(String[] args) throws Exception {
		String Host = "localhost", entrada;
		int Puerto = 6000;
		Scanner sc = new Scanner(System.in);
		Socket Cliente = new Socket(Host, Puerto);
		DataOutputStream flujoSalida = new DataOutputStream(Cliente.getOutputStream());
		DataInputStream flujoEntrada = new DataInputStream(Cliente.getInputStream());
		Boolean partidaFinalizada = false;

		while (partidaFinalizada == false) {
			tomarJugada(sc, flujoSalida);
			entrada = flujoEntrada.readUTF();
			comprobarResultado(entrada);
			if (contadorV == 3 || contadorD == 3) {
				partidaFinalizada = true;
			}
		}
		flujoEntrada.close();
		flujoSalida.close();
		Cliente.close();
	}

	

	private static void tomarJugada(Scanner sc, DataOutputStream flujoSalida) {
		boolean tomarJugada = false, jugadaValida = false;
		String jugada;
		while (tomarJugada == false) {
			System.out.println("¿Piedra, papel o tijeras?");
			jugada = sc.nextLine();
			jugada = jugada.toUpperCase();
			jugadaValida = ComprobarJugada(jugada);
			if (jugadaValida == true) {
				try {
					flujoSalida.writeUTF(jugada);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				tomarJugada = true;
			} else {
				System.out.println("por favor introduzca una jugada válida");
			}
		}
	}
	
	private static boolean ComprobarJugada(String jugada) {
		boolean jugadaValida = false;
		if (jugada.equals("PIEDRA")) {
			jugadaValida = true;
		}
		else if (jugada.equals("PAPEL")) {
			jugadaValida = true;
		}
		else if (jugada.equals("TIJERAS")) {
			jugadaValida = true;
		}
		return jugadaValida;
	}

	private static void comprobarResultado(String entrada) {

		if (entrada.contains("V")) {
			contadorV++;
			System.out.println("Victoria, llevamos " + contadorV + " y el otro " + contadorD);
		}

		else if (entrada.contains("D")) {
			contadorD++;
			System.out.println("Derrota, llevamos " + contadorV + " y el otro " + contadorD);
		}
		else if (entrada.contains("E")) {
			System.out.println("Empate, llevamos " + contadorV + " y el otro " + contadorD);
		}

	}
}

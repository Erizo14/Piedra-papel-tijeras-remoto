package app;

import java.io.*;
import java.net.*;

public class Servidor {
	static int jugador1V = 0, jugador2V = 0, jugador1D = 0, jugador2D = 0;

	public static void main(String[] arg) throws IOException {
		int numeroPuerto = 6000;
		String mensaje1 = "", mensaje2 = "";
		ServerSocket servidor = new ServerSocket(numeroPuerto);
		Socket clienteConectado1 = null, clienteConectado2 = null;
		boolean partida = true;
		InputStream entrada1 = null, entrada2 = null;
		OutputStream salida1 = null, salida2 = null;
		
		System.out.println("Esperando al cliente.....");

		clienteConectado1 = servidor.accept();
		entrada1 = clienteConectado1.getInputStream();
		salida1 = clienteConectado1.getOutputStream();
		DataInputStream flujoEntrada1 = new DataInputStream(entrada1); 
		DataOutputStream flujoSalida1 = new DataOutputStream(salida1);
		System.out.println("Cliente 1 conectado");
		
		clienteConectado2 = servidor.accept();
		entrada2 = clienteConectado2.getInputStream();
		salida2 = clienteConectado2.getOutputStream();
		DataInputStream flujoEntrada2 = new DataInputStream(entrada2);
		DataOutputStream flujoSalida2 = new DataOutputStream(salida2);
		System.out.println("Cliente 2 conectado");
		
		while (partida) {
			mensaje1 = flujoEntrada1.readUTF();
			System.out.println("Recibiendo del CLIENTE1: \n\t" + mensaje1);
			mensaje2 = flujoEntrada2.readUTF();
			System.out.println("Recibiendo del CLIENTE2: \n\t" + mensaje2);
			jugarPartida(mensaje1, mensaje2, flujoSalida1, flujoSalida2);
			if(jugador1V == 3 || jugador2V == 3) {
				partida = false;
			}
		}

		entrada1.close();
		flujoEntrada1.close();
		salida1.close();
		flujoSalida1.close();
		clienteConectado1.close();
		entrada2.close();
		flujoEntrada2.close();
		salida2.close();
		flujoSalida2.close();
		clienteConectado2.close();
		servidor.close();
	}

	private static void jugarPartida(String mensaje1, String mensaje2, DataOutputStream flujoSalida1, DataOutputStream flujoSalida2) {
		try {
			if (mensaje1.equals("TIJERAS")) {
				if (mensaje2.equals("PAPEL")) {
					jugador1V++;
					jugador2D++;
					flujoSalida1.writeUTF("V");
					flujoSalida2.writeUTF("D");
				} else if (mensaje2.equals("PIEDRA")) {
					jugador1D++;
					jugador2V++;
					flujoSalida1.writeUTF("D");
					flujoSalida2.writeUTF("V");
				} else {
					flujoSalida1.writeUTF("E");
					flujoSalida2.writeUTF("E");
				}
			}
			if (mensaje1.equals("PAPEL")) {
				if (mensaje2.equals("TIJERAS")) {
					jugador1D++;
					jugador2V++;
					flujoSalida1.writeUTF("D");
					flujoSalida2.writeUTF("V");

				} else if (mensaje2.equals("PIEDRA")) {
					jugador1V++;
					jugador2D++;
					flujoSalida1.writeUTF("V");
					flujoSalida2.writeUTF("D");
				} else {
					flujoSalida1.writeUTF("E");
					flujoSalida2.writeUTF("E");
				}
			}
			if (mensaje1.equals("PIEDRA")) {
				if (mensaje2.equals("TIJERAS")) {
					jugador1V++;
					jugador2D++;
					flujoSalida1.writeUTF("V");
					flujoSalida2.writeUTF("D");
				} else if (mensaje2.equals("PAPEL")) {
					jugador1D++;
					jugador2V++;
					flujoSalida1.writeUTF("D");
					flujoSalida2.writeUTF("V");
				} else {
					flujoSalida1.writeUTF("E");
					flujoSalida2.writeUTF("E");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

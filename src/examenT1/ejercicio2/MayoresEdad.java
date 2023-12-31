package examenT1.ejercicio2;

import java.util.Scanner;

/**
 * Esta clase se encarga de leer una serie de líneas de la entrada estándar,
 * formadas por nombres de alumnos y su edad. Debe imprimir por salida estándar
 * únicamente aquellos que sean mayores de edad.
 * 
 */
public class MayoresEdad {

	public static void main(String[] args) {
		// Línea que contendrá los datos de un línea.
		// La voy a ir leyendo de la entrada estándar
		String linea;

		// Creo el Scanner para leer de la entrada estándar
		Scanner sc = new Scanner(System.in, "UTF-8");

		// Mientras haya líneas que leer seguiremos leyendo
		while (sc.hasNextLine()) {
			linea = sc.nextLine();
			if (Integer.parseInt(linea.split(";")[1]) >= 18) {
				System.out.println(linea.split(";")[0]);
			}
		}

		sc.close();
	}

}

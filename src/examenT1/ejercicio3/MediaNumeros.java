package examenT1.ejercicio3;

import java.util.Scanner;

public class MediaNumeros {

	public static void main(String[] args) {
		double suma = 0;
		double contador = 0;
		double numero = 0;
		Scanner sc = new Scanner(System.in);
		
		while(sc.hasNextDouble()) {
			numero = sc.nextDouble();
			suma += numero;
			contador++;
		}
		
		System.out.println(suma/contador);
		
		sc.close();
	}

}

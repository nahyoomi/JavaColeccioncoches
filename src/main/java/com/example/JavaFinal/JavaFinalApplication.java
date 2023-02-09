package com.example.JavaFinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.Scanner;


@SpringBootApplication
public class JavaFinalApplication {

	//EJERCICIO 1
	public static class Fecha {
		private int dia;
		private int mes;
		private int anio;

		// Métodos de acceso
		public int getDia() {
			return dia;
		}

		public void setDia(int dia) {
			this.dia = dia;
		}

		public int getMes() {
			return mes;
		}

		public void setMes(int mes) {
			this.mes = mes;
		}

		public int getAnio() {
			return anio;
		}

		public void setAnio(int anio) {
			this.anio = anio;
		}

		// Constructor sin parámetros
		public Fecha() {
			this.dia = 0;
			this.mes = 0;
			this.anio = 0;
		}

		// Constructor con parámetros
		public Fecha(int dia, int mes, int anio) {
			setDia(dia);
			setMes(mes);
			setAnio(anio);
		}

		// Método toString
		public String toString() {
			return dia + "/" + mes + "/" + anio;
		}

		// Método sonDatosValidos
		public static boolean sonDatosValidos(Fecha fecha) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			int dia = cal.get(Calendar.DAY_OF_MONTH);
			int mes = cal.get(Calendar.MONTH) + 1;
			int anio = cal.get(Calendar.YEAR);

			return  dia >= 1 && dia <= 31 && mes >= 1 && mes <= 12;
		}

		// Método esAnterior
		public boolean esAnterior(Fecha otra) {
			if (anio < otra.anio) {
				return true;
			} else if (anio == otra.anio) {
				if (mes < otra.mes) {
					return true;
				} else if (mes == otra.mes) {
					if (dia < otra.dia) {
						return true;
					}
				}
			}
			return false;
		}
	}

	//EJERCICIO 2

	public static class Coche {
		private Fecha fechaMatriculacion;
		private int kilometraje;
		private int identificador;

		public Coche(Fecha fechaMatriculacion, int kilometraje, int identificador) {
			this.fechaMatriculacion = fechaMatriculacion;
			this.kilometraje = kilometraje;
			this.identificador = identificador;
		}

		public String toString() {
			return fechaMatriculacion.toString() + "," + kilometraje + "," + identificador;
		}
		public int getidentificador() {
			return identificador;
		}
		public int getkilometraje() {
			return kilometraje;
		}

	}

	//EJERCICIO 3

	 static class ColeccionCoches {
		private final int MAX = 10;
		private Coche[] almacenCoches = new Coche[MAX];
		private int cuantosCoches = 0;

		public ColeccionCoches() {
			this.almacenCoches = new Coche[MAX];
			this.cuantosCoches = 0;
		}

		public String toString() {
			String result = "";
			for (int i = 0; i < cuantosCoches; i++) {
				result += almacenCoches[i].toString() + "\n" + new String(new char[50]).replace("\0", ".");
			}
			return result;
		}

		public boolean añadirCoche(Coche coche) {
			if (cuantosCoches >= MAX) {
				return false;
			}
			almacenCoches[cuantosCoches++] = coche;
			return true;
		}

		public boolean borrarCoche(int id) {
			for (int i = 0; i < cuantosCoches; i++) {
				if (almacenCoches[i].getidentificador() == id) {
					for (int j = i; j < cuantosCoches - 1; j++) {
						almacenCoches[j] = almacenCoches[j + 1];
					}
					cuantosCoches--;
					return true;
				}
			}
			return false;
		}

		public String mostrarMasAntigua() {
			Coche masAntigua = almacenCoches[0];
			for (int i = 1; i < cuantosCoches; i++) {
				if (almacenCoches[i].fechaMatriculacion.toString().compareTo(masAntigua.fechaMatriculacion.toString()) < 0) {
					masAntigua = almacenCoches[i];
				}
			}
			return masAntigua.toString();
		}

		public int mostrarPorKilometros(int min) {
			int count = 0;
			for (int i = 0; i < cuantosCoches; i++) {
				if (almacenCoches[i].getkilometraje() > min) {
					count++;
				}
			}
			return count;
		}
	}


	public static void main(String[] args) {
		SpringApplication.run(JavaFinalApplication.class, args);

		ColeccionCoches misCoches = new ColeccionCoches();
		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		while (opcion != 6) {
			System.out.println("\nMenú:");
			System.out.println("1. Mostrar coches");
			System.out.println("2. Añadir coche");
			System.out.println("3. Borrar coche");
			System.out.println("4. Consultar coche más antiguo");
			System.out.println("5. Consultar coches por kilometraje");
			System.out.println("6. Terminar");
			System.out.println("7. Recuperar");
			System.out.print("Elije una opción: ");
			opcion = sc.nextInt();

			Fecha fechaMatriculacion = null;
			switch (opcion) {
				case 1:
					System.out.println(misCoches.toString());
					break;
				case 2:
					fechaMatriculacion = null;
					do {
						System.out.print("Introduce día de matriculación: ");
						int dia = sc.nextInt();
						System.out.print("Introduce mes de matriculación: ");
						int mes = sc.nextInt();
						System.out.print("Introduce año de matriculación: ");
						int anio = sc.nextInt();
						fechaMatriculacion = new Fecha(dia, mes, anio);
					} while (!Fecha.sonDatosValidos(fechaMatriculacion));
					System.out.print("Introduce número de kilómetros: ");
					int kilometros = sc.nextInt();
					System.out.print("Introduce identificador del coche: ");
					int identificador = sc.nextInt();
					Coche nuevo = new Coche(fechaMatriculacion, kilometros, identificador);
					misCoches.añadirCoche(nuevo);
					break;

			case 3:
				System.out.print("Identificador: ");
				identificador = sc.nextInt();
				if (misCoches.borrarCoche(identificador)) {
					System.out.println("Coche borrado correctamente");
				} else {
					System.out.println("Coche no borrado");
				}
				break;
			case 4:
				Coche masAntiguo = misCoches.mostrarMasAntigua();
				System.out.println("El coche más antiguo es: " + masAntiguo.toString());
				break;
			case 5:
				System.out.print("Introduce el mínimo de kilómetros: ");
			case 6:
				System.out.println("Adiós, gracias por usar el programa");

				// Guardar los datos en un archivo de texto
				try {
					FileOutputStream fos = new FileOutputStream("datos.txt");
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					oos.writeObject(misCoches);
					oos.close();
					fos.close();
					System.out.println("Datos guardados exitosamente en el archivo datos.txt");
				} catch (IOException e) {
					System.out.println("Error al guardar los datos en el archivo datos.txt");
					e.printStackTrace();
				}
				break;


		}
		}
	}
}

// Rodrigo Piña Rodriguez - bv0124
import java.util.Scanner;

public class GestionAerolinea {
    // Precio de un asiento de la clase turista
    private final int PRECIO_BILLETE_TURISTA = 350;
    // Precio de un asiento de la clase business
    private final int PRECIO_BILLETE_BUSINESS = 1500;
    // Se aplica un 15% de descuento a los menores de 15 años (<15)
    private final float DESCUENTO_INFANTIL = 15f;
    // Número de aviones de la aerolínea
    private final int NUM_AVIONES = 3;
    // Lista de aviones de la aerolínea
    private Avion[] aviones;
    private final int NUM_VUELOS = NUM_AVIONES;
    // Lista de vuelos
    private Vuelo[] vuelos;
    // Variable que indica si se han inicializado los datos de aviones y vuelos

    public static void main(String[] args) {
        // Creamos un objeto para ejecutar el programa
        GestionAerolinea obj = new GestionAerolinea();
    }

    public GestionAerolinea() {
        // Constructor de la clase que inicia el programa
        int opcion;
        do {
            opcion = menu();
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    public void ejecutarOpcion(int opcion) {
        // Ejecuta el código asociado a la opción
        switch (opcion) {
            case 1: // Inicializar aviones y vuelos
                iniciarAvionesYVuelos();
                System.out.println("Aviones y vuelos inicializados \n");
                break;
            case 2: // Reservar asiento en un vuelo
                Vuelo vuelo = preguntarVuelo();
                Clase clase = preguntarClase();
                Avion avion = vuelo.getAvion(); // Obtener el avión asociado al vuelo seleccionado
                reservarAsiento(avion,clase);
                break;
            case 3: // Mostrar el mapa de asientos
                Vuelo vueloAsientos = preguntarVuelo();
                Avion avionAsientos = vueloAsientos.getAvion(); // Obtener el avión asociado al vuelo seleccionado
                avionAsientos.mostrarMapaDeAsientos();
                break;
            case 4: // Mostrar la lista de pasajeros
                Vuelo vueloPasajeros = preguntarVuelo();
                Avion avionPasajeros = vueloPasajeros.getAvion();
                mostrarPasajeros(avionPasajeros);
                break;
            case 5: // Mostrar pasajeros menores de 15 años
                Vuelo vueloMenores = preguntarVuelo();
                Avion avionMenores = vueloMenores.getAvion();
                mostrarPasajeros(avionMenores, 15);
                break;
            case 6: // Mostrar ingresos del vuelo
                Vuelo vueloIngresos = preguntarVuelo();
                Avion avionIngresos = vueloIngresos.getAvion();
                mostrarIngresos(avionIngresos);
                break;
            default: // Finalizar
                System.out.println("Fin de la ejecución.");
        }
    }

    public void iniciarAvionesYVuelos() {
        // Se inicializan las listas de aviones y vuelos
        aviones = new Avion[NUM_AVIONES];
        aviones[0] = new Avion("Airbus A330", 40, 120);
        aviones[1] = new Avion("Airbus A310", 20, 100);
        aviones[2] = new Avion("Airbus A350", 48, 180);
        vuelos = new Vuelo[NUM_VUELOS];
        vuelos[0] = new Vuelo("Madrid", "La Habana", "01/12/2024", aviones[0]);
        vuelos[1] = new Vuelo("Madrid", "Cancún", "01/12/2024", aviones[1]);
        vuelos[2] = new Vuelo("Madrid", "Punta Cana", "01/12/2024", aviones[2]);
    }

    public int menu() {
        // Muestra el menú de opciones
        int opcion;
        // Código
        System.out.println("1. Inicializar aviones y vuelos \n" +
                "2. Reservar asiento \n" +
                "3. Mostrar el mapa de asientos \n" +
                "4. Mostrar la lista de pasajeros \n" +
                "5. Mostrar pasajeros menores de 15 años \n" +
                "6. Mostrar ingresos del vuelo \n" +
                "0. Finalizar");
        String mensajeNumero = "introduce un numero entre 0 y 6: ";
        opcion = leerNumero(0,6, mensajeNumero);
        return opcion;
    }

    public int leerNumero(int minimo, int maximo, String mensaje) {
        Scanner teclado = new Scanner(System.in);
        int numero;
        do {
            System.out.print(mensaje);
            numero = teclado.nextInt();
        } while (numero < minimo || numero > maximo);
        return numero;
    }

    public Vuelo preguntarVuelo() {
        int numero;
        numero = leerNumero(0, 2, "Elija el vuelo (0: La Habana, 1: Cancún, 2: Punta Cana) ");
        return vuelos[numero];
    }

    public Clase preguntarClase() {
        Clase clase;
        int numero = leerNumero(0, 1, "Elija la clase (0: Business, 1: Turista) ");
        clase = numero == 0 ? Clase.BUSINESS : Clase.TURISTA;
        return clase;
    }

    public void reservarAsiento(Avion avion, Clase clase) {

            Pasajero pasajero = Azar.generaPasajero();

            Asiento asientoReservado = null;

            // Buscar el primer asiento libre en la clase especificada
            int numeroFilas = avion.getNumeroFilas(clase);
            int butacasPorFila = avion.getButacasPorFila();

            // Iterar sobre filas y butacas para encontrar el primer asiento libre
            for (int fila = 1; fila <= numeroFilas; fila++) {
                for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                    // Intentar reservar el asiento en la fila y butaca actuales
                    asientoReservado = avion.reservarAsiento(fila, butaca, clase, pasajero);
                    if (asientoReservado != null) {
                        // Si el asiento fue reservado con éxito, salir de los bucles
                        break;
                    }
                }
                if (asientoReservado != null) break;
            }

            if (asientoReservado != null) {
                System.out.println("Reservado Asiento " + asientoReservado +
                        " de clase " + clase + " al pasajero " + pasajero.getNombre());
            } else {
                System.out.println("No hay asientos libres en clase " + clase  );
            }


    }


    public void mostrarPasajeros(Avion avion) {

        System.out.println("\n Avion " + avion.getModelo()+ "\n");
        System.out.println("Lista de pasajeros en la clase Business: \n");

        int numeroFilasBusiness = avion.getNumeroFilas(Clase.BUSINESS);
        int butacasPorFila = avion.getButacasPorFila();


        for (int fila = 1; fila <= numeroFilasBusiness; fila++) {
            boolean filaConPasajeros = false; // Verifica si hay al menos un pasajero en la fila

            for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                Pasajero pasajero = avion.getPasajero(fila , butaca, Clase.BUSINESS); // Obtener pasajero en business
                if (pasajero != null) { // Si hay un pasajero en el asiento
                    if (!filaConPasajeros) { // Imprimir la fila solo una vez
                        System.out.println("Fila " + fila );
                        filaConPasajeros = true;
                    }
                    System.out.printf(" %10s %s %d años\n",
                            pasajero.getNombre(),
                            pasajero.getPasaporte(),
                            pasajero.getEdad());
                } else if (filaConPasajeros) { // Si no hay pasajero pero la fila ya tiene ocupación
                    System.out.println(" (libre)");
                }
            }
            if (filaConPasajeros) System.out.println(); // Espacio después de cada fila con pasajeros
        }

        // Mostrar pasajeros en clase TURISTA
        System.out.println("Pasajeros en clase TURISTA: \n");
        int numeroFilasTurista = avion.getNumeroFilas(Clase.TURISTA);

        for (int fila = 1; fila <= numeroFilasTurista; fila++) {
            boolean filaConPasajeros = false; // Verifica si hay al menos un pasajero en la fila

            for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                Pasajero pasajero = avion.getPasajero(fila , butaca, Clase.TURISTA); // Obtener pasajero en business
                if (pasajero != null) { // Si hay un pasajero en el asiento
                    if (!filaConPasajeros) { // Imprimir la fila solo una vez
                        System.out.println("Fila " + fila );
                        filaConPasajeros = true;
                    }
                    System.out.printf("%10s %5s %d años\n",
                            pasajero.getNombre(),
                            pasajero.getPasaporte(),
                            pasajero.getEdad());
                } else if (filaConPasajeros) { // Si no hay pasajero pero la fila ya tiene ocupación
                    System.out.println(" (libre)");
                }
            }
            if (filaConPasajeros) System.out.println(); // Espacio después de cada fila con pasajeros
        }


    }

    public void mostrarPasajeros(Avion avion, int edad) {
        // Código
        System.out.println("\n Avion " + avion.getModelo()+ "\n");
        System.out.println("Lista de pasajeros menores de 15 años en la clase Business:");

        int numeroFilasBusiness = avion.getNumeroFilas(Clase.BUSINESS);
        int butacasPorFila = avion.getButacasPorFila();

        for (int fila = 1; fila <= numeroFilasBusiness; fila++) {
            for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                Pasajero pasajero = avion.getPasajero(fila , butaca, Clase.BUSINESS); // Obtener pasajero en business
                if (pasajero != null  && pasajero.getEdad() < edad) { // Si hay un pasajero en el asiento
                    System.out.printf(" %10s %d años\n",
                            pasajero.getNombre(),
                            pasajero.getEdad());
                }
            }
        }

        // Mostrar pasajeros en clase TURISTA
        System.out.println("Lista de pasajeros menores de 15 años en clase TURISTA:");
        int numeroFilasTurista = avion.getNumeroFilas(Clase.TURISTA);

        for (int fila = 1; fila <= numeroFilasTurista; fila++) {
            for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                Pasajero pasajero = avion.getPasajero(fila , butaca, Clase.TURISTA); // Obtener pasajero en business
                if (pasajero != null && pasajero.getEdad() < edad ) { // Si hay un pasajero en el asiento
                    System.out.printf("%10s %d años\n",
                            pasajero.getNombre(),
                            pasajero.getEdad());
                }
            }
        }
        System.out.println();
    }

    public void mostrarIngresos(Avion avion) {
        // Código
        int menorB = 0;
        int menorT = 0;
        int adultoB = 0;
        int adultoT = 0;

        int numeroFilasBusiness = avion.getNumeroFilas(Clase.BUSINESS);
        int butacasPorFila = avion.getButacasPorFila();

        for (int fila = 1; fila <= numeroFilasBusiness; fila++) {
            for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                Pasajero pasajero = avion.getPasajero(fila , butaca, Clase.BUSINESS); // Obtener pasajero en business
                if (pasajero != null  && pasajero.getEdad() < 15) { // Si hay un pasajero en el asiento
                    menorB++;
                }
                else {
                    adultoB++;
                }
            }
        }

        int numeroFilasTurista = avion.getNumeroFilas(Clase.TURISTA);
        for (int fila = 1; fila <= numeroFilasTurista; fila++) {
            for (int butaca = 1; butaca <= butacasPorFila; butaca++) {
                Pasajero pasajero = avion.getPasajero(fila , butaca, Clase.TURISTA); // Obtener pasajero en business
                if (pasajero != null  && pasajero.getEdad() < 15) { // Si hay un pasajero en el asiento
                    menorT++;
                }
                else {
                    adultoT++;
                }
            }
        }

        int cantidad = (int) ((adultoT * PRECIO_BILLETE_TURISTA) + (adultoB * PRECIO_BILLETE_BUSINESS) + (menorB *
                        (PRECIO_BILLETE_BUSINESS * DESCUENTO_INFANTIL) + (menorT * (PRECIO_BILLETE_TURISTA * DESCUENTO_INFANTIL) ) ));

        System.out.println("El avion " + avion.getModelo() + " proporciona ingresos de " + cantidad + " €\n" );
    }
}

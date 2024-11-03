//Hugo Sánchez del Campo bv0253
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
                this.iniciarAvionesYVuelos();
                System.out.println("Aviones y vuelos inicializados.");
                break;
            case 2: // Reservar asiento en un vuelo
                if (aviones != null) {
                    Vuelo vuelo = this.preguntarVuelo();
                    Clase clase = this.preguntarClase();
                    this.reservarAsiento(vuelo.getAvion(), clase);
                }   else {
                    System.out.println("Aviones y vuelos no inicializados");
                }
                break;
            case 3: // Mostrar el mapa de asientos
                if(aviones != null){
                    Vuelo vuelo = this.preguntarVuelo();
                    vuelo.getAvion().mostrarMapaDeAsientos();
                }   else {
                    System.out.println("Aviones y vuelos no inicializados");
                }
                break;
            case 4: // Mostrar la lista de pasajeros
                if(aviones != null){
                    Vuelo vuelo = preguntarVuelo();
                    mostrarPasajeros(vuelo.getAvion());
                }   else {
                    System.out.println("Aviones y vuelos no inicializados");
                }

                break;
            case 5: // Mostrar pasajeros menores de 15 años
                if(aviones != null){
                    Vuelo vuelo = preguntarVuelo();
                    mostrarPasajeros(vuelo.getAvion(),15);
                }   else{
                    System.out.println("Aviones y vuelos no inicializados.");
                }
                break;
            case 6: // Mostrar ingresos del vuelo
                if(aviones != null){
                    Vuelo vuelo = preguntarVuelo();
                    mostrarIngresos(vuelo.getAvion());
                }   else{
                    System.out.println("Aviones y vuelos no inicializados.");
                }
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
        System.out.println("1.Inicializar aviones y vuelos.\n" +
                "2.Reservar asiento de un vuelo.\n" +
                "3.Mostrar el mapa de asientos.\n" +
                "4.Mostrar lista de pasajeros.\n" +
                "5.Mostrar pasajeros menores de 15 años.\n" +
                "6.Mostrar ingresos del vuelo.\n" +
                "0.Finalizar.\n");
        opcion = leerNumero(0,6,"Introduce un número entre 0 y 6: ");
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
        int fila = 1;
        int butaca = 1;
        boolean encontrado = false;
        Pasajero pasajero = Azar.generaPasajero();
        Asiento asiento = null;
        while (fila <= avion.getNumeroFilas(clase)  && !encontrado) {
            while (butaca <= avion.getButacasPorFila()  && !encontrado) {
                asiento = avion.reservarAsiento(fila, butaca, clase, pasajero);
                if (asiento != null) {
                    encontrado = true;
                }
                butaca++;
            }
            fila++;
            butaca = 1;
        }
            if (encontrado) {
                System.out.println("Reservado el asiento " +  asiento.toString() + " de clase " + clase + " al pasajero " + pasajero.getNombre() + ".");
            } else {
                System.out.println("No hay asientos libres en clase " + clase);
            }

    }


    public void mostrarPasajeros(Avion avion) {
        System.out.println("Avión " + avion.getModelo());
        System.out.println("Lista de pasajeros en clase Business:");
        for (int fila= 1; fila <= avion.getNumeroFilas(Clase.BUSINESS); fila++){
            System.out.println("Fila " + fila);
            for (int butaca = 1; butaca <= avion.getButacasPorFila(); butaca++){
                Pasajero pasajero = avion.getPasajero(fila, butaca, Clase.BUSINESS);

                if (pasajero != null){
                    System.out.printf("\t\t %-30s %18s %8d años\n",pasajero.getNombre(),pasajero.getPasaporte(),pasajero.getEdad() );
                }   else {
                    System.out.println("\t\t (Libre) ");
                }
            }
        }
        System.out.println("Lista de pasajeros de la clase Turista:");
        for (int fila= 1; fila <= avion.getNumeroFilas(Clase.TURISTA); fila++){
            System.out.println("Fila " + fila);
            for (int butaca = 1; butaca <= avion.getButacasPorFila(); butaca++) {
                Pasajero pasajero = avion.getPasajero(fila, butaca, Clase.TURISTA);
                if (pasajero != null) {
                    System.out.printf("\t\t %-30s %18s %8d años\n",pasajero.getNombre(),pasajero.getPasaporte(),pasajero.getEdad() );
                } else {
                    System.out.println("\t\t (Libre) ");
                }
            }
        }
    }

    public void mostrarPasajeros(Avion avion, int edad) {
        System.out.println("Avión " + avion.getModelo());
        System.out.println("Lista de pasajeros menores de 15 años de la clase Business: ");
        for (int fila= 1; fila <= avion.getNumeroFilas(Clase.BUSINESS); fila++){
            for (int butaca = 1; butaca <= avion.getButacasPorFila(); butaca++){
                Pasajero pasajero = avion.getPasajero(fila, butaca, Clase.BUSINESS);
                if (pasajero != null && pasajero.getEdad() <= edad){
                    System.out.printf("\t\t %-30s %8d años\n",pasajero.getNombre(),pasajero.getEdad());
                }
            }
        }
        System.out.println("Lista de pasajeros menores de 15 años de la clase Turista: ");
        for (int fila= 1; fila <= avion.getNumeroFilas(Clase.TURISTA); fila++){
            for (int butaca = 1; butaca <= avion.getButacasPorFila(); butaca++){
                Pasajero pasajero = avion.getPasajero(fila,butaca, Clase.TURISTA);
                if (pasajero != null && pasajero.getEdad() <= edad){
                    System.out.printf("\t\t %-30s %8d años\n",pasajero.getNombre(),pasajero.getEdad());
                }
            }
        }
    }

    public void mostrarIngresos(Avion avion) {
        double ingresosTotales = 0;
        for (int fila = 1; fila <= avion.getNumeroFilas(Clase.BUSINESS); fila++) {
            for (int butaca = 1; butaca <= avion.getButacasPorFila(); butaca++) {
                Pasajero pasajero = avion.getPasajero(fila, butaca, Clase.BUSINESS);
                if (pasajero != null && pasajero.getEdad() <= 15) {
                    ingresosTotales += PRECIO_BILLETE_BUSINESS - DESCUENTO_INFANTIL;
                } else if (pasajero != null) {
                    ingresosTotales += PRECIO_BILLETE_BUSINESS;
                }
            }
        }
        for (int fila = 1; fila <= avion.getNumeroFilas(Clase.TURISTA); fila++) {
            for (int butaca = 1; butaca <= avion.getButacasPorFila(); butaca++) {
                Pasajero pasajero = avion.getPasajero(fila, butaca, Clase.TURISTA);
                if (pasajero != null && pasajero.getEdad() <= 15) {
                    ingresosTotales += PRECIO_BILLETE_TURISTA - DESCUENTO_INFANTIL;
                } else if (pasajero != null) {
                    ingresosTotales += PRECIO_BILLETE_TURISTA;
                }
            }
        }
        System.out.println("El avión " + avion.getModelo() + " proporciona ingresos de " + ingresosTotales + "€");
    }
    }


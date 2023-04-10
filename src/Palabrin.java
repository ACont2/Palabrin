import java.util.Objects;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;


/**
 * @author Antonio Constantin y Alberto Criado
 * @version demasiadas
 * Palabrin
 */
public class Palabrin {
    static Scanner sc = new Scanner(System.in);

    public static final String ANSI_VOLVER_A_BLANCO = "\u001B[0m";
    public static final String ANSI_VERDE = "\u001B[32m";
    public static final String ANSI_AMARILLO = "\u001B[33m";
    static StringBuilder sb = new StringBuilder();
    public static boolean acierto = false;
    public static int i = 0;
    
    public static String[] respuestas = new String[10];


    public static void main(String[] args) {

        String[] diccionario = obtenerDiccionario();
        String palabraSecreta = diccionario[(int) (Math.random() * 73251)].toUpperCase();
        //Se elige la palabra secreta del array

        estructura(palabraSecreta);

        do {
            String intento = leerPalabra(diccionario);

            respuestas[i] = colores(palabraSecreta, intento);

            for (String respuesta : respuestas) {
                System.out.println(respuesta);
                // te imprime la estructura con los intentos
            }

            i++;

            if (Objects.equals(intento, "POKE")) {
                System.out.println("la letra como pista es " + palabraSecreta.charAt((int) (Math.random() * palabraSecreta.length())));
                //al introducir poke, se imprime una letra aleatoria de la palabra secreta
                i--;
                //al introducir poke, el intento no cuenta.
            }

        } while (i < respuestas.length && !acierto);

        System.out.println("la palabra era " + palabraSecreta);

        puntuacion(acierto, i);

    }

    /**
     * Te pinta la estructura del juego nada mas empezar
     * @param palabraSecreta La palabra que hay que adivinar
     */
    private static void estructura(String palabraSecreta) {
        for (int i = 0; i < respuestas.length; i++) {
            respuestas[i] = "-".repeat(palabraSecreta.length());
            // Se rellena el array de respuestas con barras
            System.out.println(respuestas[i]);
            //Se imprimen
        }
    }

    /**
     * Te pone las letras de blanco, naranja o verde
     * @param palabraSecreta La palabra que hay que adivinar
     * @param intento el intento que das
     * @return Intento de color
     */
    private static String colores(String palabraSecreta, String intento) {
        String intentoColor = "";

        if (!Objects.equals(intento, "POKE")) { // Si el intento no es poke, se realiza lo de abajo.

            for (int j = 0; j < palabraSecreta.length(); j++) {
                char c = intento.charAt(j);
                if (palabraSecreta.charAt(j) == c) {
                    sb.append(ANSI_VERDE).append(c).append(ANSI_VOLVER_A_BLANCO); // letra contiene la solución
                } else if (palabraSecreta.contains(Character.toString(c))) {
                    sb.append(ANSI_AMARILLO).append(c).append(ANSI_VOLVER_A_BLANCO);// letra está en la palabra
                } else {
                    sb.append(c);
                }

                if (intento.equals(palabraSecreta)) {
                    acierto = true;
                }
            }

            intentoColor = sb.toString();

            sb = new StringBuilder();

        }

        return intentoColor;
    }

    /**
     * Con el numero de intentos te imprime los puntos que has ganado
     * @param acierto si has acertado la palabra o no
     * @param i contador de intentos
     */
    public static void puntuacion(boolean acierto, int i) {
        if (!acierto) {
            System.out.println("0 puntos");
        } else {
            switch (i) {
                case 1:
                    System.out.println("has ganado, toma 1000 puntos");
                    break;
                case 2:
                    System.out.println("has ganado, toma 900 puntos");
                    break;
                case 3:
                    System.out.println("has ganado, toma 800 puntos");
                    break;
                case 4:
                    System.out.println("has ganado, toma 700 puntos");
                    break;
                case 5:
                    System.out.println("has ganado, toma 600 puntos");
                    break;
                case 6:
                    System.out.println("has ganado, toma 500 puntos");
                    break;
                case 7:
                    System.out.println("has ganado, toma 400 puntos");
                    break;
                case 8:
                    System.out.println("has ganado, toma 300 puntos");
                    break;
                case 9:
                    System.out.println("has ganado, toma 200 puntos");
                    break;
                case 10:
                    System.out.println("has ganado, toma 100 puntos");
                    break;
            } //En base a los intentos, se consigue una puntuacion establecida
        }
    }

    /**
     * Te introduce una serie de palabras en un array
     * @return el array con todas las palabras posibles que puedes usar
     */
    public static String[] obtenerDiccionario() {
        Scanner leerFichero = null;
        // array de Strints para cargar palabras de 8 caracteres
        String[] diccionario = new String[73253];

        // Utilizaremos un Scanner para cargar el fichero txt
        int i = 0;

        try {
            // Cargamos el fichero txt guardado en la carpeta del proyecto
            leerFichero = new Scanner(new FileReader("palabras.txt"));
            String str;

            //repetir hasta terminar de leer el fichero
            while (leerFichero.hasNext()) {
                str = leerFichero.next();

                // añadir palabra al diccionario
                diccionario[i] = str;
                i++;
            }


        } catch (FileNotFoundException e) {
            // asegurarse que la ruta y el nombre del fichero son correctos
            System.err.println("Fichero no encontrado");
        }
        if (leerFichero != null) {
            leerFichero.close();
        }
        return diccionario;
    }

    /**
     * Te lee la palabra que le introduces por el teclado y te la desvuelve en mayusculas y sin espacios en blanco
     * @param diccionario el array de palabras válidas
     * @return la palabra que le introduces
     */
    public static String leerPalabra(String[] diccionario) {
        String intento;

        do {
            intento = sc.nextLine().toUpperCase().trim(); //Elimina espacios y lo convierte en mayusculas.

        } while (!palabraValida(diccionario, intento));

        return intento;
    }

    /**
     * Mirando el diccionario y la palabra que le introduces te mira si está correcta, en caso de no ser asi te lo dice
     * @param diccionario el array de palabras válidas
     * @param intento el intento que das
     * @return la validez que la palabra está en el diccionario
     */
    public static boolean palabraValida(String[] diccionario, String intento) {
        boolean valida = false;

        if (intento.equals("POKE")) { //Si es poke, hace un bypass
            return true;
        } else if (Arrays.binarySearch(diccionario, intento.toLowerCase()) < 0) { //Te busca en el array diccionario la palabra
            System.err.println("La palabra " + intento + " no se encuentra en el diccionario");
        } else { //Si la palabra introducida esta en el diccionario, la devuelve como valida
           valida = true;
        }
        return valida;
    }
}
package app;

import java.util.Scanner;

import modelo.Nodo;
import negocio.ArbolBinario;

/**
 * Programa principal: menu interactivo para construir y analizar
 * el arbol de dependencias universitarias.
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final ArbolBinario<String> arbol = new ArbolBinario<>();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println();
            switch (opcion) {
                case 1 -> crearArbolInicial();
                case 2 -> arbol.mostrarArbol();
                case 3 -> System.out.println("Numero de nodos: " + arbol.contarNodos());
                case 4 -> System.out.println("Numero de hojas: " + arbol.contarHojas());
                case 5 -> System.out.println("Altura del arbol (aristas): " + arbol.altura());
                case 6 -> consultarGrado();
                case 7 -> verificarHoja();
                case 8 -> System.out.println("Saliendo del programa...");
                case 9 -> ejecutarCasosDePrueba();
                default -> System.out.println("Opcion no valida. Intente de nuevo.");
            }
        } while (opcion != 8);
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== ARBOL DE DEPENDENCIAS UNIVERSITARIAS =====");
        System.out.println("1. Crear el arbol inicial");
        System.out.println("2. Visualizar el arbol");
        System.out.println("3. Consultar numero de nodos");
        System.out.println("4. Consultar numero de hojas");
        System.out.println("5. Consultar la altura");
        System.out.println("6. Consultar el grado de un nodo");
        System.out.println("7. Verificar si un nodo es hoja");
        System.out.println("8. Salir");
        System.out.println("9. Ejecutar casos de prueba");
    }

    // ===================== OPCIONES DEL MENU =====================

    private static void crearArbolInicial() {
        construirArbolInicial(arbol);
        System.out.println("Arbol inicial creado correctamente:");
        arbol.mostrarArbol();
    }

    /** Construye el arbol del enunciado (9 nodos, 5 hojas, altura 3). */
    private static void construirArbolInicial(ArbolBinario<String> a) {
        a.vaciar();
        Nodo<String> universidad = a.crearRaiz("UNIVERSIDAD");

        Nodo<String> fisei = a.agregarIzquierdo(universidad, "FISEI");
        Nodo<String> dide = a.agregarDerecho(universidad, "DIDE");

        Nodo<String> software = a.agregarIzquierdo(fisei, "SOFTWARE");
        a.agregarDerecho(fisei, "TI");

        a.agregarIzquierdo(dide, "INVESTIGACION");
        a.agregarDerecho(dide, "VINCULACION");

        a.agregarIzquierdo(software, "NIVEL1");
        a.agregarDerecho(software, "NIVEL3");
    }

    private static void consultarGrado() {
        Nodo<String> nodo = pedirNodo();
        if (nodo != null) {
            System.out.println("Grado de " + nodo.getDato() + ": " + nodo.grado());
        }
    }

    private static void verificarHoja() {
        Nodo<String> nodo = pedirNodo();
        if (nodo != null) {
            if (nodo.esHoja()) {
                System.out.println(nodo.getDato() + " SI es una hoja.");
            } else {
                System.out.println(nodo.getDato() + " NO es una hoja (tiene " + nodo.grado() + " hijo/s).");
            }
        }
    }

    /** Pide el nombre de un nodo y lo busca en el arbol. */
    private static Nodo<String> pedirNodo() {
        if (arbol.esVacio()) {
            System.out.println("El arbol esta vacio. Primero cree el arbol (opcion 1).");
            return null;
        }
        System.out.print("Ingrese el nombre del nodo: ");
        String nombre = sc.nextLine();
        Nodo<String> nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("No existe un nodo llamado '" + nombre.trim() + "'.");
        }
        return nodo;
    }

    // ===================== CASOS DE PRUEBA =====================

    private static void ejecutarCasosDePrueba() {
        System.out.println("========== CASOS DE PRUEBA ==========");

        // 1. Arbol vacio
        System.out.println("\n[Caso 1] Arbol vacio");
        ArbolBinario<String> vacio = new ArbolBinario<>();
        verificar("esVacio()", vacio.esVacio(), true);
        verificar("Nodos", vacio.contarNodos(), 0);
        verificar("Altura", vacio.altura(), -1);

        // 2. Arbol con un solo nodo
        System.out.println("\n[Caso 2] Arbol con un solo nodo");
        ArbolBinario<String> unico = new ArbolBinario<>();
        unico.crearRaiz("UNIVERSIDAD");
        unico.mostrarArbol();
        verificar("Nodos", unico.contarNodos(), 1);
        verificar("Hojas", unico.contarHojas(), 1);
        verificar("Altura", unico.altura(), 0);

        // 3. Arbol inicial
        System.out.println("\n[Caso 3] Arbol inicial");
        ArbolBinario<String> inicial = new ArbolBinario<>();
        construirArbolInicial(inicial);
        inicial.mostrarArbol();
        verificar("Nodos", inicial.contarNodos(), 9);
        verificar("Hojas", inicial.contarHojas(), 5);
        verificar("Altura", inicial.altura(), 3);

        // 4. Insertar en una posicion ocupada
        System.out.println("\n[Caso 4] Insertar un hijo en una posicion ocupada");
        try {
            System.out.println("Intentando agregar 'NIVEL2' como hijo izquierdo de SOFTWARE...");
            inicial.agregarIzquierdo(inicial.buscar("SOFTWARE"), "NIVEL2");
            System.out.println("  [FALLO] Se permitio insertar en una posicion ocupada");
        } catch (IllegalStateException e) {
            System.out.println("  [OK] Insercion rechazada -> " + e.getMessage());
        }
        verificar("Nodos despues del intento", inicial.contarNodos(), 9);

        // 5. Grado de FISEI y SOFTWARE
        System.out.println("\n[Caso 5] Grado de FISEI y SOFTWARE");
        verificar("Grado de FISEI", inicial.buscar("FISEI").grado(), 2);
        verificar("Grado de SOFTWARE", inicial.buscar("SOFTWARE").grado(), 2);

        System.out.println("\n========== FIN DE LOS CASOS DE PRUEBA ==========");
    }

    private static void verificar(String descripcion, Object obtenido, Object esperado) {
        String estado = obtenido.equals(esperado) ? "[OK]   " : "[FALLO]";
        System.out.println("  " + estado + " " + descripcion + ": obtenido = " + obtenido
                + " | esperado = " + esperado);
    }

    // ===================== UTILIDADES =====================

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {
                return 8; // fin de la entrada: salir
            }
            String linea = sc.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe ingresar un numero entero.");
            }
        }
    }
}

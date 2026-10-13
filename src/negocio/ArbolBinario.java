package negocio;

import modelo.Nodo;

/**
 * Arbol binario generico construido con nodos enlazados.
 * Las operaciones de conteo y altura se implementan con recursividad.
 *
 * @param <T> tipo de dato almacenado en los nodos
 */
public class ArbolBinario<T> {

    private Nodo<T> raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    public Nodo<T> getRaiz() {
        return raiz;
    }

    /** Verifica si el arbol esta vacio (no tiene raiz). */
    public boolean esVacio() {
        return raiz == null;
    }

    /** Elimina todos los nodos dejando el arbol vacio. */
    public void vaciar() {
        raiz = null;
    }

    // ===================== INSERCION =====================

    /**
     * Crea el nodo raiz. Si el arbol ya tiene raiz se lanza una excepcion.
     */
    public Nodo<T> crearRaiz(T dato) {
        if (raiz != null) {
            throw new IllegalStateException("El arbol ya tiene raiz: " + raiz.getDato());
        }
        raiz = new Nodo<>(dato);
        return raiz;
    }

    /**
     * Agrega un hijo izquierdo al nodo padre.
     * Si la posicion ya esta ocupada se lanza una excepcion.
     */
    public Nodo<T> agregarIzquierdo(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getIzquierdo() != null) {
            throw new IllegalStateException("Posicion ocupada: " + padre.getDato()
                    + " ya tiene hijo izquierdo (" + padre.getIzquierdo().getDato() + ")");
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setIzquierdo(nuevo);
        return nuevo;
    }

    /**
     * Agrega un hijo derecho al nodo padre.
     * Si la posicion ya esta ocupada se lanza una excepcion.
     */
    public Nodo<T> agregarDerecho(Nodo<T> padre, T dato) {
        validarPadre(padre);
        if (padre.getDerecho() != null) {
            throw new IllegalStateException("Posicion ocupada: " + padre.getDato()
                    + " ya tiene hijo derecho (" + padre.getDerecho().getDato() + ")");
        }
        Nodo<T> nuevo = new Nodo<>(dato);
        padre.setDerecho(nuevo);
        return nuevo;
    }

    private void validarPadre(Nodo<T> padre) {
        if (padre == null) {
            throw new IllegalArgumentException("El nodo padre no existe (es null)");
        }
    }

    // ===================== CONSULTAS RECURSIVAS =====================

    /** Devuelve el numero total de nodos del arbol. */
    public int contarNodos() {
        return contarNodos(raiz);
    }

    // Caso base: un subarbol vacio tiene 0 nodos.
    // Caso recursivo: 1 (el nodo actual) + nodos de la izquierda + nodos de la derecha.
    private int contarNodos(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodos(nodo.getIzquierdo()) + contarNodos(nodo.getDerecho());
    }

    /** Devuelve el numero de hojas (nodos sin hijos). */
    public int contarHojas() {
        return contarHojas(raiz);
    }

    private int contarHojas(Nodo<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.esHoja()) {
            return 1;
        }
        return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
    }

    /**
     * Altura del arbol medida en aristas.
     * Arbol vacio = -1, arbol con un solo nodo = 0.
     */
    public int altura() {
        return altura(raiz);
    }

    // Caso base: subarbol vacio = -1, asi una hoja queda con 1 + max(-1, -1) = 0.
    private int altura(Nodo<T> nodo) {
        if (nodo == null) {
            return -1;
        }
        return 1 + Math.max(altura(nodo.getIzquierdo()), altura(nodo.getDerecho()));
    }

    /**
     * Busca un nodo por su dato (comparando el texto, sin distinguir mayusculas).
     * Devuelve null si no lo encuentra.
     */
    public Nodo<T> buscar(String texto) {
        return buscar(raiz, texto);
    }

    private Nodo<T> buscar(Nodo<T> nodo, String texto) {
        if (nodo == null) {
            return null;
        }
        if (String.valueOf(nodo.getDato()).equalsIgnoreCase(texto.trim())) {
            return nodo;
        }
        Nodo<T> encontrado = buscar(nodo.getIzquierdo(), texto);
        if (encontrado != null) {
            return encontrado;
        }
        return buscar(nodo.getDerecho(), texto);
    }

    // ===================== VISUALIZACION =====================

    /**
     * Muestra el arbol en consola con forma jerarquica.
     * (I) = hijo izquierdo, (D) = hijo derecho.
     */
    public void mostrarArbol() {
        if (esVacio()) {
            System.out.println("(arbol vacio)");
            return;
        }
        System.out.println(raiz.getDato());
        mostrarHijos(raiz, "");
    }

    private void mostrarHijos(Nodo<T> nodo, String prefijo) {
        if (nodo.getIzquierdo() != null) {
            boolean esUltimo = (nodo.getDerecho() == null);
            imprimirRama(nodo.getIzquierdo(), prefijo, esUltimo, "I");
        }
        if (nodo.getDerecho() != null) {
            imprimirRama(nodo.getDerecho(), prefijo, true, "D");
        }
    }

    private void imprimirRama(Nodo<T> hijo, String prefijo, boolean esUltimo, String lado) {
        System.out.println(prefijo + (esUltimo ? "\\-- " : "|-- ") + "(" + lado + ") " + hijo.getDato());
        mostrarHijos(hijo, prefijo + (esUltimo ? "    " : "|   "));
    }
}

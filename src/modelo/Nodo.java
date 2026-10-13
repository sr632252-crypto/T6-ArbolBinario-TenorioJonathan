package modelo;

/**
 * Nodo generico de un arbol binario.
 * Cada nodo guarda un dato y, como maximo, dos referencias a sus hijos.
 *
 * @param <T> tipo de dato almacenado en el nodo
 */
public class Nodo<T> {

    private T dato;
    private Nodo<T> izquierdo;
    private Nodo<T> derecho;

    public Nodo(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    /**
     * Un nodo es hoja cuando no tiene ningun hijo.
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }

    /**
     * Grado del nodo = numero de hijos que tiene (0, 1 o 2).
     */
    public int grado() {
        int contador = 0;
        if (izquierdo != null) {
            contador++;
        }
        if (derecho != null) {
            contador++;
        }
        return contador;
    }

    // ----------------- Getters y setters -----------------

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(Nodo<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public Nodo<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(Nodo<T> derecho) {
        this.derecho = derecho;
    }

    @Override
    public String toString() {
        return String.valueOf(dato);
    }
}

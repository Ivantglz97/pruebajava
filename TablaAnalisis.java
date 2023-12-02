public class TablaAnalisis {
    
    // Método para ver si M[X, a] = X
    public static String validar(String stringX, String stringA, String[][] tabla) {
        int fila = 0;
        int columna = 0;
        for (int i = 0; i < tabla.length; i++) {
            if (tabla[i][0].hashCode() == stringX.hashCode()) {
                fila = i;
                break;
            }
        }
        for (int j = 0; j < tabla[0].length; j++) {
            if (tabla[0][j].hashCode() == stringA.hashCode()) {
                columna = j;
                break;
            }
        }
        return tabla[fila][columna];
    }

    public static boolean esTerminal(String stringX, String[][] tabla) {
        for (int i = 0; i < tabla[0].length; i++) { 
            if (tabla[0][i].hashCode() == stringX.hashCode()) {
                return true;
            }
        }
        return false;
    }

}
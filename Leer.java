import java.util.ArrayList;

public class Leer {
    public static ArrayList<String> aArraylist(String produccion) {
        ArrayList<String> palabras = new ArrayList<>();
        String palabra = "";
        for (int i = 5; i < produccion.length(); i++) {
            if (i == 5 && produccion.charAt(i) == 32) {
                continue;
            } else {
                if (produccion.charAt(i) != 32) {
                    palabra += produccion.charAt(i);
                } else {
                    palabras.add(palabra);
                    palabra = "";
                }
            }
        }
        palabras.add(palabra);
        return palabras;
    }
}
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ASDI implements Parser {

    private boolean hayErrores = false; // 0 , 1 false =0 
    private final List<Token> tokens; /*Una lista de tokens que se pasa al constructor, los tokens se
    ya debieron ser procesados antes para mandarl a llamar la lista unicamente*/
    ArrayList<String> produccionArrayList = new ArrayList<>();
    /*Una lista de cadenas que se utiliza 
    para almacenar las cadenas*/
    String produccion,stringX,stringA; /*Cadenas que guardan informacion y tokens*/
    Token A; //Objeto tipo Token para guardar elementos del token 
    Stack<String> pila = new Stack<>(); //una pila 

    public ASDI(List<Token> tokens) {
        this.tokens = tokens;   /*Constructor para la lista de tokens*/
    }

    public boolean parse() {
        String[][] tabla = {
                { "",   "select",               "from",     "distinct",         "*",        ",",            "id",           ".",            "$" },
                { "Q",  "Q -> select D from T", "",         "",                 "",         "",             "",             "",             "" },
                { "D",  "",                     "",         "D -> distinct P",  "D -> P",   "",             "D -> P",       "",             "" },
                { "P",  "",                     "",         "",                 "P -> *",   "",             "P -> A",       "",             "" },
                { "A",  "",                     "",         "",                 "",         "",             "A -> A2 A1",   "",             "" },
                { "A1", "",                     "A1 -> E",  "",                 "",         "A1 -> , A",    "",             "",             "" },
                { "A2", "",                     "",         "",                 "",         "",             "A2 -> id A3",  "",             "" },
                { "A3", "",                     "A3 -> E",  "",                 "",         "A3 -> E",      "",             "A3 -> . id",   "" },
                { "T",  "",                     "",         "",                 "",         "",             "T -> T2 T1",   "",             "" },
                { "T1", "",                     "",         "",                 "",         "T1 -> , T",    "",             "",             "T1 -> E" },
                { "T2", "",                     "",         "",                 "",         "",             "T2 -> id T3",  "",             "" },
                { "T3", "",                     "",         "",                 "",         "T3 -> E",      "T3 -> id",     "",             "T3 -> E" }
        };

        analizar(tokens, tabla); /* Analiza los tokens con la tabla */ 
        /*Tipo
         * Lexema 
         * Literal
         */
        if (A.tipo == TipoToken.EOF && !hayErrores) { //si el tipo del token ==EOF y no hay errores
            System.out.println("Consulta correcta");
            return true;
        } else {
            System.out.println("Se encontraron errores");
        }
        return false;
    }

    private void analizar(List<Token> tokens, String[][] tabla) {
        pila.clear(); //limpia la pila
        int ip = 0;
        pila.push("$"); /*inicializa la pila*/
        pila.push("Q");  

        stringX = pila.lastElement(); //el siguiente elemento despues de Q se almacena en stringX
        A = tokens.get(ip); //para obtener el elmento en la posicion pi, recorre tokenscon el objeto tipo token
        stringA = A.lexema; //el lexema, el lexema se guarda en la cadena, osea las palabras select, los if, simbolos
        //caracteres, etc. Se utiliza para arbol?

        while (!stringX.equals("$")) { //stringX es la cadena que ingresamos esta inicializada en la posicion 0
            /*Para la funcion hashCode necesitamos sobreescribir el equals, por lo que se utiliza un bucle para que lo 
             * reescriba cada vez que termine de analizarlo, cuando la cadena que nosotros estamos analizando es diferente 
             * de cadena vacia
             * mientras stringX sea diferente a $-------------palomita
             */
            if(A.tipo == TipoToken.IDENTIFICADOR){ 
                /* si token0.tipo == identificador ---------- palomita
                En este momento tenemos guardado token.get(0) */
                /*primer recorrido
                 * ip=0
                 * ---------pila
                 * Q-> select ->posicion 1
                 * $ -> posicion 0
                 * ---------fin de pila
                 stringX=Q-> la posicion 1
                 A=token.get(0) -> posicion en 0 el primer token
                 stringA=A.lexema -> lexema del token 0 
                 while (!stringX.equals("$")) -> como esta en la posicion Q si es diferente de 0
                 por eso arranca siempre el bucle
                    si A.tipo (osea si el tipo del token 0) es tipo identificador
                        stringA=id; -> el stringA va a guardar el simbolo de entrada
                        salta directo al stringX = pila.lastElement();
                        StringX=pila.lasElement() -> $ y termina
                        --------
                        StringX=->vacio

                segundo recorrido
                while (!stringX.equals("$")) -> como esta en la posicion $
                termina
                 */
                stringA = "id";//StringA es el simbolo de entrada
            }

            if (stringX.hashCode() == stringA.hashCode()) { //hasCode devuelve un entero
                /*otro caso 
                primer recorrido
                 * ip=0
                 * ---------pila
                 * Q-> select ->posicion 1
                 * $ -> posicion 0
                 * ---------fin de pila
                 stringX=Q-> la posicion 1
                 A=token.get(0) -> posicion en 0 el primer token
                 stringA=A.lexema -> lexema del token 0 
                 while (!stringX.equals("$")) -> como esta en la posicion Q si es diferente de 0
                 por eso arranca siempre el bucle
                    si A.tipo (osea si el tipo del token 0) en este caso no es identificador entonces no entra, 
                    y salta a este if
                    si stringX.hasCode(1) == stringA.hasCode(1)-- como si entra
                        pila.pop -> eliminas el pop, eliminas la tapa, en este caso Q
                         * ---------pila
                         * $ -> posicion 0
                         * ---------fin de pila
                         * ip=1
                         * A=tokens.get(1)
                         * stringA=A.lexema ->lexema del token 1
                         * termina el if 
                         * stringX = pila.lastElement();
                         * while (!stringX.equals("$")) -> como esta en la posicion $ termina
                 */
                pila.pop();
                ip++; 
                A = tokens.get(ip);
                stringA = A.lexema;
            } else if (TablaAnalisis.esTerminal(stringX, tabla)){ //sirve para recorrer la tabla .esTerminal,la jala de otro archivo
                //analizar esTerminal
                /*otro caso 
                primer recorrido
                 * ip=0
                 * ---------pila
                 * Q-> select ->posicion 1
                 * $ -> posicion 0
                 * ---------fin de pila
                 stringX=Q-> la posicion 1
                 A=token.get(0) -> posicion en 0 el primer token
                 stringA=A.lexema -> lexema del token 0 
                 while (!stringX.equals("$")) -> como esta en la posicion Q si es diferente de 0
                 por eso arranca siempre el bucle
                    si A.tipo (osea si el tipo del token 0) en este caso no es identificador entonces no entra, 
                    y como el hasCode de StringX y StringA es diferente entra a este if
                    System.out.println("Error. " + (ip + 1=1) + ": Simbolo terminal no esperado..."); 
                    hayErrores=1;    
                    termina
                    stringX = pila.lastElement();=$ 
                    while (!stringX.equals("$")) -> como esta en la posicion $ termina y hay errores
                 */
                System.out.println("Error. " + (ip + 1) + ": Simbolo terminal no esperado..."); 
                hayErrores = true;
                break;
            } else if (TablaAnalisis.validar(stringX, stringA, tabla) == "") { //sirve para recorrer la tabla columa y fila
                //ver que hace la funcion de validar
                /*otro caso 
                primer recorrido
                 * ip=0
                 * ---------pila
                 * Q-> select ->posicion 1
                 * $ -> posicion 0
                 * ---------fin de pila
                 stringX=Q-> la posicion 1
                 A=token.get(0) -> posicion en 0 el primer token
                 stringA=A.lexema -> lexema del token 0 
                 while (!stringX.equals("$")) -> como esta en la posicion Q si es diferente de 0
                 por eso arranca siempre el bucle
                    si A.tipo (osea si el tipo del token 0) en este caso no es identificador entonces no entra, 
                    y como el hasCode de StringX y StringA, y como llega a NoTerminal pasa a este
                    System.out.println("Error. " + (ip + 1 = 1) + ": Producción no válida.");
                    hayErrores=1;    
                    termina
                    stringX = pila.lastElement();=$ 
                    while (!stringX.equals("$")) -> como esta en la posicion $ termina y hay errores
                    termina
                 */
                System.out.println("Error. " + (ip + 1) + ": Producción no válida.");
                hayErrores = true;
                break;
            } else {
                /*otro caso 
                primer recorrido
                 * ip=0
                 * ---------pila
                 * Q-> select ->posicion 1
                 * $ -> posicion 0
                 * ---------fin de pila
                 stringX=Q-> la posicion 1
                 A=token.get(0) -> posicion en 0 el primer token
                 stringA=A.lexema -> lexema del token 0 
                 while (!stringX.equals("$")) -> como esta en la posicion Q si es diferente de 0
                 por eso arranca siempre el bucle
                    si A.tipo (osea si el tipo del token 0) en este caso no es identificador entonces no entra, 
                    y como el hasCode de StringX y StringA, y como llega a NoTerminal pasa a este, si no, salta a este
                    produccion=TablaAnalisis.validar(Q,lexema del token 0, tabla) -> por que tabla?
                    System.out.println(produccion);
                    pila.pop
                    * ---------pila
                    * $ -> posicion 0
                    * ---------fin de pila
                    produccionArrayList = Leer.aArraylist(produccion);
                    es un arreglo de String, cuando se escribe bien siempre esta en este caso, y en el primer caso
                    todo el arreglo de cadenas ya esta apilada, como tal no es pila solo es un arreglo
                    ----\----\---- se ve de esta forma 
                    if(el arreglo de la lista.get(0).hascode(1) es diferente de epsilon)
                    entramos al bucle para recorrer el arreglo e irla apilado en la pila para despues borrar 
                    el arreglo de cadenas, pero cuando entra en este caso para que termine tiene que entrar en
                    0,1 o 2 para que: stringX = pila.lastElement(); quede en -> $
                 */
                produccion = TablaAnalisis.validar(stringX, stringA, tabla);
                System.out.println(produccion);
                pila.pop();
                produccionArrayList = Leer.aArraylist(produccion); //archivo leer 
                if(produccionArrayList.get(0).hashCode() != "E".hashCode()){ //Si no es E no mete la producción a la pila
                    for (int i = produccionArrayList.size() - 1; i >= 0; i--) {
                        pila.push(produccionArrayList.get(i));
                    }
                }
                produccionArrayList.clear();
            }
            stringX = pila.lastElement();
        }
    }
}

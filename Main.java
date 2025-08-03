import java.util.List;
import java.util.Scanner;

public class Main {
    private final Scanner scanner;

    public Main() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("1. Seleccionar Pokémon (inicial)");
        System.out.println("2. Iniciar Combate");
        System.out.println("3. Consultar Estado Pokémon");
        System.out.println("4. Salir");
        System.out.println("5. Crear Nuevo Entrenador");
    }

    public void mostrarPokemon(List<Pokemon> pokemonList) {
        if (pokemonList.isEmpty()) {
            System.out.println("No hay Pokémon seleccionados.");
        } else {
            for (Pokemon pokemon : pokemonList) {
                System.out.println("Pokémon: " + pokemon.getNombre() + ", Tipo: " + pokemon.getTipo() +
                        ", Ataque: " + pokemon.getAtaque() + ", Defensa: " + pokemon.getDefensa() +
                        ", Habilidad: " + pokemon.getHabilidadNombre());
            }
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public int obtenerEntrada(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextInt();
    }

    public String obtenerTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.next();
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static void main(String[] args) {
        Main view = new Main();
        Entrenador entrenador1 = new Entrenador("Ash");
        Entrenador entrenador2 = new Entrenador("Misty");
        Combate controller = new Combate(entrenador1, entrenador2, view);
        controller.iniciar();
        view.getScanner().close();
    }
}
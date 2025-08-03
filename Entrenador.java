import java.util.ArrayList;
import java.util.List;

public class Entrenador {
    private final String nombre;
    private final List<Pokemon> listaPokemon;

    public Entrenador(String nombre) {
        this.nombre = nombre;
        this.listaPokemon = new ArrayList<>();
    }

    public void seleccionarPokemon(Pokemon pokemon) {
        if (listaPokemon.size() < 4) {
            listaPokemon.add(pokemon);
        }
    }

    public List<Pokemon> getListaPokemon() {
        return listaPokemon;
    }

    public String getNombre() {
        return nombre;
    }
}
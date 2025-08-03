import java.util.ArrayList;
import java.util.List;

public class Combate {
    private List<Entrenador> entrenadores;
    private final Main view;
    private int rondaActual;
    private List<Integer> victorias;
    private boolean pokemonesSeleccionados;

    public Combate(Entrenador entrenador1, Entrenador entrenador2, Main view) {
        this.entrenadores = new ArrayList<>();
        this.entrenadores.add(entrenador1);
        this.entrenadores.add(entrenador2);
        this.view = view;
        this.rondaActual = 0;
        this.victorias = new ArrayList<>(List.of(0, 0));
        this.pokemonesSeleccionados = false;
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            view.mostrarMenu();
            int opcion = view.obtenerEntrada("Seleccione una opción:");
            switch (opcion) {
                case 1:
                    if (!pokemonesSeleccionados) {
                        seleccionarPokemonesManualmente();
                        pokemonesSeleccionados = true;
                    } else {
                        view.mostrarMensaje("Los Pokémon ya han sido seleccionados. Use opción 3 para consultar.");
                    }
                    break;
                case 2:
                    if (!pokemonesSeleccionados || entrenadores.get(0).getListaPokemon().size() < 4 || entrenadores.get(1).getListaPokemon().size() < 4) {
                        view.mostrarMensaje("Debe seleccionar 4 Pokémon por entrenador primero (Opción 1).");
                    } else if (rondaActual < 4) {
                        iniciarRonda();
                    } else {
                        view.mostrarMensaje("Las 4 rondas han terminado. Consulte el resultado o salga.");
                    }
                    break;
                case 3:
                    view.mostrarMensaje("Pokémon de " + entrenadores.get(0).getNombre() + ":");
                    view.mostrarPokemon(entrenadores.get(0).getListaPokemon());
                    view.mostrarMensaje("Pokémon de " + entrenadores.get(1).getNombre() + ":");
                    view.mostrarPokemon(entrenadores.get(1).getListaPokemon());
                    break;
                case 4:
                    view.mostrarMensaje("Saliendo del juego.");
                    salir = true;
                    break;
                case 5:
                    crearNuevoEntrenador();
                    break;
                default:
                    view.mostrarMensaje("Opción inválida. Intente de nuevo.");
                    break;
            }
            if (rondaActual >= 4) {
                determinarGanadorFinal();
            }
        }
    }

    private void seleccionarPokemonesManualmente() {
        for (int t = 0; t < entrenadores.size(); t++) {
            Entrenador entrenador = entrenadores.get(t);
            view.mostrarMensaje("Seleccionando Pokémon para " + entrenador.getNombre() + ":");
            for (int i = 0; i < 4; i++) {
                view.mostrarMensaje("Pokémon disponibles: Charizard, Bulbasaur, Squirtle, Pikachu");
                int index = view.obtenerEntrada("Seleccione el Pokémon " + (i + 1) + " (1-Charizard, 2-Bulbasaur, 3-Squirtle, 4-Pikachu):") - 1;
                while (index < 0 || index > 3) {
                    index = view.obtenerEntrada("Selección inválida. Intente de nuevo (1-4):") - 1;
                }
                String[] nombres = {"Charizard", "Bulbasaur", "Squirtle", "Pikachu"};
                String[] tipos = {"Fuego", "Planta", "Agua", "Eléctrico"};
                Habilidad habilidad = new Habilidad("Habilidad" + (i + 1), 15, 30);
                Pokemon pokemon = new Pokemon(nombres[index], tipos[index], 50, 30, habilidad);
                entrenador.seleccionarPokemon(pokemon);
            }
        }
        view.mostrarMensaje("Pokémon seleccionados con éxito.");
    }

    private void iniciarRonda() {
        rondaActual++;
        view.mostrarMensaje("Ronda " + rondaActual);
        Pokemon pokemon1 = elegirPokemon(entrenadores.get(0));
        Pokemon pokemon2 = elegirPokemon(entrenadores.get(1));
        int ataque1 = pokemon1.calcularAtaque(pokemon2.getTipo());
        int ataque2 = pokemon2.calcularAtaque(pokemon1.getTipo());
        String ganador = determinarGanadorRonda(ataque1, ataque2);
        view.mostrarMensaje("El ganador es: " + ganador);
        pokemon1.resetHabilidad();
        pokemon2.resetHabilidad();
    }

    private Pokemon elegirPokemon(Entrenador entrenador) {
        view.mostrarMensaje("Pokémon disponibles de " + entrenador.getNombre() + ":");
        view.mostrarPokemon(entrenador.getListaPokemon());
        int index = view.obtenerEntrada("Seleccione el número del Pokémon (1-" + entrenador.getListaPokemon().size() + "):") - 1;
        while (index < 0 || index >= entrenador.getListaPokemon().size()) {
            index = view.obtenerEntrada("Selección inválida. Seleccione un número válido:") - 1;
        }
        Pokemon seleccionado = entrenador.getListaPokemon().get(index);
        entrenador.getListaPokemon().remove(index);
        return seleccionado;
    }

    private String determinarGanadorRonda(int ataque1, int ataque2) {
        if (ataque1 > ataque2) {
            victorias.set(0, victorias.get(0) + 1);
            return entrenadores.get(0).getNombre();
        } else if (ataque2 > ataque1) {
            victorias.set(1, victorias.get(1) + 1);
            return entrenadores.get(1).getNombre();
        } else {
            return "Empate";
        }
    }

    private void determinarGanadorFinal() {
        view.mostrarMensaje("Resultado final:");
        view.mostrarMensaje(entrenadores.get(0).getNombre() + ": " + victorias.get(0) + " victorias");
        view.mostrarMensaje(entrenadores.get(1).getNombre() + ": " + victorias.get(1) + " victorias");
        if (victorias.get(0) > victorias.get(1)) {
            view.mostrarMensaje("El ganador es: " + entrenadores.get(0).getNombre());
        } else if (victorias.get(1) > victorias.get(0)) {
            view.mostrarMensaje("El ganador es: " + entrenadores.get(1).getNombre());
        } else {
            view.mostrarMensaje("El ganador es: Empate");
        }
    }

    private void crearNuevoEntrenador() {
        String nombre1 = view.obtenerTexto("Ingrese el nombre del primer entrenador:");
        String nombre2 = view.obtenerTexto("Ingrese el nombre del segundo entrenador:");
        entrenadores.clear();
        entrenadores.add(new Entrenador(nombre1));
        entrenadores.add(new Entrenador(nombre2));
        pokemonesSeleccionados = false;
        victorias.set(0, 0);
        victorias.set(1, 0);
        view.mostrarMensaje("Nuevos entrenadores creados: " + nombre1 + " y " + nombre2);
    }
}
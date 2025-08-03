import java.util.Random;

public class Habilidad {
    private final String name;
    private final int effect;
    private final int probability;

    public Habilidad(String name, int effect, int probability) {
        this.name = name;
        this.effect = effect;
        this.probability = probability;
    }

    public boolean activar() {
        Random rand = new Random();
        return rand.nextInt(100) < probability;
    }

    public int getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }
}
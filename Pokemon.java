public class Pokemon {
    private final String nombre;
    private final String tipo;
    private final int ataque;
    private final int defensa;
    private final Habilidad habilidad;
    private boolean habilidadActivadaRondaAnterior;

    public Pokemon(String nombre, String tipo, int ataque, int defensa, Habilidad habilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.ataque = ataque;
        this.defensa = defensa;
        this.habilidad = habilidad;
        this.habilidadActivadaRondaAnterior = false;
    }

    public int calcularAtaque(String tipoOponente) {
        int ataqueTotal = ataque;
        int efectividad = calcularEfectividad(tipo, tipoOponente);
        ataqueTotal += efectividad;
        if (habilidad.activar() || habilidadActivadaRondaAnterior) {
            ataqueTotal += habilidad.getEffect();
            habilidadActivadaRondaAnterior = habilidad.activar();
        }
        return ataqueTotal;
    }

    private int calcularEfectividad(String tipoAtacante, String tipoDefensor) {
        if (tipoAtacante.equals("Fuego") && tipoDefensor.equals("Planta") ||
                tipoAtacante.equals("Planta") && tipoDefensor.equals("Agua") ||
                tipoAtacante.equals("Agua") && tipoDefensor.equals("Fuego") ||
                tipoAtacante.equals("Eléctrico") && tipoDefensor.equals("Agua")) {
            return 20; // Efectivo
        } else if (tipoAtacante.equals("Fuego") && tipoDefensor.equals("Agua") ||
                tipoAtacante.equals("Planta") && tipoDefensor.equals("Fuego") ||
                tipoAtacante.equals("Agua") && tipoDefensor.equals("Planta") ||
                tipoAtacante.equals("Eléctrico") && tipoDefensor.equals("Planta")) {
            return -10; // Débil
        }
        return 0; // Neutral
    }

    public void resetHabilidad() {
        habilidadActivadaRondaAnterior = habilidad.activar();
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getAtaque() {
        return ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public String getHabilidadNombre() {
        return habilidad.getName();
    }
}
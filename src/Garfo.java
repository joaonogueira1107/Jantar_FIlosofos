public class Garfo {
    private boolean emUso; // Indica se o garfo está em uso

    public Garfo() {
        this.emUso = false; // Inicialmente o garfo não está em uso
    }

    public synchronized boolean pegar() {
        // Tenta pegar o garfo
        if (!emUso) {
            emUso = true; // Marca como em uso
            return true;
        }
        return false; // Não conseguiu pegar
    }

    public synchronized void liberar() {
        emUso = false; // Libera o garfo
    }
}
package modelo;

public class CdAudio extends MaterialAudiovisual {

    private String artista;
    private int    numCanciones;

    public CdAudio() {}

    public CdAudio(int idDocumento, String codigoInterno, String titulo,
                   String ubicacionFisica, int unidadesTotales, int unidadesDisponibles,
                   boolean activo, int duracionMin, String genero,
                   String artista, int numCanciones) {
        super(idDocumento, 3, codigoInterno, titulo, ubicacionFisica,
              unidadesTotales, unidadesDisponibles, activo, duracionMin, genero);
        this.artista      = artista;
        this.numCanciones = numCanciones;
    }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public int getNumCanciones() { return numCanciones; }
    public void setNumCanciones(int numCanciones) { this.numCanciones = numCanciones; }
}
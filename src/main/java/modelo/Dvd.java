package modelo;

public class Dvd extends MaterialAudiovisual {

    private String director;

    public Dvd() {}

    public Dvd(int idDocumento, String codigoInterno, String titulo,
               String ubicacionFisica, int unidadesTotales, int unidadesDisponibles,
               boolean activo, int duracionMin, String genero, String director) {
        super(idDocumento, 4, codigoInterno, titulo, ubicacionFisica,
              unidadesTotales, unidadesDisponibles, activo, duracionMin, genero);
        this.director = director;
    }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
}
package modelo;

public class Libro extends MaterialEscrito {

    private String autor;
    private String isbn;
    private int    numPaginas;
    private int    anioPublicacion;

    public Libro() {}

    public Libro(int idDocumento, String codigoInterno, String titulo,
                 String ubicacionFisica, int unidadesTotales, int unidadesDisponibles,
                 boolean activo, String editorial, String autor,
                 String isbn, int numPaginas, int anioPublicacion) {
        super(idDocumento, 1, codigoInterno, titulo, ubicacionFisica,
              unidadesTotales, unidadesDisponibles, activo, editorial);
        this.autor           = autor;
        this.isbn            = isbn;
        this.numPaginas      = numPaginas;
        this.anioPublicacion = anioPublicacion;
    }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getNumPaginas() { return numPaginas; }
    public void setNumPaginas(int numPaginas) { this.numPaginas = numPaginas; }

    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    @Override
    public String toString() {
        return "[" + codigoInterno + "] " + titulo + " - " + autor;
    }
}
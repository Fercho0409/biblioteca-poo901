package modelo;

public abstract class MaterialAudiovisual extends Material {

    protected int    duracionMin;
    protected String genero;

    public MaterialAudiovisual() {}

    public MaterialAudiovisual(int idDocumento, int idTipo, String codigoInterno,
                               String titulo, String ubicacionFisica,
                               int unidadesTotales, int unidadesDisponibles,
                               boolean activo, int duracionMin, String genero) {
        super(idDocumento, idTipo, codigoInterno, titulo, ubicacionFisica,
              unidadesTotales, unidadesDisponibles, activo);
        this.duracionMin = duracionMin;
        this.genero      = genero;
    }

    public int getDuracionMin() { return duracionMin; }
    public void setDuracionMin(int duracionMin) { this.duracionMin = duracionMin; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
}
package modelo;

public abstract class MaterialEscrito extends Material {

    protected String editorial;

    public MaterialEscrito() {}

    public MaterialEscrito(int idDocumento, int idTipo, String codigoInterno,
                           String titulo, String ubicacionFisica,
                           int unidadesTotales, int unidadesDisponibles,
                           boolean activo, String editorial) {
        super(idDocumento, idTipo, codigoInterno, titulo, ubicacionFisica,
              unidadesTotales, unidadesDisponibles, activo);
        this.editorial = editorial;
    }

    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
}
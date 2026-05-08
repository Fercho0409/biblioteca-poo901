package modelo;

public abstract class Material {

    protected int    idDocumento;
    protected int    idTipo;
    protected String codigoInterno;
    protected String titulo;
    protected String ubicacionFisica;
    protected int    unidadesTotales;
    protected int    unidadesDisponibles;
    protected boolean activo;

    public Material() {}

    public Material(int idDocumento, int idTipo, String codigoInterno,
                    String titulo, String ubicacionFisica,
                    int unidadesTotales, int unidadesDisponibles, boolean activo) {
        this.idDocumento         = idDocumento;
        this.idTipo              = idTipo;
        this.codigoInterno       = codigoInterno;
        this.titulo              = titulo;
        this.ubicacionFisica     = ubicacionFisica;
        this.unidadesTotales     = unidadesTotales;
        this.unidadesDisponibles = unidadesDisponibles;
        this.activo              = activo;
    }

    public int getIdDocumento() { return idDocumento; }
    public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getUbicacionFisica() { return ubicacionFisica; }
    public void setUbicacionFisica(String ubicacionFisica) { this.ubicacionFisica = ubicacionFisica; }

    public int getUnidadesTotales() { return unidadesTotales; }
    public void setUnidadesTotales(int unidadesTotales) { this.unidadesTotales = unidadesTotales; }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return "[" + codigoInterno + "] " + titulo;
    }
}
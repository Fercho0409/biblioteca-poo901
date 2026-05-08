package modelo;

import java.util.Date;

public class Revista extends MaterialEscrito {

    private String periodicidad;
    private Date   fechaPublicacion;

    public Revista() {}

    public Revista(int idDocumento, String codigoInterno, String titulo,
                   String ubicacionFisica, int unidadesTotales, int unidadesDisponibles,
                   boolean activo, String editorial, String periodicidad, Date fechaPublicacion) {
        super(idDocumento, 2, codigoInterno, titulo, ubicacionFisica,
              unidadesTotales, unidadesDisponibles, activo, editorial);
        this.periodicidad     = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getPeriodicidad() { return periodicidad; }
    public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }

    public Date getFechaPublicacion() { return fechaPublicacion; }
    public void setFechaPublicacion(Date fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
}
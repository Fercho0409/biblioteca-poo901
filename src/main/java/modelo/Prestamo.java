package modelo;

import java.util.Date;

public class Prestamo {

    private int    idPrestamo;
    private int    idUsuario;
    private int    idDocumento;
    private Date   fechaPrestamo;
    private Date   fechaLimite;
    private Date   fechaDevolucion;
    private double moraAcumulada;
    private String estado;

    private String nombreUsuario;
    private String tituloDocumento;
    private String codigoInterno;

    public Prestamo() {}

    public Prestamo(int idPrestamo, int idUsuario, int idDocumento,
                    Date fechaPrestamo, Date fechaLimite,
                    Date fechaDevolucion, double moraAcumulada, String estado) {
        this.idPrestamo      = idPrestamo;
        this.idUsuario       = idUsuario;
        this.idDocumento     = idDocumento;
        this.fechaPrestamo   = fechaPrestamo;
        this.fechaLimite     = fechaLimite;
        this.fechaDevolucion = fechaDevolucion;
        this.moraAcumulada   = moraAcumulada;
        this.estado          = estado;
    }

    public int getIdPrestamo() { return idPrestamo; }
    public void setIdPrestamo(int idPrestamo) { this.idPrestamo = idPrestamo; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdDocumento() { return idDocumento; }
    public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }

    public Date getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public Date getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(Date fechaLimite) { this.fechaLimite = fechaLimite; }

    public Date getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public double getMoraAcumulada() { return moraAcumulada; }
    public void setMoraAcumulada(double moraAcumulada) { this.moraAcumulada = moraAcumulada; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getTituloDocumento() { return tituloDocumento; }
    public void setTituloDocumento(String tituloDocumento) { this.tituloDocumento = tituloDocumento; }

    public String getCodigoInterno() { return codigoInterno; }
    public void setCodigoInterno(String codigoInterno) { this.codigoInterno = codigoInterno; }
}
package modelo;

public class Usuario {

    private int     idUsuario;
    private String  nombre;
    private String  apellido;
    private String  email;
    private String  password;
    private String  tipoUsuario;
    private boolean activo;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellido,
                   String email, String password, String tipoUsuario, boolean activo) {
        this.idUsuario   = idUsuario;
        this.nombre      = nombre;
        this.apellido    = apellido;
        this.email       = email;
        this.password    = password;
        this.tipoUsuario = tipoUsuario;
        this.activo      = activo;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTipoUsuario() { return tipoUsuario; }
    public void setTipoUsuario(String tipo) { this.tipoUsuario = tipo; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        return nombre + " " + apellido + " [" + tipoUsuario + "]";
    }
}
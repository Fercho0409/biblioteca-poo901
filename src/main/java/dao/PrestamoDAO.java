package dao;

import conexion.Conexion;
import modelo.Prestamo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean insertar(Prestamo p) {
        String sql = "INSERT INTO prestamos (id_usuario, id_documento, fecha_prestamo, fecha_limite, estado) VALUES (?,?,CURDATE(),?,'ACTIVO')";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdUsuario());
            ps.setInt(2, p.getIdDocumento());
            ps.setDate(3, new java.sql.Date(p.getFechaLimite().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    public boolean devolver(int idPrestamo) {
        String sql = "UPDATE prestamos SET fecha_devolucion=CURDATE(), estado='DEVUELTO' WHERE id_prestamo=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al devolver préstamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> listarActivos() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.*, CONCAT(u.nombre,' ',u.apellido) AS nombre_usuario, d.titulo, d.codigo_interno " +
                     "FROM prestamos p " +
                     "JOIN usuarios u ON p.id_usuario = u.id_usuario " +
                     "JOIN documentos d ON p.id_documento = d.id_documento " +
                     "WHERE p.estado IN ('ACTIVO','CON_MORA') ORDER BY p.fecha_limite";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Prestamo pr = mapear(rs);
                pr.setNombreUsuario(rs.getString("nombre_usuario"));
                pr.setTituloDocumento(rs.getString("titulo"));
                pr.setCodigoInterno(rs.getString("codigo_interno"));
                lista.add(pr);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar préstamos: " + e.getMessage());
        }
        return lista;
    }

    public int contarPrestamosActivos(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM prestamos WHERE id_usuario=? AND estado IN ('ACTIVO','CON_MORA')";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println("Error al contar préstamos: " + e.getMessage());
        }
        return 0;
    }

    public void actualizarMoras(double moraDiaria) {
        String sql = "UPDATE prestamos SET mora_acumulada = DATEDIFF(CURDATE(), fecha_limite) * ?, estado='CON_MORA' " +
                     "WHERE estado='ACTIVO' AND fecha_limite < CURDATE()";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, moraDiaria);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar moras: " + e.getMessage());
        }
    }

    private Prestamo mapear(ResultSet rs) throws SQLException {
        return new Prestamo(
            rs.getInt("id_prestamo"),
            rs.getInt("id_usuario"),
            rs.getInt("id_documento"),
            rs.getDate("fecha_prestamo"),
            rs.getDate("fecha_limite"),
            rs.getDate("fecha_devolucion"),
            rs.getDouble("mora_acumulada"),
            rs.getString("estado")
        );
    }

    public boolean devolverConFecha(int idPrestamo, String fechaStr) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
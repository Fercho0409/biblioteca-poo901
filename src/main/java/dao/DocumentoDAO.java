package dao;

import conexion.Conexion;
import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {

    public String generarCodigo(String prefijo, int idTipo) {
        String sql = "SELECT COUNT(*) FROM documentos WHERE id_tipo=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTipo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int siguiente = rs.getInt(1) + 1;
                return prefijo + String.format("%05d", siguiente);
            }
        } catch (SQLException e) {
            System.out.println("Error al generar código: " + e.getMessage());
        }
        return prefijo + "00001";
    }

    public boolean insertarLibro(Libro l) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);
            String sqlDoc = "INSERT INTO documentos (id_tipo, codigo_interno, titulo, ubicacion_fisica, unidades_totales, unidades_disponibles) VALUES (1,?,?,?,?,?)";
            PreparedStatement psDoc = con.prepareStatement(sqlDoc, Statement.RETURN_GENERATED_KEYS);
            psDoc.setString(1, l.getCodigoInterno());
            psDoc.setString(2, l.getTitulo());
            psDoc.setString(3, l.getUbicacionFisica());
            psDoc.setInt(4, l.getUnidadesTotales());
            psDoc.setInt(5, l.getUnidadesDisponibles());
            psDoc.executeUpdate();
            ResultSet keys = psDoc.getGeneratedKeys();
            if (keys.next()) {
                int idDoc = keys.getInt(1);
                String sqlLib = "INSERT INTO libros (id_documento, autor, editorial, isbn, num_paginas, anio_publicacion) VALUES (?,?,?,?,?,?)";
                PreparedStatement psLib = con.prepareStatement(sqlLib);
                psLib.setInt(1, idDoc);
                psLib.setString(2, l.getAutor());
                psLib.setString(3, l.getEditorial());
                psLib.setString(4, l.getIsbn());
                psLib.setInt(5, l.getNumPaginas());
                psLib.setInt(6, l.getAnioPublicacion());
                psLib.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            System.out.println("Error al insertar libro: " + e.getMessage());
            return false;
        }
    }

    public List<Libro> listarLibros() {
        List<Libro> lista = new ArrayList<>();
        String sql = "SELECT d.*, l.autor, l.editorial, l.isbn, l.num_paginas, l.anio_publicacion " +
                     "FROM documentos d JOIN libros l ON d.id_documento = l.id_documento " +
                     "WHERE d.activo = 1 ORDER BY d.titulo";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Libro(
                    rs.getInt("id_documento"), rs.getString("codigo_interno"),
                    rs.getString("titulo"), rs.getString("ubicacion_fisica"),
                    rs.getInt("unidades_totales"), rs.getInt("unidades_disponibles"),
                    rs.getBoolean("activo"), rs.getString("editorial"),
                    rs.getString("autor"), rs.getString("isbn"),
                    rs.getInt("num_paginas"), rs.getInt("anio_publicacion")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar libros: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarRevista(Revista r) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);
            String sqlDoc = "INSERT INTO documentos (id_tipo, codigo_interno, titulo, ubicacion_fisica, unidades_totales, unidades_disponibles) VALUES (2,?,?,?,?,?)";
            PreparedStatement psDoc = con.prepareStatement(sqlDoc, Statement.RETURN_GENERATED_KEYS);
            psDoc.setString(1, r.getCodigoInterno());
            psDoc.setString(2, r.getTitulo());
            psDoc.setString(3, r.getUbicacionFisica());
            psDoc.setInt(4, r.getUnidadesTotales());
            psDoc.setInt(5, r.getUnidadesDisponibles());
            psDoc.executeUpdate();
            ResultSet keys = psDoc.getGeneratedKeys();
            if (keys.next()) {
                int idDoc = keys.getInt(1);
                String sqlRev = "INSERT INTO revistas (id_documento, editorial, periodicidad, fecha_publicacion) VALUES (?,?,?,?)";
                PreparedStatement psRev = con.prepareStatement(sqlRev);
                psRev.setInt(1, idDoc);
                psRev.setString(2, r.getEditorial());
                psRev.setString(3, r.getPeriodicidad());
                psRev.setDate(4, r.getFechaPublicacion() != null ? new java.sql.Date(r.getFechaPublicacion().getTime()) : null);
                psRev.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            System.out.println("Error al insertar revista: " + e.getMessage());
            return false;
        }
    }

    public List<Revista> listarRevistas() {
        List<Revista> lista = new ArrayList<>();
        String sql = "SELECT d.*, r.editorial, r.periodicidad, r.fecha_publicacion " +
                     "FROM documentos d JOIN revistas r ON d.id_documento = r.id_documento " +
                     "WHERE d.activo = 1 ORDER BY d.titulo";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Revista(
                    rs.getInt("id_documento"), rs.getString("codigo_interno"),
                    rs.getString("titulo"), rs.getString("ubicacion_fisica"),
                    rs.getInt("unidades_totales"), rs.getInt("unidades_disponibles"),
                    rs.getBoolean("activo"), rs.getString("editorial"),
                    rs.getString("periodicidad"), rs.getDate("fecha_publicacion")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar revistas: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarCd(CdAudio cd) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);
            String sqlDoc = "INSERT INTO documentos (id_tipo, codigo_interno, titulo, ubicacion_fisica, unidades_totales, unidades_disponibles) VALUES (3,?,?,?,?,?)";
            PreparedStatement psDoc = con.prepareStatement(sqlDoc, Statement.RETURN_GENERATED_KEYS);
            psDoc.setString(1, cd.getCodigoInterno());
            psDoc.setString(2, cd.getTitulo());
            psDoc.setString(3, cd.getUbicacionFisica());
            psDoc.setInt(4, cd.getUnidadesTotales());
            psDoc.setInt(5, cd.getUnidadesDisponibles());
            psDoc.executeUpdate();
            ResultSet keys = psDoc.getGeneratedKeys();
            if (keys.next()) {
                int idDoc = keys.getInt(1);
                String sqlCd = "INSERT INTO cds_audio (id_documento, artista, genero, duracion_min, num_canciones) VALUES (?,?,?,?,?)";
                PreparedStatement psCd = con.prepareStatement(sqlCd);
                psCd.setInt(1, idDoc);
                psCd.setString(2, cd.getArtista());
                psCd.setString(3, cd.getGenero());
                psCd.setInt(4, cd.getDuracionMin());
                psCd.setInt(5, cd.getNumCanciones());
                psCd.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            System.out.println("Error al insertar CD: " + e.getMessage());
            return false;
        }
    }

    public List<CdAudio> listarCds() {
        List<CdAudio> lista = new ArrayList<>();
        String sql = "SELECT d.*, c.artista, c.genero, c.duracion_min, c.num_canciones " +
                     "FROM documentos d JOIN cds_audio c ON d.id_documento = c.id_documento " +
                     "WHERE d.activo = 1 ORDER BY d.titulo";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new CdAudio(
                    rs.getInt("id_documento"), rs.getString("codigo_interno"),
                    rs.getString("titulo"), rs.getString("ubicacion_fisica"),
                    rs.getInt("unidades_totales"), rs.getInt("unidades_disponibles"),
                    rs.getBoolean("activo"), rs.getInt("duracion_min"),
                    rs.getString("genero"), rs.getString("artista"),
                    rs.getInt("num_canciones")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar CDs: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertarDvd(Dvd dvd) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);
            String sqlDoc = "INSERT INTO documentos (id_tipo, codigo_interno, titulo, ubicacion_fisica, unidades_totales, unidades_disponibles) VALUES (4,?,?,?,?,?)";
            PreparedStatement psDoc = con.prepareStatement(sqlDoc, Statement.RETURN_GENERATED_KEYS);
            psDoc.setString(1, dvd.getCodigoInterno());
            psDoc.setString(2, dvd.getTitulo());
            psDoc.setString(3, dvd.getUbicacionFisica());
            psDoc.setInt(4, dvd.getUnidadesTotales());
            psDoc.setInt(5, dvd.getUnidadesDisponibles());
            psDoc.executeUpdate();
            ResultSet keys = psDoc.getGeneratedKeys();
            if (keys.next()) {
                int idDoc = keys.getInt(1);
                String sqlDvd = "INSERT INTO dvds (id_documento, director, duracion_min, genero) VALUES (?,?,?,?)";
                PreparedStatement psDvd = con.prepareStatement(sqlDvd);
                psDvd.setInt(1, idDoc);
                psDvd.setString(2, dvd.getDirector());
                psDvd.setInt(3, dvd.getDuracionMin());
                psDvd.setString(4, dvd.getGenero());
                psDvd.executeUpdate();
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {}
            System.out.println("Error al insertar DVD: " + e.getMessage());
            return false;
        }
    }

    public List<Dvd> listarDvds() {
        List<Dvd> lista = new ArrayList<>();
        String sql = "SELECT d.*, v.director, v.duracion_min, v.genero " +
                     "FROM documentos d JOIN dvds v ON d.id_documento = v.id_documento " +
                     "WHERE d.activo = 1 ORDER BY d.titulo";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Dvd(
                    rs.getInt("id_documento"), rs.getString("codigo_interno"),
                    rs.getString("titulo"), rs.getString("ubicacion_fisica"),
                    rs.getInt("unidades_totales"), rs.getInt("unidades_disponibles"),
                    rs.getBoolean("activo"), rs.getInt("duracion_min"),
                    rs.getString("genero"), rs.getString("director")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar DVDs: " + e.getMessage());
        }
        return lista;
    }

    public int buscarIdPorCodigo(String codigo) {
        String sql = "SELECT id_documento FROM documentos WHERE codigo_interno=? AND activo=1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("id_documento");
        } catch (SQLException e) {
            System.out.println("Error al buscar documento: " + e.getMessage());
        }
        return -1;
    }

    public boolean hayDisponibilidad(int idDocumento) {
        String sql = "SELECT unidades_disponibles FROM documentos WHERE id_documento=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDocumento);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println("Error al verificar disponibilidad: " + e.getMessage());
        }
        return false;
    }

    public void actualizarDisponibilidad(int idDocumento, int cambio) {
        String sql = "UPDATE documentos SET unidades_disponibles = unidades_disponibles + ? WHERE id_documento=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cambio);
            ps.setInt(2, idDocumento);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
        }
    }
}
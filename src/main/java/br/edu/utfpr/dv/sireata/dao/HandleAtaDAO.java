package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.model.Ata;
import br.edu.utfpr.dv.sireata.util.DateUtils;

import java.sql.*;

public class HandleAtaDAO {


    public int salvar(Ata ata) throws SQLException {
        boolean insert = (ata.getIdAta() == 0);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();

            if(insert){
                stmt = conn.prepareStatement("INSERT INTO atas(idOrgao, idPresidente, idSecretario, tipo, numero, data, local, localCompleto, dataLimiteComentarios, consideracoesIniciais, audio, documento, publicada, dataPublicacao, aceitarComentarios) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, 0, NULL, 0)", Statement.RETURN_GENERATED_KEYS);
            }else{
                stmt = conn.prepareStatement("UPDATE atas SET idOrgao=?, idPresidente=?, idSecretario=?, tipo=?, numero=?, data=?, local=?, localCompleto=?, dataLimiteComentarios=?, consideracoesIniciais=?, audio=? WHERE idAta=?");
            }

            stmt.setInt(1, ata.getOrgao().getIdOrgao());
            stmt.setInt(2, ata.getPresidente().getIdUsuario());
            stmt.setInt(3, ata.getSecretario().getIdUsuario());
            stmt.setInt(4, ata.getTipo().getValue());
            stmt.setInt(5, ata.getNumero());
            stmt.setTimestamp(6, new java.sql.Timestamp(ata.getData().getTime()));
            stmt.setString(7, ata.getLocal());
            stmt.setString(8, ata.getLocalCompleto());
            stmt.setDate(9, new java.sql.Date(ata.getDataLimiteComentarios().getTime()));
            stmt.setString(10, ata.getConsideracoesIniciais());
            if(ata.getAudio() == null){
                stmt.setNull(11, Types.BINARY);
            }else{
                stmt.setBytes(11, ata.getAudio());
            }

            if(!insert){
                stmt.setInt(12, ata.getIdAta());
            }

            stmt.execute();

            if(insert){
                rs = stmt.getGeneratedKeys();

                if(rs.next()){
                    ata.setIdAta(rs.getInt(1));
                }
            }

            return ata.getIdAta();
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public boolean excluir(int idAta) throws SQLException{
        Connection conn = null;
        Statement stmt = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            stmt.execute("DELETE FROM comentarios WHERE idPauta IN (SELECT idPauta FROM pautas WHERE idAta=" + String.valueOf(idAta) + ")");
            stmt.execute("DELETE FROM pautas WHERE idAta=" + String.valueOf(idAta));
            stmt.execute("DELETE FROM ataparticipantes WHERE idAta=" + String.valueOf(idAta));
            stmt.execute("DELETE FROM anexos WHERE idAta=" + String.valueOf(idAta));
            boolean ret = stmt.execute("DELETE FROM atas WHERE idAta=" + String.valueOf(idAta));

            conn.commit();

            return ret;
        }catch(SQLException ex) {
            conn.rollback();
            throw ex;
        }finally{
            conn.setAutoCommit(true);
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public void publicar(int idAta, byte[] documento) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement("UPDATE atas SET documento=?, dataPublicacao=?, publicada=1, aceitarComentarios=0 WHERE publicada=0 AND idAta=?");

            stmt.setBytes(1, documento);
            stmt.setTimestamp(2, new java.sql.Timestamp(DateUtils.getNow().getTime().getTime()));
            stmt.setInt(3, idAta);

            stmt.execute();
        }finally{
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public boolean isPresidenteOuSecretario(int idUsuario, int idAta) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.idAta FROM atas " +
                    "WHERE idAta=" + String.valueOf(idAta) + " AND (idPresidente=" + String.valueOf(idUsuario) + " OR idSecretario=" + String.valueOf(idUsuario) + ")");

            return rs.next();
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public boolean isPresidente(int idUsuario, int idAta) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.idAta FROM atas " +
                    "WHERE idAta=" + String.valueOf(idAta) + " AND idPresidente=" + String.valueOf(idUsuario));

            return rs.next();
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public boolean isPublicada(int idAta) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.publicada FROM atas " +
                    "WHERE idAta=" + String.valueOf(idAta));

            if(rs.next()) {
                return rs.getInt("publicada") == 1;
            } else {
                return false;
            }
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }


}

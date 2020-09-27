package br.edu.utfpr.dv.sireata.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ComentariosDAO {


    public void liberarComentarios(int idAta) throws SQLException {
        Connection conn = null;
        Statement stmt = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            stmt.execute("UPDATE atas SET aceitarComentarios=1 WHERE publicada=0 AND idAta=" + String.valueOf(idAta));
        }finally{
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public void bloquearComentarios(int idAta) throws SQLException{
        Connection conn = null;
        Statement stmt = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            stmt.execute("UPDATE atas SET aceitarComentarios=0 WHERE idAta=" + String.valueOf(idAta));
        }finally{
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public boolean temComentarios(int idAta) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT COUNT(comentarios.idComentario) AS qtde FROM comentarios " +
                    "INNER JOIN pautas ON pautas.idPauta=comentarios.idPauta " +
                    "WHERE pautas.idAta=" + String.valueOf(idAta));

            if(rs.next()){
                return (rs.getInt("qtde") > 0);
            }else{
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

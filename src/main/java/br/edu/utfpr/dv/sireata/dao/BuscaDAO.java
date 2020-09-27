package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.model.Ata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.utfpr.dv.sireata.model.Ata;
import br.edu.utfpr.dv.sireata.model.Ata.TipoAta;
import br.edu.utfpr.dv.sireata.util.DateUtils;

public abstract class BuscaDAO implements AtaDAO1 {

    public Ata buscarPorId(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(
                    "SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                            "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                            "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                            "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                            "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                            "WHERE idAta = ?");

            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if(rs.next()){
                return this.carregarObjeto(rs);
            }else{
                return null;
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

    public Ata buscarPorNumero(int idOrgao, Ata.TipoAta tipo, int numero, int ano) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(
                    "SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                            "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                            "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                            "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                            "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                            "WHERE atas.publicada = 1 AND atas.idOrgao = ? AND atas.tipo = ? AND atas.numero = ? AND YEAR(atas.data) = ?");

            stmt.setInt(1, idOrgao);
            stmt.setInt(2, tipo.getValue());
            stmt.setInt(3, numero);
            stmt.setInt(4, ano);

            rs = stmt.executeQuery();

            if(rs.next()){
                return this.carregarObjeto(rs);
            }else{
                return null;
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

    public Ata buscarPorPauta(int idPauta) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(
                    "SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                            "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                            "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                            "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                            "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                            "INNER JOIN pautas ON pautas.idAta=atas.idAta " +
                            "WHERE pautas.idPauta = ?");

            stmt.setInt(1, idPauta);

            rs = stmt.executeQuery();

            if(rs.next()){
                return this.carregarObjeto(rs);
            }else{
                return null;
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

    public int buscarProximoNumeroAta(int idOrgao, int ano, Ata.TipoAta tipo) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.prepareStatement(
                    "SELECT MAX(numero) AS numero FROM atas WHERE idOrgao = ? AND YEAR(data) = ? AND tipo = ?");

            stmt.setInt(1, idOrgao);
            stmt.setInt(2, ano);
            stmt.setInt(3, tipo.getValue());

            rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("numero") + 1;
            }else{
                return 1;
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

    public Ata carregarObjeto(ResultSet rs) throws SQLException{
        Ata ata = new Ata();

        ata.setIdAta(rs.getInt("idAta"));
        ata.getOrgao().setIdOrgao(rs.getInt("idOrgao"));
        ata.getOrgao().setNome(rs.getString("orgao"));
        ata.getPresidente().setIdUsuario(rs.getInt("idPresidente"));
        ata.getPresidente().setNome(rs.getString("presidente"));
        ata.getSecretario().setIdUsuario(rs.getInt("idSecretario"));
        ata.getSecretario().setNome(rs.getString("secretario"));
        ata.setTipo(Ata.TipoAta.valueOf(rs.getInt("tipo")));
        ata.setNumero(rs.getInt("numero"));
        ata.setData(rs.getTimestamp("data"));
        ata.setLocal(rs.getString("local"));
        ata.setLocalCompleto(rs.getString("localCompleto"));
        ata.setDataLimiteComentarios(rs.getDate("dataLimiteComentarios"));
        ata.setConsideracoesIniciais(rs.getString("consideracoesIniciais"));
        ata.setAudio(rs.getBytes("audio"));
        ata.setPublicada(rs.getInt("publicada") == 1);
        ata.setAceitarComentarios(rs.getInt("aceitarComentarios") == 1);
        ata.setDataPublicacao(rs.getTimestamp("dataPublicacao"));
        ata.setDocumento(rs.getBytes("documento"));

        return ata;
    }

}

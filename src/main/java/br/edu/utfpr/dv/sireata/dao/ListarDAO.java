package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.model.Ata;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import br.edu.utfpr.dv.sireata.model.Ata;
import br.edu.utfpr.dv.sireata.model.Ata.TipoAta;
import br.edu.utfpr.dv.sireata.util.DateUtils;


public class ListarDAO {

    public List<Ata> listar(int idUsuario, int idCampus, int idDepartamento, int idOrgao, boolean publicadas) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                    "INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "WHERE ataparticipantes.idUsuario = " + String.valueOf(idUsuario) +
                    " AND atas.publicada = " + (publicadas ? "1 " : "0 ") +
                    (idCampus > 0 ? " AND departamentos.idCampus = " + String.valueOf(idCampus) : "") +
                    (idDepartamento > 0 ? " AND departamentos.idDepartamento = " + String.valueOf(idDepartamento) : "") +
                    (idOrgao > 0 ? " AND atas.idOrgao = " + String.valueOf(idOrgao) : "") +
                    "ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPublicadas() throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "WHERE atas.publicada=1 ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPorOrgao(int idOrgao) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "WHERE atas.publicada=1 AND atas.idOrgao=" + String.valueOf(idOrgao) + " ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPorDepartamento(int idDepartamento) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "WHERE atas.publicada=1 AND Orgaos.idDepartamento=" + String.valueOf(idDepartamento) + " ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPorCampus(int idCampus) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "WHERE atas.publicada=1 AND departamentos.idCampus=" + String.valueOf(idCampus) + " ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarNaoPublicadas(int idUsuario) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                    "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) +" ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPorOrgao(int idOrgao, int idUsuario) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                    "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " AND atas.idOrgao=" + String.valueOf(idOrgao) + " ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPorDepartamento(int idDepartamento, int idUsuario) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                    "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " AND Orgaos.idDepartamento=" + String.valueOf(idDepartamento) + " ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
        }finally{
            if((rs != null) && !rs.isClosed())
                rs.close();
            if((stmt != null) && !stmt.isClosed())
                stmt.close();
            if((conn != null) && !conn.isClosed())
                conn.close();
        }
    }

    public List<Ata> listarPorCampus(int idCampus, int idUsuario) throws SQLException{
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            conn = ConnectionDAO.getInstance().getConnection();
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                    "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                    "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                    "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                    "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                    "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                    "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " AND departamentos.idCampus=" + String.valueOf(idCampus) + " ORDER BY atas.data DESC");

            List<Ata> list = new ArrayList<Ata>();

            while(rs.next()){
                list.add(this.carregarObjeto(rs));
            }

            return list;
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

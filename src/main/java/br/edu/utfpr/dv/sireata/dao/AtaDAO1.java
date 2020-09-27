package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.model.Ata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface AtaDAO1 {
    public Ata buscarPorId(int id) throws SQLException;
    public Ata buscarPorNumero(int idOrgao, Ata.TipoAta tipo, int numero, int ano) throws SQLException;
    public Ata buscarPorPauta(int idPauta) throws SQLException;
    public int buscarProximoNumeroAta(int idOrgao, int ano, Ata.TipoAta tipo) throws SQLException;
    public List<Ata> listar(int idUsuario, int idCampus, int idDepartamento, int idOrgao, boolean publicadas) throws SQLException;
    public List<Ata> listarPublicadas() throws SQLException;
    public List<Ata> listarPorOrgao(int idOrgao) throws SQLException;
    public List<Ata> listarPorDepartamento(int idDepartamento) throws SQLException;
    public List<Ata> listarPorCampus(int idCampus) throws SQLException;
    public List<Ata> listarNaoPublicadas(int idUsuario) throws SQLException;
    public List<Ata> listarPorOrgao(int idOrgao, int idUsuario) throws SQLException;
    public List<Ata> listarPorDepartamento(int idDepartamento, int idUsuario) throws SQLException;
    public List<Ata> listarPorCampus(int idCampus, int idUsuario) throws SQLException;
    public int salvar(Ata ata) throws SQLException;
    public void publicar(int idAta, byte[] documento) throws SQLException;
    public void liberarComentarios(int idAta) throws SQLException;
    public void bloquearComentarios(int idAta) throws SQLException;
    public Ata carregarObjeto(ResultSet rs) throws SQLException;
    public boolean temComentarios(int idAta) throws SQLException;
    public boolean isPresidenteOuSecretario(int idUsuario, int idAta) throws SQLException;
    public boolean isPresidente(int idUsuario, int idAta) throws SQLException;
    public boolean isPublicada(int idAta) throws SQLException;
    public boolean excluir(int idAta) throws SQLException;
}

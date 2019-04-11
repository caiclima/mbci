package br.com.mbci.dao;

import java.util.List;

import br.com.mbci.dao.exceptions.DataException;
import br.com.mbci12.pojo.Usuario;

public interface IUsuarioDAO extends IGenericDAO<Usuario>{

	/**
	 * Busca os agentes de uma equipe passada em parametro
	 * @param equipe
	 * @return
	 * @throws DataException
	 */
	//List<Agente> getAgentesPorEquipe(Equipe equipe) throws DataException;
	
	/**
	 * retorna o Usuario pelo Login informado
	 * @param login
	 * @return
	 * @throws DataException
	 */
	Usuario buscaUsuarioPorLogin(String login) throws DataException;
	
	/**
	 * Busca todos os usuarios ativos
	 * @return List<Usuario>
	 * @throws DataException
	 */
	List<Usuario> findAllAtivos() throws DataException;

	/**
	 * busca usuarios por nome
	 * @param nome
	 * @return
	 * @throws DataException
	 */
	List<Usuario> buscaUsuarioPorNome(String nome) throws DataException;
	
}

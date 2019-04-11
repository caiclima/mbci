package br.com.mbci.service;

import java.util.List;

import br.com.mbci.dao.IGenericDAO;
import br.com.mbci.dao.exceptions.DataException;
import br.com.mbci.service.exceptions.ServiceException;
import br.com.mbci.service.exceptions.ValidationException;
import br.com.mbci12.pojo.Usuario;

public interface IUsuarioService extends IGenericService<Usuario, IGenericDAO<Usuario>>{

	/**
	 * verifica se existe um Usuario com o login informado
	 * @param login
	 * @return
	 */
	Boolean usuarioExiste(String login);

	/**
	 * Salva registro de usuario na base de dados
	 * @param logUid
	 * @param password
	 * @return
	 * @throws ServiceException
	 * @throws ValidationException
	 */
	Usuario save(String logUid, String password)  throws ServiceException, ValidationException;
	
	/**
	 * retorna o Usuario pelo Login informado
	 * @param login
	 * @return
	 * @throws DataException
	 */
	Usuario buscaUsuarioPorLogin(String login) throws ServiceException;
	
	/**
	 * Busca todos os usuarios ativos
	 * @return List<Usuario>
	 * @throws ServiceException
	 */
	List<Usuario> findAllAtivos() throws ServiceException;

	/**
	 * Busca usuarios pelo nome informado
	 * @param nome
	 * @return List<Usuario>
	 * @throws ServiceException
	 */
	List<Usuario> buscaUsuarioPorNome(String nome) throws ServiceException;


}

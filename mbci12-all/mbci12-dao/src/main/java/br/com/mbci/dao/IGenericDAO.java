package br.com.mbci.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import br.com.mbci.dao.exceptions.DataException;

public interface IGenericDAO<T> extends Serializable{

	/**
	 * Localiza todos os objetos do tipo T.
	 * 
	 * @return Colecao de objetos do tipo T.
	 * @throws DataException
	 */
	List<T> findAll() throws DataException;

	/**
	 * Salva um objeto do tipo T.
	 * 
	 * @param object
	 *            o objeto a ser salvo
	 * @throws DataException
	 */
	void save(final T object) throws DataException;

	/**
	 * Atualiza o objeto do tipo T.
	 * 
	 * @param object
	 *            o objeto a ser atualizado
	 * @throws DataException
	 *             LanÃ§ada quando a validacao nao ocorre com sucesso.
	 */
	void update(final T object) throws DataException;

	/**
	 * Salva o objeto do tipo T enviado, verificando se o mesmo possui a
	 * Annotation: javax.persistence.Id.class capturando o seu valor realizando
	 * a chamada do metodo save (id == null) ou update(id != null).
	 * 
	 * @param object
	 * @throws DataException
	 */
	void saveOrUpdate(final T object) throws DataException;

	/**
	 * Verifica se o objeto enviado possui a Annotation:
	 * javax.persistence.Id.class capturando o seu valor.
	 * 
	 * @param object
	 * @return boolean 
	 * @throws Exception
	 */
	boolean possuiIdComValor(T object) throws Exception;

	/**
	 * Exclui o objeto do tipo T.
	 * 
	 * @param object
	 *            o objeto a ser excluido
	 * @throws DataException
	 *             LanÃ§ada quando a validacao nao ocorre com sucesso.
	 */
	void delete(final T object) throws DataException;

	/**
	 * Carrega o objeto do tipo T.
	 * 
	 * @param object
	 *            objeto do tipo T com o atributo ID setado.
	 * @return o objeto do tipo T com todos os atributos preenchidos.
	 * @throws DataException
	 */
	T findByPk(Object object) throws DataException;

	/**
	 * Carrega os objetos com propriedades iguais às que foram passadas no
	 * objeto de exemplo.
	 * 
	 * @param object
	 * @return
	 * @throws DataException
	 */
	List<T> findByExample(T object) throws DataException;

	/**
	 * Carrega os objetos conforme query e os paramentros enviados.
	 * 
	 * @param query
	 * @param params
	 * @return List&lt;T&gt;
	 * @throws DataException
	 */
	List<T> findByNamedQuery(final String query, Object... params) throws DataException;

	/**
	 * Carrega os objetos conforme query e os paramentros enviados.
	 * 
	 * @param name
	 * @param params
	 * @return List&lt;T&gt;
	 */
	List<T> findByNamedQueryAndNamedParams(final String name, final Map<String, ? extends Object> params)
			throws DataException;
}

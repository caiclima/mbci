package br.com.mbci.service;

import java.io.Serializable;
import java.util.List;

import br.com.mbci.dao.IGenericDAO;
import br.com.mbci.service.exceptions.ServiceException;
import br.com.mbci.service.exceptions.ValidationException;

public interface IGenericService<T, DAO extends IGenericDAO<T>> extends Serializable {

    /**
     * Localiza todos os objetos do tipo T.
     *
     * @return Colecao de objetos do tipo T.
     * @throws ServiceException
     */
    List<T> findAll() throws ServiceException;

    T findByPk(Object object) throws ServiceException;

    /**
     * Salva um objeto do tipo T.
     *
     * @param object o objeto a ser salvo
     * @throws ServiceException
     */
    void save(final T object) throws ServiceException, ValidationException;

    /**
     * Salva ou atualiza um objeto do tipo T.
     *
     * @param object o objeto a ser salvo ou atualizado.
     * @throws ServiceException LanÃ§ada quando a validacao nao ocorre com
     * sucesso.
     */
    void saveOrUpdate(final T object) throws ServiceException, ValidationException;

    /**
     * Exclui o objeto do tipo T.
     *
     * @param object o objeto a ser excluido
     * @throws ServiceException LanÃ§ada quando a validacao nao ocorre com
     * sucesso.
     */
    void delete(final T object) throws ServiceException, ValidationException;

    /**
     *
     * @param object
     * @return
     * @throws br.com.callink.center.coreutils.service.exception
     */
    T get(final T object) throws ServiceException;

    /**
     * Carrega o objeto do tipo T.
     *
     * @param object objeto do tipo T com o atributo ID setado.
     * @return o objeto do tipo T com todos os atributos preenchidos.
     * @throws ServiceException
     */
    T load(T object) throws ServiceException;

    /**
     * Carrega os objetos com propriedades iguais a que foi passada no objeto de
     * exemplo.
     *
     * @param object
     * @return
     * @throws ServiceException
     */
    List<T> findByExample(T object) throws ServiceException;
}

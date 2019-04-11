package br.com.mbci.service.impl;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import br.com.mbci.dao.IGenericDAO;
import br.com.mbci.dao.exceptions.DataException;
import br.com.mbci.service.exceptions.ServiceException;
import br.com.mbci.service.exceptions.ValidationException;

public abstract class GenericService<T, DAO extends IGenericDAO<T>> {

    protected abstract DAO getDAO();

    /**
     * Validacao executada antes de saltar o objeto do tipo T.
     *
     * @param object o objeto do tipo T que sera validado
     * @throws ServiceException LanÃ§ada quando a validacao nao ocorre com
     * sucesso.
     */
    protected abstract void validarSave(T object) throws ValidationException;

    /**
     * Validacao executada antes de atualizar o objeto do tipo T.
     *
     * @param object o objeto do tipo T que sera validado
     * @throws ServiceException LanÃ§ada quando a validacao nao ocorre com
     * sucesso.
     */
    protected abstract void validarUpdate(T object) throws ValidationException;

    /**
     * Validacao executada antes de excluir o objeto do tipo T.
     *
     * @param object o objeto do tipo T que sera validado
     * @throws ServiceException LanÃ§ada quando a validacao nao ocorre com
     * sucesso.
     */
    protected abstract void validarDelete(T object) throws ValidationException;

    public List<T> findAll() throws ServiceException {
        try {
            return getDAO().findAll();
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    public T findByPk(Object object) throws ServiceException {
        try {
            return getDAO().findByPk(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(T object) throws ServiceException, ValidationException {
        try {
            validarSave(object);
            getDAO().save(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    public void saveOrUpdate(T object) throws ServiceException, ValidationException {
        try {
            if (getDAO().possuiIdComValor(object)) {
                update(object);
            } else {
                save(object);
            }
        } catch (ValidationException e) {
            throw e;
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(T object) throws ServiceException, ValidationException {
        try {
            validarUpdate(object);
            getDAO().update(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(T object) throws ServiceException, ValidationException {
        try {
            validarDelete(object);
            getDAO().delete(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    public T get(T object) throws ServiceException {
        try {
            return getDAO().findByPk(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    public T load(T object) throws ServiceException {
        try {
            return getDAO().findByPk(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }

    public List<T> findByExample(T object) throws ServiceException {
        try {
            return getDAO().findByExample(object);
        } catch (DataException e) {
            throw new ServiceException(e);
        }
    }
}

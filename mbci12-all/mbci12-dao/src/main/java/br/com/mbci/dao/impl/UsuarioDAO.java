package br.com.mbci.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.com.mbci.dao.IUsuarioDAO;
import br.com.mbci.dao.exceptions.DataException;
import br.com.mbci.utils.impls.Constantes;
import br.com.mbci12.pojo.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> implements IUsuarioDAO {

	private static final long serialVersionUID = 9198295922135885057L;

	private static Logger logger = Logger.getLogger(UsuarioDAO.class);

	public UsuarioDAO() {
		super(Usuario.class);
	}

	public UsuarioDAO(EntityManager em) {
		super(Usuario.class);
		this.em = em;
	}

	public UsuarioDAO(Class<Usuario> entityClass) {
		super(entityClass);
	}

	@PersistenceContext(unitName = Constantes.UNIT_NAME)
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findByExample(Usuario usuario) throws DataException {
		if (logger.isDebugEnabled()) {
			logger.debug("findByExample - usuario");
			logger.debug("Parâmetros: " + String.format("%s", usuario));
		}
		
		List<Usuario> listAgente = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT distinct pojo FROM Usuario pojo WHERE 1=1 ");

			if (null != usuario.getIdUsuario()) {
				sql.append(" and pojo.idUsuario = :id");
			}
			if (null != usuario.getLogin()) {
				sql.append(" and usuario.login like :login");
			}

			Query q = getEntityManager().createQuery(sql.toString(), getEntityClass());

			if (null != usuario.getIdUsuario()) {
				q.setParameter("id", usuario.getIdUsuario());
			}
			if (null != usuario.getLogin()) {
				q.setParameter("nome", "%" + usuario.getLogin() + "%");
			}
			
			listAgente = q.getResultList();
//			List<Agente> listToRemove = new ArrayList<Agente>();
//			if (null != agente.getEquipeList()) {
//				for (Agente a : listAgente) {
//					if (a.getEquipeList().isEmpty()) {
//						listToRemove.add(a);
//					} else {
//						boolean remover = true;
//						for (Equipe e : agente.getEquipeList()) {
//							if (a.getEquipeList().toString()
//									.contains(e.getNome())) {
//								remover = false;
//								break;
//							}
//						}
//						if (remover) {
//							listToRemove.add(a);
//						}
//					}
//				}
//				listAgente.removeAll(listToRemove);
//			}
			return listAgente;

		} catch (Exception ex) {
			logger.error("findByExample - agente");
			logger.error("", ex);
			logger.error("Parâmetros: " + String.format("%s", usuario));
			throw new DataException(ex);
		}
	}



	public Usuario buscaUsuarioPorLogin(String login) throws DataException {
		if (logger.isDebugEnabled()) {
			logger.debug("getUsuarioPorLogin - login");
			logger.debug("Parâmetros: " + String.format("%s", login));
		}
		try {
			Query q = getEntityManager()
					.createQuery(
							"SELECT pojo FROM Usuario pojo where pojo.login = :login ",
							getEntityClass());
			q.setParameter("login", login);
			return (Usuario) q.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			logger.error("getUsuarioPorLogin - login");
			logger.error("", ex);
			logger.error("Parâmetros: " + String.format("%s", login));
			throw new DataException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> findAllAtivos() throws DataException {
		if (logger.isDebugEnabled()) {
			logger.debug("findAllAtivos ");
		}
		List<Usuario> ret = null;
		try {
			Query q = getEntityManager().createQuery(
					"SELECT pojo FROM Usuario pojo where pojo.isEnabled = true",
					getEntityClass());
			ret = q.getResultList();
		} catch (Exception ex) {
			logger.error("findAllAtivos ");
			logger.error("",ex);
			throw new DataException(ex);
		}
		if (ret == null) {
			ret = new ArrayList<Usuario>();
		}
		return ret;
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> buscaUsuarioPorNome(String nome) throws DataException {
		if (logger.isDebugEnabled()) {
			logger.debug("buscaUsuarioPorNome - login");
			logger.debug("Parâmetros: " + String.format("%s", nome));
		}
		try {
			Query q = getEntityManager()
					.createQuery(
							"SELECT pojo FROM Usuario pojo where pojo.nome = :nome ",
							getEntityClass());
			q.setParameter("login", nome);
			return (List<Usuario>) q.getResultList();
		} catch (NoResultException ex) {
			return null;
		} catch (Exception ex) {
			logger.error("buscaUsuarioPorNome - login");
			logger.error("", ex);
			logger.error("Parâmetros: " + String.format("%s", nome));
			throw new DataException(ex);
		}
	}
}

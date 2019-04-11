package br.com.mbci.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import br.com.mbci.dao.exceptions.DataException;
import br.com.mbci.utils.impls.ReflectionUtils;

public abstract class GenericDAO<T> implements Serializable{
	protected static final int START = 1;
	protected static final int END = 2;
	protected static final int ANYWHERE = 3;
	protected static final int EXACT = 4;
	
	private static final long serialVersionUID = 3039641109292922198L;
	
	private Class<T> entityClass;

	protected abstract EntityManager getEntityManager();

	public int getMatchMode(){
		return ANYWHERE;
	}
	public GenericDAO(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> findAll() throws DataException {
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));

		return getEntityManager().createQuery(cq).getResultList();
	}

	public void save(T entity) throws DataException {
		getEntityManager().persist(entity);
	}

	public void update(T entity) throws DataException {
		getEntityManager().merge(entity);
	}

	public void saveOrUpdate(T object) throws DataException {
		try {
			if (possuiIdComValor(object)) {
				update(object);
			} else {
				save(object);
			}
		} catch (Exception e) {
			throw new DataException(e);
		}

	}

	public boolean possuiIdComValor(T object) throws Exception {
		if (object != null) {
			List<Field> fields = ReflectionUtils.getAllFieldsByClass(entityClass);
			for (Field field : fields) {
				if (field.isAnnotationPresent(javax.persistence.Id.class)) {
					if (ReflectionUtils.getValue(object, field) != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void delete(T entity) throws DataException {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public T findByPk(Object id) throws DataException {
		return getEntityManager().find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQuery(final String name, Object... params) {
		Query query = getEntityManager().createNamedQuery(name);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		return (List<T>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNamedQueryAndNamedParams(final String name, final Map<String, ? extends Object> params) {

		Query query = getEntityManager().createNamedQuery(name);

		for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		return (List<T>) query.getResultList();
	}

	public List<T> findByExample(T example) throws DataException {
		try {
			Class<T> clazz = entityClass;
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
			Root<T> root = criteriaQuery.from(clazz);
			Predicate predicate = criteriaBuilder.conjunction();
			Metamodel metaModel = getEntityManager().getMetamodel();
			EntityType<T> et = metaModel.entity(clazz);
			Set<Attribute<? super T, ?>> attrs = et.getAttributes();
			for (Attribute<? super T, ?> atributo : attrs) {
				String name = atributo.getName();
				String javaName = atributo.getJavaMember().getName();
				String getter = "get" + javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
				Method metodo = clazz.getMethod(getter, (Class<?>[]) null);
				Object valorCampo = metodo.invoke(example, (Object[]) null);

				if (valorCampo != null && !metodo.isAnnotationPresent(Transient.class)) {
					if (valorCampo.getClass().equals(String.class)) {
						if (!valorCampo.toString().trim().equals("")) {
							predicate = criteriaBuilder.and(predicate,
									criteriaBuilder.like(root.<String> get(name), getValueMatchMode(valorCampo)));
						}
					} else {
						predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get(name), valorCampo));
					}
				}
			}
			criteriaQuery.select(root).where(predicate);
			TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery);
			return query.getResultList();

		} catch (Exception e) {
			throw new DataException(e);
		}
	}

	private String getValueMatchMode(Object valorCampo) {
		if(getMatchMode() == ANYWHERE){
			return "%" + valorCampo + "%";
		}else if(getMatchMode() == START){
			return valorCampo + "%";
		}else if(getMatchMode() == END){
			return "%" + valorCampo;
		}else{
			return valorCampo + "";
		}
	}
}

package ch.bfh.awebt.login.persistence;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import ch.bfh.awebt.login.persistence.data.PersistentObject;

/**
 * Represents a data access object for a generic entity type.
 * Provides CRUD operations for generic percistence
 *
 * @param <T> type of the entities
 * @param <K> type of the unique entity identifiers
 *
 *
 */
public abstract class GenericDAO<T extends PersistentObject<K>, K> {

	/**
	 * Gets the name of the persistence unit to use.
	 */
	public static final String PERSISTENCE_UNIT = "JSFLogin";

	private static EntityManager _entityManager;

	/**
	 * Gets an entity manager for the configured.
	 *
	 * @return entity manager for the configured persistence unit
	 */
	protected static EntityManager getEntityManager() {

		if (_entityManager == null)
			_entityManager = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT).createEntityManager();

		return _entityManager;
	}

	/**
	 * Gets the class of the entities.
	 *
	 * @return class of the entities
	 */
	protected abstract Class<T> getEntityClass();

	/**
	 * persists an entity in the data source.
	 *
	 * @param entity entity to persist
	 *
	 * @return persisted entity
	 */
	public T persist(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entityManager.persist(entity);
			transaction.commit();

			return entity;

		} catch (Exception ex) {
			if (transaction.isActive())
				transaction.rollback();

			throw new PersistenceException("Could not persist the entity.", ex);
		}
	}

	/**
	 *  finds an entity in the data source by its unique identifier.
	 *
	 * @param id unique identifier of the entity
	 *
	 * @return entity with the specified unique identifier
	 */
	public T find(K id) {

		return getEntityManager().find(getEntityClass(), id);
	}

	/**
	 * finds all entities in the data source.
	 *
	 * @return {@link List} of all entities
	 */
	public List<T> findAll() {

		CriteriaQuery<T> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(getEntityClass());
		TypedQuery<T> query = getEntityManager().createQuery(criteriaQuery.select(criteriaQuery.from(getEntityClass())));

		return query.getResultList();
	}

	/**
	 *  finds entities in the the data source
	 *
	 * @param name name of the query to use
	 *
	 * @return a list with the found entities
	 */
	protected List<T> findByQuery(String name) {

		return findByQuery(name, null);
	}

	/**
	 * Read: finds entities in the the data source
	 *
	 * @param name       name of the query to use
	 * @param parameters parameters to pass to the query
	 * @param <P>        type of the parameters
	 *
	 * @return a list with the found entities
	 */
	protected <P> List<T> findByQuery(String name, Map<String, P> parameters) {

		TypedQuery<T> query = getEntityManager().createNamedQuery(name, getEntityClass());
		if (parameters != null)
			parameters.forEach(query::setParameter);

		return query.getResultList();
	}

	/**
	 *  refreshes an entity with the data source.
	 *
	 * @param entity entity to refresh
	 *
	 * @return refreshed entity
	 */
	public T refresh(T entity) {

		getEntityManager().refresh(entity);
		return entity;
	}

	/**
	 *  merges an entity into the data source.
	 *
	 * @param entity entity to update
	 *
	 * @return merged entity
	 */
	public T merge(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entity = entityManager.merge(entity);
			transaction.commit();

			return entity;

		} catch (Exception ex) {
			if (transaction.isActive())
				transaction.rollback();

			throw new PersistenceException("Could not merge the entity.", ex);
		}
	}

	/**
	 *  removes an entity from the data source.
	 *
	 * @param entity entity to remove
	 */
	public void remove(T entity) {

		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		transaction.begin();

		try {
			entityManager.remove(entityManager.merge(entity));
			transaction.commit();

		} catch (Exception ex) {
			if (transaction.isActive())
				transaction.rollback();

			throw new PersistenceException("Could not remove the entity.", ex);
		}
	}
}

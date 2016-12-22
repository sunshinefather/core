package com.palmlink.core.database;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.palmlink.core.util.StopWatch;

/**
 * Common Hibernate operations encapsulate
 * 
 * @author Shihai.Fu
 * 
 */
public class HibernateAccess {

    private final Logger logger = LoggerFactory.getLogger(HibernateAccess.class);

    private SessionFactory sessionFactory;

    /**
     * find list with hql
     * 
     * @param hql
     * @param params
     */
    public <T> List<T> find(String hql, Object... params) {
        return find(new HibernateQuery(hql, null, null), params);
    }

    /**
     * find pagination list
     * 
     * @param hql
     * @param firstResult
     * @param maxResults
     * @param params
     */
    public <T> List<T> find(String hql, Integer firstResult, Integer maxResults, Object... params) {
        return find(new HibernateQuery(hql, firstResult, maxResults), params);
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> find(HibernateQuery hibernateQuery, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = hibernateQuery.createQuery(currentSession(), params);
            return (List<T>) query.list();
        } finally {
            logger.debug("find, hibernateQuery={}, params={}, elapsedTime={}", new Object[] { hibernateQuery, params, watch.elapsedTime() });
        }
    }

    /**
     * find list with DetachedCriteria
     * 
     * @param detachedCriteria
     */
    public <T> List<T> find(DetachedCriteria detachedCriteria) {
        return find(detachedCriteria, null, null);
    }

    /**
     * find pagination list with DetachedCriteria
     * 
     * @param detachedCriteria
     * @param firstResult
     * @param maxResults
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> find(DetachedCriteria detachedCriteria, Integer firstResult, Integer maxResults) {
        StopWatch watch = new StopWatch();
        try {
            Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
            if (firstResult != null)
                criteria.setFirstResult(firstResult);
            if (maxResults != null)
                criteria.setMaxResults(maxResults);
            return criteria.list();
        } finally {
            logger.debug("find, criteria={}, firstResult={}, maxResults={}, elapsedTime={}", new Object[] { String.valueOf(detachedCriteria), firstResult, maxResults, watch.elapsedTime() });
        }
    }

    /**
     * find unique record with DetachedCriteria, null will be returned if not
     * exists
     * 
     * @param detachedCriteria
     */
    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(DetachedCriteria detachedCriteria) {
        StopWatch watch = new StopWatch();
        try {
            Criteria criteria = detachedCriteria.getExecutableCriteria(currentSession());
            List<T> list = criteria.list();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            logger.debug("findUniqueResult, criteria={}, elapsedTime={}", String.valueOf(detachedCriteria), watch.elapsedTime());
        }
    }

    /**
     * find unique record with hql, null will be returned if not exists
     * 
     * @param hql
     * @param params
     */
    @SuppressWarnings("unchecked")
    public <T> T findUniqueResult(String hql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = currentSession().createQuery(hql);
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
            List<T> list = query.list();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            logger.debug("findUniqueResult, hql={}, params={}, elapsedTime={}", new Object[] { hql, params, watch.elapsedTime() });
        }
    }

    /**
     * get with id
     * 
     * @param entityClass
     * @param id
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> entityClass, Serializable id) {
        StopWatch watch = new StopWatch();
        try {
            return (T) currentSession().get(entityClass, id);
        } finally {
            logger.debug("get, entityClass={}, id={}, elapsedTime={}", new Object[] { entityClass.getName(), id, watch.elapsedTime() });
        }
    }

    /**
     * persist entity
     * 
     * @param entity
     */
    public void persist(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().persist(entity);
        } finally {
            logger.debug("persist, entityClass={}, value={}, elapsedTime={}", new Object[] { entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime() });
        }
    }

    /**
     * update entity
     * 
     * @param entity
     */
    public void update(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().update(entity);
        } finally {
            logger.debug("update, entityClass={}, value={}, elapsedTime={}", new Object[] { entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime() });
        }
    }

    /**
     * delete entity
     * 
     * @param entity
     */
    public void delete(Object entity) {
        StopWatch watch = new StopWatch();
        try {
            currentSession().delete(entity);
        } finally {
            logger.debug("delete, entityClass={}, value={}, elapsedTime={}", new Object[] { entity.getClass().getName(), String.valueOf(entity), watch.elapsedTime() });
        }
    }

    /**
     * execute query
     * 
     * @param hql
     * @param params
     */
    public int execute(String hql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            Query query = new HibernateQuery(hql, null, null).createQuery(currentSession(), params);
            return query.executeUpdate();
        } finally {
            logger.debug("execute, hql={}, params={}, elapsedTime={}", new Object[] { hql, params, watch.elapsedTime() });
        }
    }

    /**
     * get current session
     * 
     * @return
     */
    public Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}

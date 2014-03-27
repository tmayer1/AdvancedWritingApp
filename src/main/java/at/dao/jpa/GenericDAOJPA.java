/*
    This file is part of Advanced Writing App.
  
    Advanced Writing App is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Advanced Writing App is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Advanced Writing App.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.dao.jpa;

import at.dao.GenericDAO;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


// JPA-Implementation of the GenericDAO-Interface

public abstract class GenericDAOJPA<E, ID extends Serializable> implements GenericDAO<E, ID> {
    
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(GenericDAOJPA.class);
    
    protected Class<E> entityClass = null;
    protected String findAllQueryString = null;

    @PersistenceContext
    protected EntityManager entityManager;
    
    
    public GenericDAOJPA() {
        
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        
        this.findAllQueryString = "SELECT e FROM " + this.entityClass.getSimpleName() + " e";
    }
    
    
    @Override
    public E create(E entity) {
        this.entityManager.persist(entity);
        return entity;
    }
    
    @Override
    public E findById(ID id) {
        return this.entityManager.find(entityClass, id);
    }
    
    @Override
    public List<E> findAll() {
        return (List<E>) this.entityManager.createQuery(this.findAllQueryString).getResultList();
    }

    @Override
    public List<E> find(String where) {
        return (List<E>) this.entityManager.createQuery("SELECT e FROM " + this.entityClass.getSimpleName() + " e WHERE " + where).getResultList();
    }
    
    @Override
    public E update(E entity) {
        return this.entityManager.merge(entity);
    }
    
    @Override
    public void delete(E entity) {
        this.entityManager.remove(entity);
    }
    
    @Override
    public void flush() {
    	this.entityManager.flush();
    }
    
    @Override
    public void detach(E entity) {
    	this.entityManager.detach(entity);
    }
}

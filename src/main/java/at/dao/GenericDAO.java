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

package at.dao;

import java.io.Serializable;
import java.util.List;


public interface GenericDAO<E, ID extends Serializable> {

        //create
	
	public E create(E entity);              
        
        
        //read
        
        public E findById(ID id);               

	public List<E> findAll();

	public List<E> find(String where);

        
        //update
        
	public E update(E entity);
        
        
        //delete

	public void delete(E entity);
        
        public void detach(E entity);
        
        
        public void flush();

}
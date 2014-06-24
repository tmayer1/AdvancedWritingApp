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
    along with Advanced Writing App. If not, see <http://www.gnu.org/licenses/>.
 */

package at.service;

import at.model.Author;
import org.springframework.transaction.annotation.Transactional;

/**
 * Interface for a service used to deal with instances of <code>Author</code> 
 * (business logic).
 * 
 * 
 * @author Thomas Mayer
 */
public interface AuthorService {
    
    @Transactional
    public boolean addAuthor(Author author);
    
    public void removeAuthor(Author autor);
    
    public Author getRegisteredAuthor(String matnr);
    
    @Transactional
    public Author updateAuthor(Author author);

    @Transactional
    public void testDBConnection();
    
}

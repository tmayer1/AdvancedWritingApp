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

package at.controller;


/**
 * Interface for a controller used to handle the registration process.
 * In more specific that means that implementations of the interface are 
 * able to save authors into the database as part of the registration 
 * (request-scoped).
 * 
 * 
 * @author Thomas Mayer
 */
public interface RegisterControl {
    
    /**
     * Registers an author with specific information.
     * <p>
     * Precondition: An author with the same registrationnumber is not 
     * registered yet (not saved in DB).
     * <p>
     * Postcondition: The registered <code>Author</code>-instance is written to 
     * the DB.
     * 
     * @return the URL of the current page (register, no page change)
     */
    public String register();
  
    
}

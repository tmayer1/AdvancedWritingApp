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
 * Interface for a controller used to handle the login process.
 * In more specific that means that implementations of the interface are 
 * able to process login- and logout-actions on a specific 
 * <code>Author</code>-instance (session-scoped). Furthermore, there is a method
 * to test if the application is connected to the database.
 * 
 * 
 * @author Thomas Mayer
 */
public interface LoginControl {
    
    /**
     * Do a login for a specific author.
     * <p>
     * Precondition: An author must already exist in the DB.
     * <p>
     * Invariants: DB-state (nothing is written to the DB)
     * 
     * @return the URL of the portfolio, otherwise the URL of the index-page
     */
    public String login();

    
    /**
     * Do a logout for a specific author.
     * <p>
     * Precondition: An Author must already be logged in.
     * <p>
     * Postcondition: Session data is cleared.
     * <br/>
     * Postcondition: All author specific data have been saved into the DB.
     * 
     * @return the URL of the index-page
     */
    public String logout();
    
    
    /**
     * Tests whether a connection to the DB is given.
     * <p>
     * Invariants: DB-state (nothing is written to the DB)
     * 
     * @return true if the test is successful, false otherwise
     */
    public boolean testDBConnection();
  
    
}

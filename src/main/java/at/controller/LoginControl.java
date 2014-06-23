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
 * Interface for a controller used to handle the login-process.
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
     * Creates a new paper for a specific author.
     * <p>
     * Precondition: An Author must already exist 
     * (PaperControlImpl.author != null).
     * <p>
     * Postcondition: One paper for a specific author has been created 
     * (PaperControlImpl.author.papers.size++).
     * <p>
     * Invariants: DB-state (nothing is written to the DB)
     * 
     * @return 
     */
    public String login();

    
    /**
     * Edits an already existing paper (sets author's current paper).
     * <p>
     * Precondition: An Author must already exist 
     * (PaperControlImpl.author != null).
     * <br/>
     * Precondition: title != null
     * <br/>
     * Precondition: The paper to edit must already exist in author's paperlist
     * (PaperControlImpl.author.getPaper(title) != null).
     * <p>
     * Postcondition: One paper of a specific author is set as current paper 
     * (PaperControlImpl.currentPaper).
     * <p>
     * Invariants: DB-state (nothing is written to the DB)
     * 
     * @return 
     */
    public String logout();
    
    
    /**
     * Deletes an already existing paper from author's papers 
     * (PaperControlImpl.author.papers).
     * <p>
     * Precondition: An author must already exist 
     * (PaperControlImpl.author != null).
     * <br/>
     * Precondition: title != null
     * <br/>
     * Precondition: The paper to remove must already exist in author's 
     * paperlist
     * (PaperControlImpl.author.getPaper(title) != null).
     * <p>
     * Postcondition: One paper has been removed from authors' paperlist 
     * (PaperControlImpl.author.papers.size--).
     * 
     * @return true if the test is successful, false otherwise
     */
    public boolean testDBConnection();
  
    
}

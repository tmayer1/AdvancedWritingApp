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

package at.controller;


/**
 * Interface for a controller used to handle scientific papers (in this 
 * prototype only seminar thesis).
 * In more specific that means that implementations of the interface are able to create, edit and delete papers to 
 * finally generate a laTex-File out of the model-data.
 * 
 * 
 * @author Thomas Mayer
 */
public interface PaperControl {
    
    /**
     * Creates a new paper for a specific author.
     * <p>
     * Precondition: An Author must already exist (PaperControlImpl.author != null).
     * <p>
     * Postcondition: One paper for a specific author has been created 
     * (PaperControlImpl.author.papers.size++).
     * <p>
     * Invariants: DB-state (nothing is written to the DB)
     * 
     * @return 
     */
    public String createPaper();

    
    /**
     * Edits an already existing paper (sets author's current paper).
     * <p>
     * Precondition: An Author must already exist (PaperControlImpl.author != null).
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
     * @param title the title of the paper which has to be set as current paper
     * 
     * @return 
     */
    public String editPaper(String title);
    
    
    /**
     * Deletes an already existing paper from author's papers (PaperControlImpl.author.papers).
     * <p>
     * Precondition: An author must already exist (PaperControlImpl.author != null).
     * <br/>
     * Precondition: title != null
     * <br/>
     * Precondition: The paper to remove must already exist in author's paperlist
     * (PaperControlImpl.author.getPaper(title) != null).
     * <p>
     * Postcondition: One paper has been removed from authors' paperlist 
     * (PaperControlImpl.author.papers.size--).
     * 
     * @param title the title of the paper which has to be removed
     */
    public void removePaper(String title);
    
    
    /**
     * Saves the actual state of an author. 
     * The Method writes/maps one particular author-instance including papers, 
     * chapters, paragraphs and graphics to the DB.
     * <p>
     * Precondition: An author must already exist (PaperControlImpl.author != null).
     * <br/>
     * Precondition: Access to a database must be ensured (existing DB-connection).
     * <p>
     * Postcondition: The actual author-instance (object) has been stored in the
     * DB. 
     */
    public void saveAll();
    
    
    /**
     * Creates a laTex-file as output.
     * The generated output depends on the the structure and data of a specific 
     * paper (PaperControlImpl.currentPaper).
     * <p>
     * Precondition: An author must already exist (PaperControlImpl.author != null).
     * <br/>
     * Precondition: All necessary attributes of author has to be != null (for word-processing).
     * <br/>
     * Precondition: The current paper has to be set (PaperControlImpl.currentPaper != null).
     * <br/>
     * Precondition: All necessary attributes of currentPaper has to be != null (for word-processing).
     * <p>
     * Postcondition: A latex-Outputfile has been generated out of currentPaper's data. 
     * <p>
     * Invariants: DB-state (nothing is written to the DB)
     */
    public void createOutputFile();
    
}

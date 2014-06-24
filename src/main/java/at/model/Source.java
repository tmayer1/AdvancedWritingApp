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

package at.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

/**
 * This class represents a JPA-entity.
 * It is used for ORM. For more information about the whole data-model have a 
 * look at the ER-diagram.
 * 
 * 
 * @author Thomas Mayer
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Source {
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    protected Integer sourceID;
    
    protected String title;
    protected String authorNames;
    protected String year;
    protected String note;
    
    @ManyToMany(mappedBy="sources")
    protected Set<Author> authors = new HashSet<Author>();
    
    protected int sourcePosNr;
        
    @Transient
    protected boolean showContent = false;
    
    /**
     * @return the id of the source
     */
    public Integer getSourceID() {
        return this.sourceID;
    }

    /**
     * @param sourceID the id to set
     */
    public void sourceID(Integer sourceID) {
        this.sourceID = sourceID;
    }
    
    /**
     * @return the title of the source
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the authorNames
     */
    public String getAuthorNames() {
        return this.authorNames;
    }

    /**
     * @param authorNames the authorNames to set
     */
    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }
    
    /**
     * @return the year of the source
     */
    public String getYear() {
        return this.year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }   

    /**
     * @return the note of the source
     */
    public String getNote() {
        return this.note;
    }

    /**
     * @param note the year to set
     */
    public void setNote(String note) {
        this.note = note;
    }
    
    /**
     * @return the sourcePosNr of the source
     */
    public int getSourcePosNr() {
        return this.sourcePosNr;
    }

    /**
     * @param sourcePosNr the sourcePosNr to set
     */
    public void setSourcePosNr(int sourcePosNr) {
        this.sourcePosNr = sourcePosNr;
    }
    
    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getSourceType() {

        if(this instanceof Book) {
            return "book";
        }
        if(this instanceof Thesis) {
            return "thesis";
        }
        if(this instanceof Misc) {
            return "misc";
        }
        return null;
    }
    
    public void toggleContent() {
        
        if(this.showContent)
            this.showContent = false;
        else
            this.showContent = true;
        
        System.out.println("ShowContent: " + this.showContent);
    }
    
    public boolean showContent() {
        
        return this.showContent;
    }
}

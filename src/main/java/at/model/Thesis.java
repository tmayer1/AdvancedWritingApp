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

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.apache.log4j.Logger;

/**
 * This class represents a JPA-entity.
 * It is used for ORM. For more information about the whole data-model have a 
 * look at the ER-diagram.
 * 
 * 
 * @author Thomas Mayer
 */
@Entity
@Access(AccessType.FIELD)
public class Thesis extends Source implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Thesis.class);
   
    private String school;
    
    private String thesisType;
    
    private int thesisPosNr;
              
    public Thesis() {
       
        log.info("Thesis wurde instanziert...");
    }
    
    /*public void initThesis() {
           
        this.chapterContentPosNr = chapterContentPosNr;
        this.paragraphPosNr = paragraphPosNr;
        
        this.setTitle(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr);
        
        log.info(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr + " wurde initialisiert...");
    }*/
    
    /**
     * @return the school of the thesis
     */
    public String getSchool() {
        return this.school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }
    
    /**
     * @return the thesisType of the thesis
     */
    public String getThesisType() {
        return this.thesisType;
    }

    /**
     * @param thesisType the thesisType to set
     */
    public void setThesisType(String thesisType) {
        this.thesisType = thesisType;
    }
    
    /**
     * @return the thesisPosNr of the thesis
     */
    public int getThesisPosNr() {
        return this.thesisPosNr;
    }

    /**
     * @param thesisPosNr the thesisPosNr to set
     */
    public void setThesisPosNr(int thesisPosNr) {
        this.thesisPosNr = thesisPosNr;
    }
    
    @Override
    public boolean equals(Object other) {
    
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        Thesis thesis = (Thesis) other;
        
        if(!(this.title == thesis.getTitle() || (this.title != null && this.title.equals(thesis.getTitle())))) {
            return false;
        }
        if(!(this.authorNames == thesis.getAuthorNames() || (this.authorNames != null && this.authorNames.equals(thesis.getAuthorNames())))) {
            return false;
        }
        if(!(this.year == thesis.getYear() || (this.year != null && this.year.equals(thesis.getYear())))) {
            return false;
        }
        if(!(this.school == thesis.getSchool() || (this.school != null && this.school.equals(thesis.getSchool())))) {
            return false;
        }
        if(!(this.note == thesis.getNote() || (this.note != null && this.note.equals(thesis.getNote())))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.school != null ? this.school.hashCode() : 0);
        return hash;
    }
}

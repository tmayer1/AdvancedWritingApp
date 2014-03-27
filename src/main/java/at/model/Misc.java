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

package at.model;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Transient;
import org.apache.log4j.Logger;

@Entity
@Access(AccessType.FIELD)
public class Misc extends Source implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Misc.class);
   
    private String howPublished;
    
    private int miscPosNr;
              
    public Misc() {
       
        log.info("Misc wurde instanziert...");
    }
    
    /*public void initMisc() {
           
        this.chapterContentPosNr = chapterContentPosNr;
        this.paragraphPosNr = paragraphPosNr;
        
        this.setTitle(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr);
        
        log.info(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr + " wurde initialisiert...");
    }*/
    
    /**
     * @return the howPublished-string of the misc
     */
    public String getHowPublished() {
        return this.howPublished;
    }

    /**
     * @param howPublished the content to set
     */
    public void setHowPublished(String howPublished) {
        this.howPublished = howPublished;
    }
    
    /**
     * @return the miscPosNr of the misc
     */
    public int getMiscPosNr() {
        return this.miscPosNr;
    }

    /**
     * @param miscPosNr the miscPosNr to set
     */
    public void setMiscPosNr(int miscPosNr) {
        this.miscPosNr = miscPosNr;
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
        Misc misc = (Misc) other;
        
        if(!(this.title == misc.getTitle() || (this.title != null && this.title.equals(misc.getTitle())))) {
            return false;
        }
        if(!(this.authorNames == misc.getAuthorNames() || (this.authorNames != null && this.authorNames.equals(misc.getAuthorNames())))) {
            return false;
        }
        if(!(this.year == misc.getYear() || (this.year != null && this.year.equals(misc.getYear())))) {
            return false;
        }
        if(!(this.howPublished == misc.getHowPublished() || (this.howPublished != null && this.howPublished.equals(misc.getHowPublished())))) {
            return false;
        }
        if(!(this.note == misc.getNote() || (this.note != null && this.note.equals(misc.getNote())))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.howPublished != null ? this.howPublished.hashCode() : 0);
        return hash;
    }
}

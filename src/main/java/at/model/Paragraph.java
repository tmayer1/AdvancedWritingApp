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
import java.util.ResourceBundle;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
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
public class Paragraph extends ChapterContent implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Paragraph.class);
   
    @Column(length=10000)
    private String content;
    
    private int paragraphPosNr;
              
    public Paragraph() {
       
        log.info(ResourceBundle.getBundle("i18n").getString("paragraph") + " wurde instanziert...");
    }
    
    public void initParagraph(int chapterContentPosNr, int paragraphPosNr) {
           
        this.chapterContentPosNr = chapterContentPosNr;
        this.paragraphPosNr = paragraphPosNr;
        
        this.setTitle(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr);
        
        log.info(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr + " wurde initialisiert...");
    }
    
    /**
     * @return the content of the paragraph
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
        
    public void inputValueChanged(ValueChangeEvent e) {
        
        this.content = e.getNewValue().toString();
    }
    
    public void explicitSettingContent(String content) {
       
        this.content = content;
    }

    public boolean isParagraph() {

        if (this instanceof Paragraph) {
            return true;
        } else {
            return false;
        }
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
        Paragraph paragraph = (Paragraph) other;
        
        if(!(this.title == paragraph.getTitle() || (this.title != null && this.title.equals(paragraph.getTitle())))) {
            return false;
        }
        if(!(this.content == paragraph.getContent() || (this.content != null && this.content.equals(paragraph.getContent())))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.content != null ? this.content.hashCode() : 0);
        return hash;
    }
}

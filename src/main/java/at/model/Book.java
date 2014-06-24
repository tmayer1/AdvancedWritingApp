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
public class Book extends Source implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Book.class);
   
    private String isbn;
    private String publisher;
    private String address;
    private String edition;
    private String series;
    
    private int bookPosNr;
              
    public Book() {
       
        log.info("Book wurde instanziert...");
    }
    
    /*public void initThesis() {
           
        this.chapterContentPosNr = chapterContentPosNr;
        this.paragraphPosNr = paragraphPosNr;
        
        this.setTitle(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr);
        
        log.info(ResourceBundle.getBundle("i18n").getString("paragraph") + " " + this.paragraphPosNr + " wurde initialisiert...");
    }*/
    
    /**
     * @return the isbn of the book
     */
    public String getISBN() {
        return this.isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setISBN(String isbn) {
        this.isbn = isbn;
    }
    
    /**
     * @return the publisher of the book
     */
    public String getPublisher() {
        return this.publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    
    /**
     * @return the edition of the book
     */
    public String getEdition() {
        return this.edition;
    }

    /**
     * @param edition the edition to set
     */
    public void setEdition(String edition) {
        this.edition = edition;
    }
    
    /**
     * @return the series of the book
     */
    public String getSeries() {
        return this.series;
    }

    /**
     * @param series the series to set
     */
    public void setSeries(String series) {
        this.series = series;
    }
        
    /**
     * @return the address of the book-publisher
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * @return the bookPosNr of the book
     */
    public int getBookPosNr() {
        return this.bookPosNr;
    }

    /**
     * @param bookPosNr the bookPosNr to set
     */
    public void setBookPosNr(int bookPosNr) {
        this.bookPosNr = bookPosNr;
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
        Book book = (Book) other;
        
        if(!(this.title == book.getTitle() || (this.title != null && this.title.equals(book.getTitle())))) {
            return false;
        }
        if(!(this.authorNames == book.getAuthorNames() || (this.authorNames != null && this.authorNames.equals(book.getAuthorNames())))) {
            return false;
        }
        if(!(this.year == book.getYear() || (this.year != null && this.year.equals(book.getYear())))) {
            return false;
        }
        if(!(this.publisher == book.getPublisher() || (this.publisher != null && this.publisher.equals(book.getPublisher())))) {
            return false;
        }
        if(!(this.address == book.getAddress() || (this.address != null && this.address.equals(book.getAddress())))) {
            return false;
        }
        if(!(this.edition == book.getEdition() || (this.edition != null && this.edition.equals(book.getEdition())))) {
            return false;
        }
        if(!(this.series == book.getSeries() || (this.series != null && this.series.equals(book.getSeries())))) {
            return false;
        }
        if(!(this.note == book.getNote() || (this.note != null && this.note.equals(book.getNote())))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.publisher != null ? this.publisher.hashCode() : 0);
        hash = 59 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 59 * hash + (this.edition != null ? this.edition.hashCode() : 0);
        hash = 59 * hash + (this.series != null ? this.series.hashCode() : 0);
        return hash;
    }
}

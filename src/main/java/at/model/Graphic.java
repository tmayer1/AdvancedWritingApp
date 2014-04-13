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

import at.utilities.Utilities;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

@Entity
@Access(AccessType.FIELD)
public class Graphic extends ChapterContent implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Graphic.class);
       
   
    @Lob
    @Column(columnDefinition="mediumblob")
    private byte[] image;
    
    private int height;
    private int width;
    
    private String type;
    
    @Transient
    private transient Part file;
    
    private int graphicPosNr;
            
      
    public Graphic() {
       
        log.info(ResourceBundle.getBundle("i18n").getString("graphic") + " wurde instanziert...");
    }
    
    public void initGraphic(int chapterContentPosNr, int graphicPosNr) {
           
        this.chapterContentPosNr = chapterContentPosNr;
        this.graphicPosNr = graphicPosNr;
        
        this.setTitle(ResourceBundle.getBundle("i18n").getString("graphic") + " " + this.graphicPosNr);
        
        log.info(ResourceBundle.getBundle("i18n").getString("graphic") + " " + this.graphicPosNr + " wurde initialisiert...");
    }
    
    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
    
    public Part getFile() {
        return this.file;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getHeightAsString() {
        return String.valueOf(this.height);
    }
    
    public String getWidthAsString() {
        return String.valueOf(this.width);
    }
    
    // Not used
    public String getScaledHeightAsString() {
        
        int scaledHeight = (500 * this.height) / this.width;
        
        return String.valueOf(scaledHeight);
    }
    
    public String getCalcWidthAsString() {
        
        if(this.width < 500) {
            return String.valueOf(this.width);
        }
        else {
            return "500";
        }
    }
    
    public void setFile(Part file) {
        
        if(file != null) {
            
            long fileSize = 0;
            
            // use of fileSize for comparation
            if(this.file != null) {
                fileSize = this.file.getSize();
            }
            
            if (Utilities.validateFileType(file) && file.getSize() != fileSize) {

                this.file = file;

                log.info(file.getContentType());

                InputStream input = null;
                byte[] imageByteArray = null;

                try {
                    input = this.file.getInputStream();
                    imageByteArray = IOUtils.toByteArray(input);  // Apache commons IO.
                    this.setImage(imageByteArray);
                    this.type = file.getContentType();

                    BufferedInputStream in = new BufferedInputStream(file.getInputStream());
                    BufferedImage image = ImageIO.read(in);

                    this.height = image.getHeight();
                    this.width = image.getWidth();

                } 
                catch (IOException ex) {
                    java.util.logging.Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
                }
                finally {
                    if(input != null) {
                        try {
                            input.close();
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(Graphic.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }

    public boolean isParagraph() {
        return false;
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
        Graphic graphic = (Graphic) other;
        
        if(!(this.title == graphic.getTitle() || (this.title != null && this.title.equals(graphic.getTitle())))) {
            return false;
        }
        if(!(this.type == graphic.getType() || (this.type != null && this.type.equals(graphic.getType())))) {
            return false;
        }
        if(this.height != graphic.getHeight()) {
            return false;
        }
        if(!(this.width == graphic.getWidth())) {
            return false;
        }
        if(!(this.image == graphic.getImage() || (this.image != null && Arrays.equals(this.image, graphic.getImage())))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Arrays.hashCode(this.image);
        hash = 67 * hash + this.height;
        hash = 67 * hash + this.width;
        hash = 67 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }
}

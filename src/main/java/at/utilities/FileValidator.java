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

package at.utilities;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;


@FacesValidator("FileValidator")
public class FileValidator implements Validator {
 
    private static Part safedPart = null;
    
    @Override
    public void validate(FacesContext context, UIComponent uiComponent, Object value) throws ValidatorException {
 
        Part part = (Part) value;
 
        
        /* 1. validate file name length 
        String fileName = getFileName(part);
        System.out.println("----- validator fileName: " + fileName);
        if(fileName.length() == 0 ) {
            FacesMessage message = new FacesMessage("Error: File name is invalid !!");
            throw new ValidatorException(message);
        } else if (fileName.length() > 50) {
            FacesMessage message = new FacesMessage("Error: File name is too long !!");
            throw new ValidatorException(message);
        }
        
         */

        if (part.getContentType().equals("application/octet-stream")) {
            if (FileValidator.safedPart == null || part == null) {
                
                FacesMessage message = new FacesMessage(ResourceBundle.getBundle("i18n").getString("nofileselected"));
                throw new ValidatorException(message);
            }
        }
        else {
            this.validateFileType(part);
            //ACCEPTED
            FileValidator.safedPart = part;
        }
        

        /* 3. validate file size (should not be greater than 512 bytes)
         if (part.getSize() > 512) {
         FacesMessage message = new FacesMessage("Error: File size is too big !!");
         throw new ValidatorException(message);
        }
        */
    }
    
    private void validateFileType(Part file) {
        
        // validate file type (only jpeg and png files allowed)
        if (!"image/jpeg".equals(file.getContentType())) {

            if (!"image/png".equals(file.getContentType())) {

                    FacesMessage message = new FacesMessage(ResourceBundle.getBundle("i18n").getString("noimagefile"));
                    throw new ValidatorException(message);

                    //context.addMessage(null, new FacesMessage("Error: File type is invalid!"));
                    //context.validationFailed();
                    //((UIInput) uiComponent).setValid(false);
            }
        }
    }
}
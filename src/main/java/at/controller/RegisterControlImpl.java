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

import at.model.Author;
import at.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller
@Scope("request")
public class RegisterControlImpl implements RegisterControl {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RegisterControlImpl.class);
    
    private Author newAuthor;
        
    @Autowired
    private AuthorService authorService;
    
    private boolean registrationSuccessful;
    
    private boolean authorAlreadyRegistered;

    /**
     * Creates a new instance of RegisterControlImpl
     */
    public RegisterControlImpl() {
        
        this.newAuthor = new Author();
        
        this.registrationSuccessful = false;
        this.authorAlreadyRegistered = false;
        
        log.info("RegisterControl wurde instanziert...");
    }

    @Override
    public String register() {
        
        boolean success = this.authorService.addAuthor(this.newAuthor);

        if (success == true) {
            registrationSuccessful = true;
        } else {
            authorAlreadyRegistered = true;
        }
        return "register";
    }

    /**
     * @return the newAuthor
     */
    public Author getNewAuthor() {
        return newAuthor;
    }

    /**
     * @param newAuthor the newAuthor to set
     */
    public void setNewAuthor(Author newAuthor) {
        this.newAuthor = newAuthor;
    }
    
    /**
     * @return the registrationsuccessful
     */
    public boolean isRegistrationSuccessful() {
        return this.registrationSuccessful;
    }

    /**
     * @param registrationsuccessful the registrationsuccessful to set
     */
    public void setRegistrationSuccessful(boolean registrationSuccessful) {
        this.registrationSuccessful = registrationSuccessful;
    }

    
    public boolean isAuthorAlreadyRegistered() {
        return this.authorAlreadyRegistered;
    }

    public void setAuthorAlreadyRegistered(boolean authorAlreadyRegistered) {
        this.authorAlreadyRegistered = authorAlreadyRegistered;
    }
}

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

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import at.model.Author;
import at.service.AuthorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("loginControl")
@Scope("session")
public class LoginControlImpl {

    private static org.apache.log4j.Logger log = Logger.getLogger(LoginControlImpl.class);
    
    @Autowired
    private PaperControlImpl paperControl;
    
    @Autowired
    private AuthorService authorService;
    
    private Author author = null;
    
    private String matnr;
    private String password;

    public LoginControlImpl() {
        
        log.info("LoginControl wurde instanziert...");
        log.info("JSF-Version: " + FacesContext.class.getPackage().getImplementationVersion());
    }

    
    public String login() {
        
        this.author = this.authorService.getRegisteredAuthor(this.matnr);
        
        if (this.author != null) {
              
            if (!this.author.getIsUsedNow()) {
                
                if (this.password.equals(this.author.getPassword())) {

                    this.paperControl.setAuthor(this.author);
                    this.author.setIsUsedNow(true);
                    this.paperControl.saveAll();

                    return "portfolio?faces-redirect=true";
                }
            }
        }
        return "index?faces-redirect=true";
    }
    
    
    public String logout() {
        
        HttpServletRequest hsr = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        
        String uri = hsr.getRequestURI();
         
        
        this.author.setIsUsedNow(false);
        this.paperControl.saveAll();
        
        
        // To really remove all user specific data 
        hsr.getSession().invalidate();
        // To do not get an error on the next page 
        hsr.getSession(true);
        
        log.info("logout...");
        
        return "index";
    }


    /**
     * @return the author
     */
    public Author getAuthor() {
        return this.author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * @return the registration-number
     */
    public String getMatnr() {
        return this.matnr;
    }
    
    /**
     * @param matnr the registration-number to set
     */
    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return the paperControl
     */
    public PaperControlImpl getPaperControl() {
        return this.paperControl;
    }

    /**
     * @param paperControl the paperControl to set
     */
    public void setPaperControl(PaperControlImpl paperControl) {
        this.paperControl = paperControl;
    }
    
    
    public void createDefaultAuthor() {
                
        Author test = new Author();
        test.setFirstname("Max");
        test.setLastname("Mustermann");
        test.setMatnr("1000000");
        test.setPassword("hallo");
        
        this.author = test;
        
        this.authorService.addAuthor(test);        
    }
}
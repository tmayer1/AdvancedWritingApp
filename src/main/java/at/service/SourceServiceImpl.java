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

package at.service;

import at.dao.SourceDAO;
import at.model.Source;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SourceServiceImpl implements SourceService {

    @Autowired
    private SourceDAO sourceDAO;
        
    /*@Override
    public boolean addAuthor(Author author) {
        
        Author newAuthor = this.authorDAO.findById(author.getMatnr());
        
        if(newAuthor == null) {
            this.authorDAO.create(author);
            return true;
        }
        else {
           return false;
        }
    }
    
    @Override
    public void removeAuthor(Author autor) {
        
        this.authorDAO.delete(autor);
    }

    @Override
    public Author getRegisteredAuthor(String matnr) {
        
        return this.authorDAO.findById(matnr);
    }

    @Override
    public Author updateAuthor(Author author) {
        
        //JPA/Hibernate - Implementation

        if (this.authorStates.size() > 0) {

            Author oldAuthorStateManaged = this.authorDAO.findById(this.authorStates.get(this.authorStates.size() - 1).getMatnr());
           
            //same author?
            if (oldAuthorStateManaged.getMatnr().equals(author.getMatnr())) {
                
                //semantic equals (are there any changes?)
                if (!oldAuthorStateManaged.equals(author)) {
                    //remove last authorState (delete already existing author in DB)
                    this.authorDAO.delete(oldAuthorStateManaged);
                } 
                else {
                    return null;
                }
            }
        }

        //save current authorState
        try {

            Author currentAuthorState = (Author) Utilities.deepCopy(author);

            this.authorStates.add(currentAuthorState);
        } 
        catch (Exception ex) {
            Logger.getLogger(SourceServiceImpl.class.getName()).log(Level.SEVERE, "AuthorState konnte nicht gespeichert werden (tiefe Kopie)...");
        }


        //create currentAuthorStateManaged and save after transaction ends
        Author currentAuthorStateManaged = this.authorDAO.update(author);
        
        return currentAuthorStateManaged;
    }*/

    public boolean addSource(Source source) {
        
        Source newSource = this.sourceDAO.findById(source.getSourceID());
        
        if(newSource == null) {
            this.sourceDAO.create(source);
            return true;
        }
        else {
           return false;
        }
    }

    public void removeSource(Source source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Source getSource(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Source updateSource(Source source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Source> getAllSources() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

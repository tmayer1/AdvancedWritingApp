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

package at.service;

import at.dao.PaperDAO;
import at.model.Paper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service not used in application

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperDAO paperDAO;
    
    
    @Override
    public void createPaper(Paper paper) {
        
        this.paperDAO.create(paper);
    }
    
    @Override
    public Paper getPaper(Integer id) {
        
        return this.paperDAO.findById(id);
    }
    
    @Override
    public List<Paper> getAllPapers() {
        
        return this.paperDAO.findAll();
    }
    
    @Override
    public List<Paper> getPapersByAuthor(String matnr) {
        
        return this.paperDAO.find("e.author.matnr = " +  "'" + matnr +  "'");
    }
        
    @Override
    public void updatePaper(Paper paper) {
        
        this.paperDAO.update(paper);
    }

    @Override
    public void removePaper(Paper paper) {
        this.paperDAO.delete(paper);
    }
    
}

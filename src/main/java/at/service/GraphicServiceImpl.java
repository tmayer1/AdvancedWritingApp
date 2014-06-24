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

import at.dao.GraphicDAO;
import at.model.Graphic;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Service not used in application

@Service
public class GraphicServiceImpl implements GraphicService {

    @Autowired
    private GraphicDAO graphicDAO;
    
    @Override
    public void removeGraphic(Graphic graphic) {

        List<Graphic> allGraphics = this.graphicDAO.findAll();
        
        for(int i=0; i<allGraphics.size(); i++) {
            
            if(allGraphics.get(i) != null && allGraphics.get(i).getChapterContentPosNr() == graphic.getChapterContentPosNr() && 
                    allGraphics.get(i).getChapter().getChapterPosNr() == graphic.getChapter().getChapterPosNr() &&
                    allGraphics.get(i).getTitle().equals(graphic.getTitle())) {
                
                this.graphicDAO.delete(allGraphics.get(i));
                break;
            }
        }
    }
}

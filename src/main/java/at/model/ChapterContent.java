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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class ChapterContent {
    
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    protected Integer chapterContentID;
    
    protected String title;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    protected Chapter chapter;
    
    protected int chapterContentPosNr;
    
    @Transient
    protected boolean showContent = false;
    
    
    /**
     * @return the id of the chapterContent
     */
    public Integer getChapterContentID() {
        return this.chapterContentID;
    }

    /**
     * @param chapterContentID the id to set
     */
    public void chapterContentID(Integer chapterContentID) {
        this.chapterContentID = chapterContentID;
    }
    
    /**
     * @return the title of the chapterContent
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the chapter
     */
    public Chapter getChapter() {
        return this.chapter;
    }

    /**
     * @param chapter the chapter to set
     */
    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    
    /**
     * @return the nr of the chapterContent
     */
    public int getChapterContentPosNr() {
        return this.chapterContentPosNr;
    }

    /**
     * @param nr the posNr to set
     */
    public void setChapterContentPosNr(int nr) {
        this.chapterContentPosNr = nr;
    }
    
    public void toggleContent() {
        
        if(this.showContent)
            this.showContent = false;
        else
            this.showContent = true;
        
        System.out.println("ShowContent: " + this.showContent);
    }
    
    public boolean showContent() {
        
        return this.showContent;
    }
}

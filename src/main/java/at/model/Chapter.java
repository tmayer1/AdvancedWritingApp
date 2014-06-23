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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import org.apache.log4j.Logger;
import at.utilities.Section;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;

@Entity
@Access(AccessType.FIELD)
public class Chapter implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Chapter.class);
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer chapterID;
    
    private String title;
    
    private int chapterPosNr;
    
    @Enumerated(EnumType.STRING)
    private Section section;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Paper paper;
        
    @OneToMany(mappedBy="chapter", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ChapterContent> chapterContents = new HashSet();
    
    @Transient
    private boolean firstChapterInSection;
        
    private int chapterContentPosNr;
    
    private int paragraphPosNr;
    
    private int graphicPosNr;

    public Chapter() {

        int nr = this.chapterPosNr + 1;
        
        log.info(ResourceBundle.getBundle("i18n").getString("chapter") + " " + this.chapterPosNr + " wurde instanziert...");
    }
    
    public void initChapter(int chapterPosNr) {
        
        this.addParagraph();
        
        this.chapterPosNr = chapterPosNr;
        
        log.info(ResourceBundle.getBundle("i18n").getString("chapter") + " " + this.chapterPosNr + " wurde initialisiert...");
    }
    
    public void addParagraph() {
        
        int nr = this.chapterContentPosNr + 1;
        int nr2 = this.paragraphPosNr + 1;
        
        ChapterContent newParagraph = new Paragraph();
        ((Paragraph)newParagraph).initParagraph(nr, nr2);
        newParagraph.setChapter(this);
        
        this.chapterContents.add(newParagraph);
        this.chapterContentPosNr++;
        this.paragraphPosNr++;
    }
    
    public void addGraphic() {
        
        int nr = this.chapterContentPosNr + 1;
        int nr2 = this.graphicPosNr + 1;
        
        ChapterContent newGraphic = new Graphic();
        ((Graphic)newGraphic).initGraphic(nr, nr2);
        newGraphic.setChapter(this);
        
        this.chapterContents.add(newGraphic);
        this.chapterContentPosNr++;
        this.graphicPosNr++;
    }
    
    public Set<ChapterContent> getChapterContentSet() {
        return this.chapterContents;
    }
    
    /**
     * @return the chapters paragraphs
     */
    public List<ChapterContent> getChapterContent() {

        return this.sortChapterContent(this.getListAsSet(this.chapterContents));
    }
    
    public List<ChapterContent> getParagraphs() {
        
        List<ChapterContent> paragraphs = new ArrayList<ChapterContent>();
        
        for (ChapterContent tempChapterContent : this.chapterContents) {
            
            if(tempChapterContent instanceof Paragraph) {
                paragraphs.add(tempChapterContent);
            }
        }
        
        return this.sortChapterContent(paragraphs);  
    }
    
    public List<ChapterContent> getGraphics() {
        
        List<ChapterContent> graphics = new ArrayList<ChapterContent>();
        
        for (ChapterContent tempChapterContent : this.chapterContents) {
            
            if(tempChapterContent instanceof Graphic) {
                graphics.add(tempChapterContent);
            }
        }
        
        return this.sortChapterContent(graphics);  
    }

    private List<ChapterContent> getListAsSet(Set<ChapterContent> set) {
        return new ArrayList<ChapterContent>(set);
    }
    
    public boolean isChapterContentsContentShown(String title) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(title)) {
                
                return tempChapterContent.showContent();
            }
        }
        
        return false;
    }
    
    public boolean toggleChapterContentsContent(String title) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(title)) {
                
                tempChapterContent.toggleContent();
                
                return true;
            }
        }
        
        return false;
    }
    
    public ChapterContent getChapterContent(String title) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(title)) {
                
                return tempChapterContent;
            }
        }
        
        return null;
    }
    
    public Graphic getChapterGraphic(String title) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(title)) {
                
                return (Graphic)tempChapterContent;
            }
        }
        
        return null;
    }
            
    public void removeChapterContent(String title) {

        Iterator it = this.chapterContents.iterator();

        while (it.hasNext()) {

            ChapterContent tempChapterContent = (ChapterContent) it.next();

            if (tempChapterContent != null) {
                if (tempChapterContent.getTitle().equals(title)) {                       
                    
                    if(tempChapterContent instanceof Paragraph) {
                        
                        if (((Paragraph)tempChapterContent).getContent() != null) {
                            
                            this.clearParagraphData(title);
                        }
                        
                        this.paragraphPosNr = this.paragraphPosNr - 1;
                    }
                    else {
                        
                        if (((Graphic)tempChapterContent).getImage() != null) {
                            
                            this.clearGraphicData(title);
                        }
                        
                        this.graphicPosNr = this.graphicPosNr - 1;
                    }
                    
                    this.chapterContents.remove(tempChapterContent);
                    this.chapterContentPosNr = this.chapterContentPosNr - 1;  
                    
                    log.info(tempChapterContent.getTitle() + " wurde entfernt...");
                    break;
                }
            }
        }
    }
    
    // helper-function for clean removing of a paragraph
    private void clearParagraphData(String paragraphTitle) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(paragraphTitle)) {
                
                ((Paragraph)tempChapterContent).setContent(null);
            }
        }
    }
    
    // helper-function for clean removing of a graphic
    private void clearGraphicData(String graphicTitle) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(graphicTitle)) {
                
                tempChapterContent.setChapter(null);
                tempChapterContent.setTitle(null);
                ((Graphic)tempChapterContent).setFile(null);
                ((Graphic)tempChapterContent).setImage(null);
                ((Graphic)tempChapterContent).setType(null);
                break;
            }
        }
    }
        
    public boolean isChapterContentParagraph(String title) {
        
        for (ChapterContent tempChapterContent : this.chapterContents) {

            if(tempChapterContent.getTitle().equals(title)) {
                
                if(tempChapterContent instanceof Paragraph) 
                    return true;
                else
                    return false;
            }
        }
        
        return false;
    }
    
    /**
     * @return the id of the chapter
     */
    public Integer getChapterID() {
        return this.chapterID;
    }

    /**
     * @param chapterID the id to set
     */
    public void setPaperID(Integer chapterID) {
        this.chapterID = chapterID;
    }
    
    /**
     * @return the title of the chapter
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
     * @return the section of the chapter
     */
    public String getSection() {
        return this.section.toString();
    }
    
    /**
     * @return the section of the chapter
     */
    public Section getSectionEnum() {
        return this.section;
    }

    /**
     * @param section the section to set
     */
    public void setSection(String section) {

        if (section.equals("Introduction")) {
            this.section = Section.Introduction;
        } 
        else if (section.equals("Mainpart")) {
            this.section = Section.Mainpart;
        } 
        else if (section.equals("Conclusion")) {
            this.section = Section.Conclusion;
        }
        
        log.info("Section wurde gesetzt: " +  this.section);
    }
    
    
    public boolean isFirstChapterInSection() {
        
        if(this.firstChapterInSection)
            return true;
        else
            return false;
    }
    
    public void setToFirstChapterInSection(boolean firstChapterInSection) {
        this.firstChapterInSection = firstChapterInSection;
    }
    
    /**
     * @return the nr of the chapter
     */
    public int getChapterPosNr() {
        return this.chapterPosNr;
    }

    /**
     * @param nr the posNr to set
     */
    public void setChapterPosNr(int nr) {
        this.chapterPosNr = nr;
    }
    
    /**
     * @return the paper
     */
    public Paper getPaper() {
        return this.paper;
    }

    /**
     * @param paper the paper to set
     */
    public void setPaper(Paper paper) {
        this.paper = paper;
    }
    
    private List<ChapterContent> sortChapterContent(List<ChapterContent> unsortedChapterContent) {
        
        //Sort (Selection-Sort)
        List<ChapterContent> sortedChapterContent = unsortedChapterContent;
        
        int size = sortedChapterContent.size(); //micro for-loop-optimization
        
        for (int i = 0; i < size - 1; i++) {
            
            for (int j = i + 1; j < size; j++) {
                
                if (sortedChapterContent.get(i).getChapterContentPosNr() > sortedChapterContent.get(j).getChapterContentPosNr()) {
                    
                    ChapterContent tempChapterContent = sortedChapterContent.get(i);
                    sortedChapterContent.set(i, sortedChapterContent.get(j));
                    sortedChapterContent.set(j, tempChapterContent);
                }
            }
        }
        
        return sortedChapterContent;
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
        Chapter chapter = (Chapter) other;

        if(!(this.title == chapter.getTitle() || (this.title != null && this.title.equals(chapter.getTitle())))) {
            return false;
        }
        if(!(this.section == chapter.getSectionEnum() || (this.section != null && this.section.equals(chapter.getSectionEnum())))) {
            return false;
        }
        
        if(!this.chapterContentEquals(chapter.getChapterContent())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 79 * hash + (this.section != null ? this.section.hashCode() : 0);
        return hash;
    }
    
    private boolean chapterContentEquals(List<ChapterContent> otherChapterContent) {
        
        List<ChapterContent> chapterContent = this.getChapterContent();
        
        List<ChapterContent> sortedChapterContent = this.sortChapterContent(chapterContent);
        List<ChapterContent> sortedOtherChapterContent = this.sortChapterContent(otherChapterContent);
        
        
        if(sortedChapterContent.size() == sortedOtherChapterContent.size()) {
            
            for(int i=0; i<sortedChapterContent.size(); i++) {
                if(!sortedChapterContent.get(i).equals(sortedOtherChapterContent.get(i))) {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        
        return true;
    }
}

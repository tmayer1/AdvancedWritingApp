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

import at.utilities.GUIUtilities;
import at.utilities.Utilities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.persistence.*;
import org.apache.log4j.Logger;


@Entity
@Access(AccessType.FIELD)
public class Paper implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Paper.class);
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer paperID;
    
    private String title;
    
    private int paperPosNr;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Author author;
        
    @Column(length=2000)
    private String abstractPart;
    
    @OneToMany(mappedBy="paper", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Chapter> chapters = new HashSet();

    private String currentChapterTitle;
    
    private String lang;
    
    private boolean useBibliography;
    
    @Transient
    private Chapter currentChapter;
    
    private int chapterPosNr;
    
    
    public Paper() {
        
        log.info(ResourceBundle.getBundle("i18n").getString("paper") + " " + this.paperPosNr + " wurde instanziert...");
    }
    
    public void initPaper(int paperPosNr) {

        int nr = this.chapterPosNr + 1;

        Chapter newChapter = new Chapter();
        newChapter.setSection("Introduction");
        newChapter.setTitle("Abschnitt " + nr);
        newChapter.setPaper(this);
        newChapter.initChapter(this.chapterPosNr++);
        this.chapters.add(newChapter);
        
        nr++;
        
        Chapter newChapter2 = new Chapter();
        newChapter2.setSection("Mainpart");
        newChapter2.setTitle("Abschnitt " + nr);
        newChapter2.setPaper(this);
        newChapter2.initChapter(this.chapterPosNr++);
        this.chapters.add(newChapter2);
        
        nr++;
        
        Chapter newChapter3 = new Chapter();
        newChapter3.setSection("Conclusion");
        newChapter3.setTitle("Abschnitt " + nr);
        newChapter3.setPaper(this);
        newChapter3.initChapter(this.chapterPosNr++);
        this.chapters.add(newChapter3);
        
        this.paperPosNr = paperPosNr;
        
        this.lang = Utilities.getBrowserLocaleForLaTeX();
        
        this.useBibliography = false;
        
        log.info(ResourceBundle.getBundle("i18n").getString("paper") + " " + this.paperPosNr + " wurde initialisiert...");
    }
    
    public void addChapter(String section) {
        
        int nr = this.chapterPosNr + 1;
        
        Chapter newChapter = new Chapter();
        newChapter.setSection(section);
        newChapter.setTitle("Abschnitt " + nr);
        newChapter.initChapter(nr);
        newChapter.setPaper(this);
        
        this.chapters.add(newChapter);
        this.chapterPosNr++;
    }
    
    public Chapter getChapter(String chapterTitle) {
        
        HashSet<Chapter> chapters = new HashSet<Chapter>();
        for (Chapter chapterTemp : this.chapters) {

            if(chapterTemp.getTitle().equals(chapterTitle)) {
                return chapterTemp;
            }
        }
        return null;
    }

    public List<Chapter> getSectionChapters(String section) {
        
        HashSet<Chapter> sectionChapters = new HashSet<Chapter>();
        for (Chapter chapterTemp : this.chapters) {

            if(chapterTemp.getSection().equals(section)) {
                sectionChapters.add(chapterTemp);
            }
        }
        
        return this.sortChapters(this.getListOfSet(sectionChapters));
    }

    private List<Chapter> getListOfSet(Set<Chapter> set) {
        return new ArrayList<Chapter>(set);
    }
    
    public void removeAllChapters() { 
        
        for(int i=0; i<this.chapters.size(); i++) {
            log.info("Anzahl der Kapitel: " + this.chapters.size());
            this.chapters.remove(i);
        }
        this.chapterPosNr = 0;
    }

    public void removeChapter(String title, String section) {

        Iterator it = this.chapters.iterator();

        while (it.hasNext()) {

            Chapter chapterTemp = (Chapter) it.next();

            if (chapterTemp != null) {
                if (chapterTemp.getTitle().equals(title) && chapterTemp.getSection().equals(section)) {
                    this.chapters.remove(chapterTemp);
                    this.chapterPosNr = this.chapterPosNr - 1;   
                    
                    log.info("Chapter " + chapterTemp.getTitle() + " wurde entfernt...");
                    break;
                }
            }
        }
    }
    
    public boolean setCurrentChapter(String title) {
        
        for (Chapter chapterTemp : this.chapters) {

            if(chapterTemp.getTitle().equals(title)) {
                
                this.currentChapter = chapterTemp;
                this.currentChapterTitle = chapterTemp.getTitle();
                
                log.info("CurrentChapter(Chapter Nr: " + this.currentChapter.getChapterPosNr() + ") wurde gesetzt...");
                
                return true;
            }
        }
        return false;
    }
        
    public Chapter getCurrentChapter() {
        
        return this.currentChapter;
    }
    
    public String getCurrentChapterTitle() {
        
        return this.currentChapterTitle;
    }
    
    /**
     * @return the id of the paper
     */
    public Integer getPaperID() {
        return this.paperID;
    }

    /**
     * @param paperID the id to set
     */
    public void setPaperID(Integer paperID) {
        this.paperID = paperID;
    }
    
    /**
     * @return the nr of the paper
     */
    public int getPaperPosNr() {
        return this.paperPosNr;
    }

    /**
     * @param nr the posNr to set
     */
    public void setPaperPosNr(int nr) {
        this.paperPosNr = nr;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return this.title;
    }
    
    public String getTitleForHeader() {
    
        String uri = GUIUtilities.chooseTitle();
        
        if(uri == null) {
            return this.title;
        }
        else {
            return uri;
        }
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the abstract of the paper
     */
    public String getAbstractPart() {
        return this.abstractPart;
    }

    /**
     * @param abstractPart the text for the abstract to set
     */
    public void setAbstractPart(String abstractPart) {
        this.abstractPart = abstractPart;
    }
    
    /**
     * @return the language of the paper
     */
    public String getLang() {
        return this.lang;
    }

    /**
     * @param lang the language for the paper to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }
    
    /**
     * @return a boolean value which shows wheter the paper is using authors sources for a bibliography at the end of the paper
     */
    public boolean getUseBibliography() {
        return this.useBibliography;
    }

    /**
     * @param useBibliography the useBibliography for the paper to set
     */
    public void setUseBibliography(boolean useBibliography) {
        this.useBibliography = useBibliography;
    }
    
    /**
     * @return the papers chapters
     */
    public Set<Chapter> getChapters() {
        return this.chapters;
    }
    
    public List<Chapter> getChaptersAsList() {
        return this.getListOfSet(this.chapters);
    }
    
    public List<Chapter> getSortedChaptersAsList() {
        return this.sortChapters(this.getListOfSet(this.chapters));
    }
    
    /**
     * @param chapter the chapter to add
     */
    public void addChapter(Chapter chapter) {
       
        this.chapters.add(chapter);
        chapter.setPaper(this);
    }
    
    private List<Chapter> sortChapters(List<Chapter> unsortedChapters) {
        
        //Sort (Selection-Sort)
        List<Chapter> sortedSectionChapters = unsortedChapters;
        
        int size = sortedSectionChapters.size(); //micro for-loop-optimization
        
        for (int i = 0; i < size - 1; i++) {
            
            for (int j = i + 1; j < size; j++) {
                
                if (sortedSectionChapters.get(i).getChapterPosNr() > sortedSectionChapters.get(j).getChapterPosNr()) {
                    
                    Chapter tempChapter = sortedSectionChapters.get(i);
                    sortedSectionChapters.set(i, sortedSectionChapters.get(j));
                    sortedSectionChapters.set(j, tempChapter);
                }
            }
        }
        return sortedSectionChapters;
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
        Paper paper = (Paper) other;
        
        if(!(this.title == paper.getTitle() || (this.title != null && this.title.equals(paper.getTitle())))) {
            return false;
        }
        if(!(this.abstractPart == paper.getAbstractPart() || (this.abstractPart != null && this.abstractPart.equals(paper.getAbstractPart())))) {
            return false;
        }
        if(!(this.chaptersEquals(paper.getChaptersAsList()))) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 97 * hash + (this.abstractPart != null ? this.abstractPart.hashCode() : 0);
        return hash;
    }
    
    private boolean chaptersEquals(List<Chapter> otherChapters) {
        
        List<Chapter> chapters = this.getChaptersAsList();
        
        List<Chapter> sortedChapters = this.sortChapters(chapters);
        List<Chapter> sortedOtherChapters = this.sortChapters(otherChapters);
        
        if(sortedChapters.size() == sortedOtherChapters.size()) {
            
            for(int i=0; i<sortedChapters.size(); i++) {
                if(!sortedChapters.get(i).equals(sortedOtherChapters.get(i))) {
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

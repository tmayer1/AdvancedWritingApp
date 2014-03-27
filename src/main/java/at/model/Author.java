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

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.log4j.Logger;


@Entity
@Access(AccessType.FIELD)
public class Author implements Serializable {
    
    @Transient
    private static org.apache.log4j.Logger log = Logger.getLogger(Author.class);
    
    @Id
    private String matnr;
    private String firstname;
    private String lastname;
    private String password;
    
    @OneToMany(mappedBy="author", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Paper> papers = new HashSet<Paper>();
    
    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="Author_Source", 
                joinColumns={@JoinColumn(name="authorMatnr")}, 
                inverseJoinColumns={@JoinColumn(name="sourceID")})
    private Set<Source> sources = new HashSet<Source>();
    
    private boolean isUsedNow = false;
    
    @Transient
    private int sourcePosNr = 0;
    
    @Transient
    private int bookPosNr = 0;
    
    @Transient
    private int thesisPosNr = 0;
    
    @Transient
    private int miscPosNr = 0;
    
    public Author() {
        
        log.info(ResourceBundle.getBundle("i18n").getString("author") + " " + this.matnr + " wurde instanziert...");
    }
    
     /**
     * @return the registration-number of the author
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
     * @return the firstname of the author
     */
    public String getFirstname() {
        return this.firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    /**
     * @return the lastname of the author
     */
    public String getLastname() {
        return this.lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    /**
     * @return the lastName of the author
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the lastname to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @return isUsedNow of the author
     */
    public boolean getIsUsedNow() {
        return this.isUsedNow;
    }

    /**
     * @param isUsedNow the lastname to set
     */
    public void setIsUsedNow(boolean isUsedNow) {
        this.isUsedNow = isUsedNow;
    }

    /**
     * @return the authors papers
     */
    public Set<Paper> getPapers() {
        return this.papers;
    }
    
    private List<Paper> getListAsSet(Set<Paper> set) {
        return new ArrayList<Paper>(set);
    }
    
    private List<Source> getListAsSet2(Set<Source> set) {
        return new ArrayList<Source>(set);
    }
    
    public List<Paper> getPapersList() {
        return this.getListAsSet(this.papers);
    }
    
    public List<Paper> getSortedPapersList() {
                
        return this.sortPapers(this.getListAsSet(this.papers));
    }
    
    public Paper getPaper(String paperTitle) {
        
        for (Paper paperTemp : this.papers) {

            if(paperTemp.getTitle().equals(paperTitle)) {
                return paperTemp;
            }
        }
        return null;
    }
    
    /**
     * @return the authors sources
     */
    public Set<Source> getSources() {
        return this.sources;
    }
    
    public List<Source> getSourcesList() {
        return this.sortSources(this.getListAsSet2(this.sources));
    }
    
    /**
     * @param paper the papter to add
     */
    public void addPaper(Paper paper) {
        this.papers.add(paper);
        paper.setAuthor(this);
    }
    
    public void removePaper(String title) {

        Iterator it = this.papers.iterator();

        while (it.hasNext()) {

            Paper paperTemp = (Paper) it.next();

            if (paperTemp != null) {
                if (paperTemp.getTitle().equals(title)) {
                    this.papers.remove(paperTemp);
                    
                    
                    log.info("Paper " + paperTemp.getTitle() + " wurde entfernt...");
                    break;
                }
            }
        }
    }
    
    public int countPapersChapters(String paperTitle) {
        
        for (Paper paperTemp : this.papers) {

            if(paperTemp.getTitle().equals(paperTitle)) {
                
                return paperTemp.getChapters().size();
            }
        }
        
        return 0;
    }

    public void addBook() {

        int nr = this.bookPosNr + 1;
        int nr2 = this.sourcePosNr + 1;
        
        Book newBook = new Book();
        newBook.setTitle("Buch " + nr);
        newBook.setBookPosNr(nr);
        newBook.setSourcePosNr(nr2);
        
        this.sources.add(newBook);
        this.bookPosNr++;
        this.sourcePosNr++;
    }
    
    public void removeSource(String title) {

        Iterator it = this.sources.iterator();

        while (it.hasNext()) {

            Source sourceTemp = (Source) it.next();

            if (sourceTemp.getTitle().equals(title)) {
                this.sources.remove(sourceTemp);
                
                this.sourcePosNr--;
                
                if(sourceTemp.getSourceType().equals("book")) {
                    this.bookPosNr--;
                }
                if(sourceTemp.getSourceType().equals("thesis")) {
                    this.thesisPosNr--;
                }
                if(sourceTemp.getSourceType().equals("misc")) {
                    this.miscPosNr--;
                }

                log.info("Source " + sourceTemp.getTitle() + " wurde entfernt...");
                break;
            }
        }
    }

    public void addThesis() {

        int nr = this.thesisPosNr + 1;
        int nr2 = this.sourcePosNr + 1;        
        
        Thesis newThesis = new Thesis();
        
        newThesis.setTitle("Thesis " + nr);
        newThesis.setThesisPosNr(nr);
        newThesis.setSourcePosNr(nr2);
        
        this.sources.add(newThesis);
        this.thesisPosNr++;
        this.sourcePosNr++;
    }
    
    public void addMisc() {

        int nr = this.miscPosNr + 1;
        int nr2 = this.sourcePosNr + 1;
        
        Misc newMisc = new Misc();
        
        newMisc.setTitle("Misc " + nr);
        newMisc.setMiscPosNr(nr);
        newMisc.setSourcePosNr(nr2);
        
        this.sources.add(newMisc);
        this.miscPosNr++;
        this.sourcePosNr++;
    }
    
    public boolean isSourceContentShown(String title) {
        
        for (Source tempSource : this.sources) {

            if(tempSource.getTitle() != null && tempSource.getTitle().equals(title)) {
                
                return tempSource.showContent();
            }
        }
        
        return false;
    }
    
    private List<Paper> sortPapers(List<Paper> unsortedPapers) {
    
        //Sort (Selection-Sort)
        List<Paper> sortedPapers = unsortedPapers;
        
        int size = sortedPapers.size(); //micro for-loop-optimization
        
        for (int i = 0; i < size - 1; i++) {
            
            for (int j = i + 1; j < size; j++) {
                
                if (sortedPapers.get(i).getPaperPosNr() > sortedPapers.get(j).getPaperPosNr()) {
                    
                    Paper tempPaper = sortedPapers.get(i);
                    sortedPapers.set(i, sortedPapers.get(j));
                    sortedPapers.set(j, tempPaper);
                }
            }
        }
        
        return sortedPapers;
    }
    
    private List<Source> sortSources(List<Source> unsortedSources) {
        
        //Sort (Selection-Sort)
        List<Source> sortedSources = unsortedSources;
        
        int size = sortedSources.size(); //micro for-loop-optimization
        
        for (int i = 0; i < size - 1; i++) {
            
            for (int j = i + 1; j < size; j++) {
                
                if (sortedSources.get(i).getSourcePosNr() > sortedSources.get(j).getSourcePosNr()) {
                    
                    Source tempSource = sortedSources.get(i);
                    sortedSources.set(i, sortedSources.get(j));
                    sortedSources.set(j, tempSource);
                }
            }
        }
        
        return sortedSources;
    }

    @Override
    public boolean equals(Object other) {
        
        boolean flag;
        
        if (other == this) {                        //  (reflexivity)
            return true;
        }
        if (other == null) {                        //  (non-null)
            return false;   
        }
        if (getClass() != other.getClass()) {       //  (symmetry)
            return false;
        }
        Author author = (Author) other;
        
        if(!(this.matnr == author.getMatnr() || (this.matnr != null && this.matnr.equals(author.getMatnr())))) {
            return false;
        }
        if(!(this.firstname == author.getFirstname() || (this.firstname != null && this.firstname.equals(author.getFirstname())))) {
            return false;
        }
        if(!(this.lastname == author.getLastname() || (this.lastname != null && this.lastname.equals(author.getLastname())))) {
            return false;
        }
        if(!(this.password == author.getPassword() || (this.password != null && this.password.equals(author.getPassword())))) {
            return false;
        }
        if(this.isUsedNow != author.getIsUsedNow()) {
            return false;
        }
        if(!this.papersEquals(author.getPapersList())) {
            return false;
        }
        if(!this.sourcesEquals(author.getSourcesList())) {
            return false;
        }
            
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.matnr != null ? this.matnr.hashCode() : 0);
        hash = 37 * hash + (this.firstname != null ? this.firstname.hashCode() : 0);
        hash = 37 * hash + (this.lastname != null ? this.lastname.hashCode() : 0);
        hash = 37 * hash + (this.password != null ? this.password.hashCode() : 0);
        return hash;
    }
    
    private boolean papersEquals(List<Paper> otherPapers) {
        
        List<Paper> papers = this.getPapersList();
        
        List<Paper> sortedPapers = this.sortPapers(papers);
        List<Paper> sortedOtherPapers = this.sortPapers(otherPapers);
        
        if(sortedPapers.size() == sortedOtherPapers.size()) {
            
            for(int i=0; i<sortedPapers.size(); i++) {
                if(!sortedPapers.get(i).equals(sortedOtherPapers.get(i))) {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        
        return true;
    }
    
    private boolean sourcesEquals(List<Source> otherSources) {
        
        List<Source> sources = this.getSourcesList();
        
        List<Source> sortedSources = this.sortSources(sources);
        List<Source> sortedOtherSources = this.sortSources(otherSources);
        
        if(sortedSources.size() == sortedOtherSources.size()) {
            
            for(int i=0; i<sortedSources.size(); i++) {
                if(!sortedSources.get(i).equals(sortedOtherSources.get(i))) {
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

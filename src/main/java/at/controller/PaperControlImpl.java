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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import at.model.Author;
import at.model.Book;
import at.model.Chapter;
import at.model.ChapterContent;
import at.model.Graphic;
import at.model.Misc;
import at.model.Paper;
import at.model.Paragraph;
import at.model.Source;
import at.model.Thesis;
import at.service.AuthorService;
import at.service.PaperService;
import at.utilities.GUIUtilities;
import at.utilities.Utilities;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("paperControl")
@Scope("session")
public class PaperControlImpl implements PaperControl {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(PaperControlImpl.class);

    @Autowired
    private PaperService paperService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private VelocityEngine velocityEngine;

    private String templateFile;
    private String bibTeXFile;

    private Author author = null;

    private Paper currentPaper = null;

    private int paperPosNr;


    public PaperControlImpl() {

        this.templateFile = "paperTemplates" + File.separator + "template_essay_de.vm";
        this.bibTeXFile = "bibtex.vm";
                
        log.info("PaperControl wurde instanziert...");
    }


    public String createPaper() {

        this.currentPaper = new Paper();
        this.currentPaper.initPaper(this.paperPosNr++);

        if (this.author != null) {

            this.author.addPaper(this.currentPaper);
            
            return "titlecreation?faces-redirect=true";
        }
        else {
            log.error("Violation of precondition (author != null)! Paper could not be created.");
            return "portfolio?faces-redirect=true";
        }
    }

    public String editPaper(String title) {

        if (this.author != null) {

            if (title != null) {
                this.currentPaper = this.author.getPaper(title);

                if (this.currentPaper != null) {
                    return "documentlayer?faces-redirect=true";
                }
                else {
                    log.error("Violation of precondition (PaperControlImpl.author.getPaper(title) != null)! CurrentPaper could not be set...");
                    return "portfolio?faces-redirect=true";
                }
            }
            else {
                log.error("Violation of precondition (title != null)! CurrentPaper could not be set...");
                return "portfolio?faces-redirect=true";
            }
        }
        else {
            log.error("Violation of precondition (author != null)! CurrentPaper could not be set...");
            return "portfolio?faces-redirect=true";
        }
    }

    public void removePaper(String title) {

        if (this.author != null) {

            if (title != null) {
                
                Paper tempPaper = this.author.getPaper(title);

                if (tempPaper != null) {
                    this.author.removePaper(title);
                }
                else {
                    log.error("Violation of precondition (PaperControlImpl.author.getPaper(title) != null)! Paper could not be removed...");
                }
            }
            else {
                log.error("Violation of precondition (title != null)! Paper could not be removed...");
            }
        }
        else {
            log.error("Violation of precondition (author != null)! Paper could not be removed...");
        }
    }

    public void saveAll() {

        if(this.authorService.updateAuthor(this.author) != null) {
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, ResourceBundle.getBundle("i18n").getString("savesuccessfully"), "");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
        else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, ResourceBundle.getBundle("i18n").getString("saveunsuccessfully"), "");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
        }
    }
    
    // helper-method for LoginControlImpl.logout()
    public void removeCurrentPaper() {
        
        this.author.removePaper(this.currentPaper.getTitle());
        this.currentPaper = null;
    }

    public String saveAllGoToPortfolio() {

        this.authorService.updateAuthor(this.author);

        return "portfolio?faces-redirect=true";
    }

    public String savePaperTitle() {

        if (this.currentPaper != null) {
            return "documentlayer?faces-redirect=true";
        }
        else {
            return "titlecreation?faces-redirect=true";
        }
    }

    public String setPapersCurrentChapter(String title) {

        if (this.currentPaper != null) {

            if (this.currentPaper.setCurrentChapter(title)) {

                return "chapterlayer?faces-redirect=true";
            }
        }

        return "documentlayer?faces-redirect=true";
    }

    public List<Paper> getAllPapers() {

        return this.paperService.getAllPapers();
    }

    public List<Paper> getPapersByAuthorFromDB() {

        if (this.author != null) {
            return this.paperService.getPapersByAuthor(this.author.getMatnr());
        } else {
            return null;
        }
    }

    public List<Paper> getPapersByAuthor() {

        if (this.author != null) {
            return this.author.getSortedPapersList();
        } else {
            return null;
        }
    }
    
    
    private void createBibliographyFile() {
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = servletContext.getRealPath(File.separator);
        
        String filePath = realPath + File.separator + this.author.getLastname() + File.separator +  this.currentPaper.getPaperPosNr() + File.separator + "literature.bib";
                    
        //paper uses bibliography
        if(this.currentPaper.getUseBibliography()) {
            
            if(this.author != null && this.author.getSources() != null) {

                Template template = null;

                    try {

                        template = this.velocityEngine.getTemplate(this.bibTeXFile);

                    } catch (ResourceNotFoundException rnfe) {

                        System.out.println("Example : error : cannot find template " + this.bibTeXFile);

                    } catch (ParseErrorException pee) {

                        System.out.println("Example : Syntax error in template " + this.bibTeXFile + ":" + pee);
                    }

                    // create a context and add data
                    VelocityContext context = new VelocityContext();
                    context.put("sources", this.createVelocityTemplateSources());

                
                    // writer initialization
                    FileWriter fileWriter = null;
                    BufferedWriter bufferedWriter = null;
                    
                    File file = new File(filePath);

                    try {
                        fileWriter = new FileWriter(file.getAbsoluteFile(), false);
                        bufferedWriter = new BufferedWriter(fileWriter);
                    } catch (IOException ex) {
                        Logger.getLogger(PaperControlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    // template merging
                    if (template != null && file != null) {
                        template.merge(context, bufferedWriter);
                    }

                    // flush and cleanup
                    try {

                        bufferedWriter.flush();
                        bufferedWriter.close();

                    } catch (IOException ex) {

                        Logger.getLogger(PaperControlImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        }
        else {
            
            File tempFile = new File(filePath);
            
            if(tempFile.exists())
            {
                tempFile.delete();
                log.info("Existing bibliography-file removed...");
            }
            
            log.info("No bibliography-file added...");
        }
    }
    


    public void createOutputFile() {

        // velocity-properties (use of WebappResourceLoader instead of FileResourceLoader)
        //Properties p = new Properties();
        //p.setProperty("resource.loader", "webapp");
        //p.setProperty("webapp.resource.loader.class", "org.apache.velocity.tools.view.WebappResourceLoader");
        //p.setProperty("webapp.resource.loader.path", "/WEB-INF/paperTemplates/");
        //this.velocityEngine.init(p);

        //ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //this.velocityEngine.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
        

        if(this.author != null && this.author.getMatnr() != null && this.author.getFirstname() != null && this.author.getLastname() != null) {
            
            if(this.currentPaper != null && this.currentPaper.getTitle() != null && this.currentPaper.getAbstractPart() != null && this.currentPaper.getLang() != null) {
        
                //prepare for export (Foldername: authorLastname_paperTitle)
                if (!Utilities.prepareForExport(this.author.getLastname() + "_" + this.currentPaper.getPaperPosNr())) {

                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, ResourceBundle.getBundle("i18n").getString("exportunsuccessfully"), "");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, message);
                    
                    return;
                }
            
                Template template = null;

                try {

                    template = this.velocityEngine.getTemplate(this.templateFile);

                } catch (ResourceNotFoundException rnfe) {

                    System.out.println("Example : error : cannot find template " + this.templateFile);

                } catch (ParseErrorException pee) {

                    System.out.println("Example : Syntax error in template " + this.templateFile + ":" + pee);
                }


                Date dt = new Date();
                SimpleDateFormat df = new SimpleDateFormat( "dd-MM-yyyy" );

                // create a context and add data
                VelocityContext context = new VelocityContext();
                context.put("title", this.currentPaper.getTitle());
                context.put("date", df.format(dt.getTime()));
                context.put("author", this.author.getFirstname() + " " + this.author.getLastname());
                context.put("matNr", this.author.getMatnr());

                if (this.currentPaper.getAbstractPart().length() == 0) {
                    context.put("abstract", "\\lipsum");
                } 
                else {
                    context.put("abstract", this.currentPaper.getAbstractPart());
                }


                context.put("introductionChapters", this.createVelocityTemplateChapters("Introduction"));
                context.put("mainpartChapters", this.createVelocityTemplateChapters("Mainpart"));
                context.put("clonclusionChapters", this.createVelocityTemplateChapters("Conclusion"));
                
                //language
                if(this.currentPaper.getLang().length() != 0) {
                    context.put("lang", this.currentPaper.getLang());
                }
                else {
                    context.put("lang", Utilities.getBrowserLocaleForLaTeX());
                }
                    
                //create bibTeX-file
                this.createBibliographyFile();

                // writer initialization
                FileWriter fileWriter = null;
                BufferedWriter bufferedWriter = null;
                
                ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                String realPath = servletContext.getRealPath(File.separator);
        
                File file = new File(realPath + File.separator + this.author.getLastname() + "_" +  this.currentPaper.getPaperPosNr() + File.separator + this.createOutputFileName() + ".tex");

                try {
                    fileWriter = new FileWriter(file.getAbsoluteFile(), false);
                    bufferedWriter = new BufferedWriter(fileWriter);
                } 
                catch (IOException ex) {
                    Logger.getLogger(PaperControlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }


                // template merging
                if (template != null) {
                    template.merge(context, bufferedWriter);
                }

                // flush and cleanup
                try {

                    bufferedWriter.flush();
                    bufferedWriter.close();

                } catch (IOException ex) {

                    Logger.getLogger(PaperControlImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                GUIUtilities.downloadFile(realPath + this.author.getLastname() + "_" +  this.currentPaper.getPaperPosNr());
                
                // delete temporary folder
                Utilities.cleanUpAfterExport(this.author.getLastname() + "_" +  this.currentPaper.getPaperPosNr());
            }
            else {
                log.error("Violation of precondition (currentPaper != null or currentPaper's attributes != null)! Output could not be generated...");
            }
        }
        else {
            log.error("Violation of precondition (author != null or author's attributes != null)! Output could not be generated...");
        }
    }

    private String createVelocityTemplateSources() {

        String output = "";

        List<Source> sources = this.author.getSourcesList();
        int size = this.author.getSourcesList().size();

        for (int i = 0; i < size; i++) {

            Source tempSource = sources.get(i);

            if (tempSource != null && tempSource instanceof Book) {

                if (tempSource.getAuthorNames() != null && tempSource.getTitle() != null && ((Book) tempSource).getPublisher() != null && tempSource.getYear() != null) {
                    //required fields
                    output = output + "\n@book{book" + ((Book) tempSource).getBookPosNr() + "_" + tempSource.getYear() + ",\n"
                            + "author = {" + tempSource.getAuthorNames() + "},\n"
                            + "title =  {" + tempSource.getTitle() + "},\n"
                            + "publisher = {" + ((Book) tempSource).getPublisher() + "},\n"
                            + "year = " + tempSource.getYear() + ",\n";

                    //optional fields
                    if (((Book) tempSource).getAddress() != null && !((Book) tempSource).getAddress().equals("")) {
                        output = output + "address = {" + ((Book) tempSource).getAddress() + "},\n";
                    }

                    if (((Book) tempSource).getEdition() != null && !((Book) tempSource).getEdition().equals("")) {
                        output = output + "edition = {" + ((Book) tempSource).getEdition() + "},\n";
                    }

                    if (((Book) tempSource).getSeries() != null && !((Book) tempSource).getSeries().equals("")) {
                        output = output + "series = {" + ((Book) tempSource).getSeries() + "},\n";
                    }

                    if (tempSource.getNote() != null && !tempSource.getNote().equals("")) {
                        output = output + "note = {" + tempSource.getNote() + "},\n";
                    }

                    output = output + "}\n";
                }
            }

            if (tempSource != null && tempSource instanceof Thesis) {

                if (tempSource.getAuthorNames() != null && tempSource.getTitle() != null && ((Thesis) tempSource).getSchool() != null && tempSource.getYear() != null) {
                    //required fields
                    output = output + "\n@" + ((Thesis) tempSource).getThesisType() + "{thesis" + ((Thesis) tempSource).getThesisPosNr() + "_" + tempSource.getYear() + ",\n"
                            + "author = {" + tempSource.getAuthorNames() + "},\n"
                            + "title =  {" + tempSource.getTitle() + "},\n"
                            + "school = {" + ((Thesis) tempSource).getSchool() + "},\n"
                            + "year = " + tempSource.getYear() + ",\n";

                    //optional fields
                    if (tempSource.getNote() != null && !tempSource.getNote().equals("")) {
                        output = output + "note = {" + tempSource.getNote() + "},\n";
                    }

                    output = output + "}\n";
                }
            }

            if (tempSource != null && tempSource instanceof Misc) {

                if (tempSource.getAuthorNames() != null && tempSource.getTitle() != null && ((Misc) tempSource).getHowPublished() != null && tempSource.getYear() != null && tempSource.getNote() != null && tempSource.getNote().equals("")) {

                    output = output + "\n@misc{misc" + ((Misc) tempSource).getMiscPosNr() + "_" + tempSource.getYear() + ",\n"
                            + "author = {" + tempSource.getAuthorNames() + "},\n"
                            + "title =  {" + tempSource.getTitle() + "},\n"
                            + "howpublished = {\\url{" + ((Misc) tempSource).getHowPublished() + "}},\n"
                            + "year = " + tempSource.getYear() + ",\n"
                            + "note = {" + tempSource.getNote() + "},\n";

                    output = output + "}\n";
                }
            }
        }

        return output;
    }

    private String createVelocityTemplateChapters(String section) {

        String output = "";

        List<Chapter> sectionChapters = this.currentPaper.getSectionChapters(section);
        int size = this.currentPaper.getSectionChapters(section).size();

        for(int i=0; i < size; i++) {

            List<ChapterContent> chapterContent = sectionChapters.get(i).getChapterContent();
            int size2 = chapterContent.size();

            output = output + "\n\\chapter{" + sectionChapters.get(i).getTitle() + "}\n\n";

            for (int j = 0; j < size2; j++) {

                if (chapterContent.get(j) instanceof Paragraph) {

                    output = output + "\\section{" + chapterContent.get(j).getTitle() + "}\n";
                    
                    if(((Paragraph)chapterContent.get(j)).getContent() == null) {
                        output = output + "\\lipsum\n\n";
                    }
                    else {
                        output = output + ((Paragraph)chapterContent.get(j)).getContent() + "\n\n";
                    }
                }
                else if(chapterContent.get(j) instanceof Graphic) {

                    output = output + "\\begin{figure}[t]\n"
                                    +   "\t\\centering\n"
                                    +   "\t\\includegraphics[scale=" + Utilities.calcScalingFactor(((Graphic)chapterContent.get(j)).getHeight(), ((Graphic)chapterContent.get(j)).getWidth()) + "]{" + chapterContent.get(j).getTitle().replaceAll(" ", "_") + "}\n"
                                    +   "\t\\caption{" + chapterContent.get(j).getTitle() + "}\n"
                                    + "\\end{figure}\n\n";

                    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
                    String realPath = servletContext.getRealPath(File.separator);
                    
                    String filePath = realPath + File.separator + this.author.getLastname() + "_" +  this.currentPaper.getPaperPosNr() + File.separator + "figures";

                    Utilities.writeFile(((Graphic)chapterContent.get(j)).getImage(), filePath + File.separator + chapterContent.get(j).getTitle().replaceAll(" ", "_") + "." + Utilities.modifyMIMEType(((Graphic)chapterContent.get(j)).getFile()));
                }
            }
        }

        return output;
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
     * @return the current Paper
     */
    public Paper getCurrentPaper() {
        return this.currentPaper;
    }

    /**
     * @param currentPaper the current Paper to set
     */
    public void setCurrentPaper(Paper currentPaper) {
        this.currentPaper = currentPaper;
    }
    
    private String createOutputFileName() {
        
        String name = this.currentPaper.getTitle().replace(" ", "_") + "_" + this.currentPaper.getAuthor().getLastname().replace(" ", "_");
        name = name.toLowerCase();
        
        name = name.replace("ä","ae");
        name = name.replace("ö","oe");
        name = name.replace("ü","ue");
        
        return name;
    }
    
    public void initPaperPosNr() {
        
        if (this.author != null && !this.author.getSortedPapersList().isEmpty()) {
             this.paperPosNr = this.author.getSortedPapersList().get(this.author.getSortedPapersList().size() - 1).getPaperPosNr() + 1;
        } else {
            this.paperPosNr = 1;
        }
    }
}

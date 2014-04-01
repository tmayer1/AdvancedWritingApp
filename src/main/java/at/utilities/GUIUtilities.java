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

package at.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class GUIUtilities {
    
    private static org.apache.log4j.Logger log = Logger.getLogger(GUIUtilities.class);
    
    public GUIUtilities() {
        
        log.info("GUIUtilities wurde instanziert...");
    }
    
    
    public static String chooseTitle() {
        
         String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         

         if(uri.equals("/awapp/portfolio.xhtml")) {
             return ResourceBundle.getBundle("i18n").getString("portfolio");
         }
         else {
            if (uri.equals("/awapp/titlecreation.xhtml")) {
                return ResourceBundle.getBundle("i18n").getString("titlecreation");
            } else {
                if (uri.equals("/awapp/sourcelayer.xhtml")) {
                    return ResourceBundle.getBundle("i18n").getString("sources");
                } else {
                    if (uri.equals("/awapp/documentlayer.xhtml")) {
                        return null;
                    } else {
                        if (uri.equals("/awapp/chapterlayer.xhtml")) {
                            return null;
                        }
                    }
                }
            }
        }

        return uri;
    }
    
    
    public String chooseForm() {
        
         String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         

         if (uri.equals("/awapp/portfolio.xhtml")) {
            return "portfolioForm";
        } else {
            if (uri.equals("/awapp/titlecreation.xhtml")) {
                return "titleCreationForm";
            } else {
                if (uri.equals("/awapp/documentlayer.xhtml")) {
                    return "documentForm";
                } else {
                    if (uri.equals("/awapp/chapterlayer.xhtml")) {
                        return "chapterForm";
                    } else {
                        if (uri.equals("/awapp/sourcelayer.xhtml")) {
                            return "sourceForm";
                        }
                    }
                }
            }
        }
         
        return uri;
    }
    
    
    public String chooseSection(String section) {
        
        if (section != null) {
            if (section.equals("Introduction")) {
                return "introductionArea";
            } else if (section.equals("Mainpart")) {
                return "mainpartArea";
            } else if (section.equals("Conclusion")) {
                return "conclusionArea";
            }
            else {
                return null;
            }
        }
        return null;
    }
    
    
    public boolean renderButtonPanel() {
        
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         
         if(uri.equals("/awapp/portfolio.xhtml")) {
             return false;
         }
         else {
             if(uri.equals("/awapp/titlecreation.xhtml")) {
                 return false;
             }
             else {
                 if(uri.equals("/awapp/documentlayer.xhtml")) {
                     return true;
                 }
                 else {
                     if(uri.equals("/awapp/chapterlayer.xhtml")) {
                         return true;
                     }
                     else {
                         if (uri.equals("/awapp/sourcelayer.xhtml")) {
                             return true;
                         }
                     }
                 }
             }
         }
        
         return false;
    }
    
    
    public boolean renderExportButton() {
        
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         
         if(uri.equals("/awapp/portfolio.xhtml")) {
             return false;
         }
         else {
             if(uri.equals("/awapp/titlecreation.xhtml")) {
                 return false;
             }
             else {
                 if(uri.equals("/awapp/documentlayer.xhtml")) {
                     return true;
                 }
                 else {
                     if(uri.equals("/awapp/chapterlayer.xhtml")) {
                         return true;
                     }
                     else {
                         if (uri.equals("/awapp/sourcelayer.xhtml")) {
                             return false;
                         }
                     }
                 }
             }
         }
         
         return false;
    }
    
    
    public boolean renderNavigationPanel() {
        
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         
         if (uri.equals("/awapp/portfolio.xhtml")) {
            return true;
        } else {
            if (uri.equals("/awapp/titlecreation.xhtml")) {
                return false;
            } else {
                if (uri.equals("/awapp/documentlayer.xhtml")) {
                    return true;
                } else {
                    if (uri.equals("/awapp/chapterlayer.xhtml")) {
                        return true;
                    } else {
                        if (uri.equals("/awapp/sourcelayer.xhtml")) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    
    public boolean renderPortfolioNav() {
        
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         
         if(uri.equals("/awapp/portfolio.xhtml")) {
             return false;
         }
         else {
             if(uri.equals("/awapp/titlecreation.xhtml")) {
                 return false;
             }
             else {
                 if(uri.equals("/awapp/documentlayer.xhtml")) {
                     return true;
                 }
                 else {
                     if(uri.equals("/awapp/chapterlayer.xhtml")) {
                         return true;
                     }
                     else {
                         if (uri.equals("/awapp/sourcelayer.xhtml")) {
                             return true;
                         }
                     }
                 }
             }
         }
        return false;
    }
    
    
    public boolean renderDocumentLayerNav() {
        
        String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();
         
         if(uri.equals("/awapp/portfolio.xhtml")) {
             return false;
         }
         else {
             if(uri.equals("/awapp/titlecreation.xhtml")) {
                 return false;
             }
             else {
                 if(uri.equals("/awapp/documentlayer.xhtml")) {
                     return false;
                 }
                 else {
                     if(uri.equals("/awapp/chapterlayer.xhtml")) {
                         return true;
                     }
                     else {
                         if (uri.equals("/awapp/sourcelayer.xhtml")) {
                             return false;
                         }
                     }
                 }
             }
         }
        
         return false;
    }
    
    
    public String goToPortfolio() {
        
        return "portfolio?faces-redirect=true";
    }
    
    
    public String goToDocumentLayer() {
        
        return "documentlayer?faces-redirect=true";
    }
    
    
    public String goToSourceLayer() {
        
        return "sourcelayer?faces-redirect=true";
    }
    
    
    public String clear(final String parentComponentId) {
        UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
        UIComponent fc = view.findComponent(parentComponentId);
        if (null != fc) {
            List<UIComponent> components = fc.getChildren();
            for (UIComponent component : components) {
                if (component instanceof UIInput) {
                    UIInput input = (UIInput) component;

                    input.resetValue();
                }
            }
        }
        return null;
    }
    

    public static void downloadFile(String fileName) {

        try {

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();

            //prepare for download
            ec.responseReset();
            ec.setResponseContentType("application/zip");
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + "fileName.zip"); // "Content-Disposition" creates a download-file-dialog

            OutputStream output = ec.getResponseOutputStream();
                        
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = servletContext.getRealPath(File.separator);
                
            //pack folder
            Utilities.createZipfileFromFolder(fileName, realPath);

            InputStream input = null;
            OutputStream output2 = null;

            try {
                input = new BufferedInputStream(new FileInputStream(realPath + File.separator + "zipFile.zip"));
                output2 = new BufferedOutputStream(output);
                byte[] buffer = new byte[8192];

                for (int length; (length = input.read(buffer)) != -1;) {
                    output2.write(buffer, 0, length);
                }
            } 
            finally {
                if (output2 != null) {
                    try {
                        output2.close();
                    } 
                    catch (IOException ignore) {
                    }
                }
                if (input != null) {
                    try {
                        input.close();
                    } 
                    catch (IOException ignore) {
                    }
                }
                if (output != null) {
                    try {
                        output.close();
                    } 
                    catch (IOException ignore) {
                    }
                }
            }
            
            File file = new File(realPath + File.separator + "zipFile.zip");

            if (file.exists()) {
                file.delete();
            }
            
            // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
            fc.responseComplete(); 

        } 
        catch (IOException ex) {
            log.error("Could not download file...", ex);
        }
    }
}

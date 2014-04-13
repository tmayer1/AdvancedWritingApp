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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;

public class Utilities {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Utilities.class);
    
    
    public static Object deepCopy(Object o) throws Exception {
        
        Object obj = null;
        
        ObjectInputStream in = null;
        
        try {
            
            // write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(o);
            out.flush();
            out.close();

            // make an input stream from the byte array and read
            // a copy of the object back in
            in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            obj = in.readObject();
        }
        catch(IOException ex) {
            log.error(ex);
        }
        catch(ClassNotFoundException ex) {
            log.error(ex);
        }
        finally {
            
            if(in != null) {
                in.close();
            }
        }
        
        return obj;
    }
    
    
    public static void writeFile(byte[] file, String path) {
        
        FileOutputStream fos = null;
        
        try {
            FileUtils.writeByteArrayToFile(new File(path), file);
        } 
        catch (FileNotFoundException ex) {
            log.error("File not found...", ex);
        } 
        catch (IOException ex) {
            log.error("Could not create file out of byte-array...", ex);
        }
        finally {
            
            if(fos != null) {
                try {
                    fos.close();
                } 
                catch (IOException ex) {
                    log.error("Unable to close FileOutputStream...", ex);
                }
            }
        }
    } 
    
    
    public static String modifyMIMEType(Part file) {
        
        if (file != null && "image/jpeg".equals(file.getContentType())) {
            return "jpg";
        }

        if (file != null && "image/png".equals(file.getContentType())) {
            return "png";
        }
        
        return null;
    }
    
    
    public static String modifyMIMEType(String contentType) {
        
        if ("image/jpeg".equals(contentType)) {
            return "jpg";
        }

        if ("image/png".equals(contentType)) {
            return "png";
        }
        
        return null;
    }
    
    
    public static String calcScalingFactor(int height, int width) {
        
        if(width < 150) {
            return "1.0";
        } else {
            if (width < 300) {
                return "0.9";
            } else {
                if (width < 500) {
                    return "0.6";
                } else {
                    if (width < 1000) {
                        return "0.3";
                    }
                    else {
                        if(width < 2000) {
                            return "0.15";
                        }
                        else {
                            if(width < 3000) {
                                return "0.1";
                            }
                        }
                    }
                }
            }
        }
        return "graphic to large";
    }
    
    
    public static boolean validateFileType(Part file) {
        
        if (file != null) {
            
            // validate file type (only jpeg and png files allowed)
            if (!"image/jpeg".equals(file.getContentType())) {

                if (!"image/png".equals(file.getContentType())) {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }
    
    
    public static boolean validateFileType(String contentType) {
        
        if (contentType != null) {
            
            // validate file type (only jpeg and png files allowed)
            if (!"image/jpeg".equals(contentType)) {

                if (!"image/png".equals(contentType)) {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return true;
    }
    
    
    public static String getBrowserLocaleForLaTeX() {
        
        Locale browserLocale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        
        if(browserLocale.getLanguage() != null && browserLocale.getLanguage().equals("de")) {
            return "ngerman";
        }
        if(browserLocale.getLanguage() != null && browserLocale.getLanguage().equals("en")) {
            return "english";
        }
        
        return browserLocale.getLanguage();
    }
    
    
    //copies all the necessary files for the laTeX-output (style-files, figures, ...)
    public static boolean prepareForExport(String destFolderName) {
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = servletContext.getRealPath(File.separator);
                
        File srcFolder = new File(realPath + File.separator + "WEB-INF" + File.separator + "necessaryPaperFiles");
    	File destFolder = new File(realPath + File.separator + destFolderName);
 
    	if(!srcFolder.exists()) {

            log.error("Directory (necessaryPaperFiles) does not exist...");
        }
        else {
 
           try {
        	Utilities.copyFolder(srcFolder, destFolder);
                return true;
           }
           catch(IOException ex) {
        	log.error("Could not copy source-folder to destination-folder...", ex);
                return false;
           }
        }
        
        return false;
    }
    
    
    public static void copyFolder(File src, File dest) throws IOException {
 
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		}
 
    		//directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
                    
                    if (file != ".DS_Store") {
                        
                        //create the src and dest file structure
                        File srcFile = new File(src, file);
                        File destFile = new File(dest, file);
                        
                        copyFolder(srcFile, destFile);
                    }
    		}
 
    	}
        else {
    		//copy file
            
    		//bytes-stream to support all file types
    		InputStream in = new FileInputStream(src);
    	        OutputStream out = new FileOutputStream(dest); 
 
    	        byte[] buffer = new byte[1024];
 
    	        int length;
    	        //copy the file content in bytes 
    	        while ((length = in.read(buffer)) > 0){
    	    	   out.write(buffer, 0, length);
    	        }
 
    	        in.close();
    	        out.close();
    	}
    }
    
    public static void cleanUpAfterExport(String destFolderName) {
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = servletContext.getRealPath(File.separator);
                
        File folder = new File(realPath + File.separator + destFolderName);
 
    	if(!folder.exists()) {

            log.error("Directory does not exist...");
        }
        else{
 
           try {
        	Utilities.deleteFolder(folder);
                log.info("Folder successfully removed...");
           }
           catch(IOException ex){
        	log.error("Could not delete folder...", ex);
           }
        }
    }
    
    public static void deleteFolder(File folder) throws IOException {
        
        if(folder.isDirectory()) {
 
    		if(folder.list().length == 0) {
 
    		   folder.delete();
    		}
                else {
 
        	   String files[] = folder.list();
 
        	   for (String temp : files) {

                        File fileDelete = new File(folder, temp);
                        deleteFolder(fileDelete);
        	   }
 
        	   if(folder.list().length == 0){
           	        folder.delete();
                }
            }
        } 
        else {

            folder.delete(); 
        }
    }
    
    // create temporary zip-file (will be deleted after finishing export)
    public static void createZipfileFromFolder(String srcFolderPath, String targetPath) {

        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        
        try {
         
            // source-path: ../awapp/<authorLastname>_<paperPosNr>
            // output-path: ../awapp/
            
            fos = new FileOutputStream(targetPath + File.separator + "zipFile.zip");
            zos = new ZipOutputStream(fos);

            File srcFile = new File(srcFolderPath);

            Utilities.addFolderToArchive(zos, srcFile);
        }
        catch (IOException ex) {
            log.error("Error creating zip file...", ex);
        }
        finally {
            
            try {
                if (zos != null) {
                    zos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } 
            catch (IOException ex) {
                log.error("Unable to close OutputStreams...", ex);
            }
        }
    }
    

    private static void addFolderToArchive(ZipOutputStream zos, File srcFile) {

        File[] files = srcFile.listFiles();
        
        if(files != null) {
            
            for (int i = 0; i < files.length; i++) {

                // if the file is directory, use recursion
                if (files[i].isDirectory()) {
                    addFolderToArchive(zos, files[i]);
                    continue;
                }

                FileInputStream fis = null;

                try {

                    // create byte buffer
                    byte[] buffer = new byte[1024];

                    fis = new FileInputStream(files[i]);

                    zos.putNextEntry(new ZipEntry(files[i].getAbsolutePath()));

                    int length;

                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                } 
                catch (IOException ex) {
                    log.error("Unable to create ZipEntry...", ex);
                }
                finally {
                    try {
                        if (zos != null) {
                            zos.closeEntry();
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    } 
                    catch (IOException ex) {
                       log.error("Unable to close ZipEntry or InputStream...", ex);
                    }
                }
            }
        }
        else {
            log.error("Unable to create Zip-File (empty source-file)...");
        }
    }
}

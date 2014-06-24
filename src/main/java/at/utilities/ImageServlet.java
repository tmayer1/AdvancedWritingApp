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

package at.utilities;

import at.model.Author;
import at.service.AuthorService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

/**
 * Servlet which loads an image from the database and then streams the image to 
 * the HttpServletResponse.
 * The Servlet has to be also defined in the Web Deployment Descriptor web.xml.
 * 
 * @author Thomas Mayer
 */
@Component("imageServlet")
public class ImageServlet implements HttpRequestHandler {

    @Autowired
    private AuthorService authorService;

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                
        // Get parameters from request.
        String parameterMatnr = request.getParameter("matnr");
        String parameterPaperTitle = request.getParameter("papertitle");
        String parameterGraphicTitle = request.getParameter("graphictitle");

        // Check if 'parameterMatnr' is supplied to the request.
        if (parameterMatnr == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        // Check if 'parameterPaperTitle' is supplied to the request.
        if (parameterPaperTitle == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        // Check if 'parameterGraphicTitle' is supplied to the request.
        if (parameterGraphicTitle == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // DB
        Author author = this.authorService.getRegisteredAuthor(parameterMatnr);

        if (author == null) {
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, ResourceBundle.getBundle("i18n").getString("imageloadfirsttime"), "");
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, message);
            
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        
        byte[] blob = author.getPaper(parameterPaperTitle).getChapter(author.getPaper(parameterPaperTitle).getCurrentChapterTitle()).getChapterGraphic(parameterGraphicTitle).getImage();

        // Check if 'blob' (img) is actually retrieved from database.
        if (blob == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }
        
        String contentType = author.getPaper(parameterPaperTitle).getChapter(author.getPaper(parameterPaperTitle).getCurrentChapterTitle()).getChapterGraphic(parameterGraphicTitle).getType();

        InputStream in = new ByteArrayInputStream(blob);
        BufferedImage image = ImageIO.read(in);

        // Check if 'image' is actually retrieved from database.
        if (image == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;
        }

        // Init servlet response.
        response.reset();
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        response.setContentType(contentType);

        OutputStream out = response.getOutputStream();
        ImageIO.write(image, Utilities.modifyMIMEType(contentType), out);
    }
}
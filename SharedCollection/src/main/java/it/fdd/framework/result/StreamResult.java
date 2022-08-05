package it.fdd.framework.result;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StreamResult {

    protected ServletContext context;

    public StreamResult(ServletContext context) {
        this.context = context;
    }

    public void activate(File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        activate(new FileInputStream(file), file.length(), file.getName(), request, response);
    }

    public void activate(InputStream data, long size, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        try {

            //disabilitiamo tutte le forme di caching...
            //disable caching...
            response.setHeader("Pragma", "");
            response.setHeader("Cache-Control", "");

            String contentType = (String) request.getAttribute("contentType");
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            response.setContentType(contentType);
            response.setContentLength((int) size);

            String contentDisposition = (String) request.getAttribute("contentDisposition");
            if (contentDisposition == null) {
                contentDisposition = "attachment";
            }
            contentDisposition += "; filename=\"" + fileName + "\"";
            response.setHeader("Content-Disposition", contentDisposition);

            //copiamo lo stream in output
            //copy the stream to the output
            out = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int read;
            while ((read = data.read(buffer)) > 0) {
                out.write(buffer, 0, read);
            }
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                //ingoriamo altri errori nel finally
                //ignore errors in finally clause
            }
        }
    }

}

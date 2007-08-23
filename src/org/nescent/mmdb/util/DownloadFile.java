package org.nescent.mmdb.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
 
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

 
public class DownloadFile {
 
	private static final Log log = LogFactory.getLog(DownloadFile.class);
    /**
     * Send the given file as a byte array to the servlet response. If attachment 
     * is set to true, then show a "Save as" dialogue, else show the file inline
     * in the browser or let the operating system open it in the right application.
     * @param response The HttpServletResponse to be used.
     * @param bytes The file contents in a byte array.
     * @param fileName The file name.
     * @param attachment Download as attachment? 
     */
    public static void downloadFile(HttpServletResponse response, byte[] bytes, String fileName, boolean attachment) throws IOException {
        // Wrap the byte array in a ByteArrayInputStream and pass it through another method.
        downloadFile(response, new ByteArrayInputStream(bytes), fileName, attachment);
    }
 
    /**
     * Send the given file as a File object to the servlet response. If attachment
     * is set to true, then show a "Save as" dialogue, else show the file inline
     * in the browser or let the operating system open it in the right application.
     * @param response The HttpServletResponse to be used.
     * @param file The file as a File object.
     * @param attachment Download as attachment? 
     */
    public static void downloadFile(HttpServletResponse response, File file, boolean attachment) throws IOException {
        // Prepare stream.
        BufferedInputStream input = null;
 
        try {
            // Wrap the file in a BufferedInputStream and pass it through another method.
            input = new BufferedInputStream(new FileInputStream(file));
            downloadFile(response, input, file.getName(), attachment);
        } catch (IOException e) {
            throw e;
        } finally {
            // Gently close stream.
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("Download failed.",e);
                }
            }
        }
    }
 
    /**
     * Send the given file as an InputStream to the servlet response. If attachment 
     * is set to true, then show a "Save as" dialogue, else show the file inline
     * in the browser or let the operating system open it in the right application.
     * @param response The HttpServletResponse to be used.
     * @param input The file contents in an InputStream.
     * @param fileName The file name.
     * @param attachment Download as attachment? 
     */
    public static void downloadFile(HttpServletResponse response, InputStream input, String fileName, boolean attachment) throws IOException {
        // Prepare stream.
        BufferedOutputStream output = null;
 
        try {
            // Prepare.
            int contentLength = input.available();
            String contentType = URLConnection.guessContentTypeFromName(fileName);
            String disposition = attachment ? "attachment" : "inline";
 
            // Init servlet response.
            response.setContentLength(contentLength);
            response.setContentType(contentType);
            response.setHeader("Content-disposition", disposition + "; filename=\"" + fileName + "\"");
            output = new BufferedOutputStream(response.getOutputStream());
 
            // Write file contents to response.
            while (contentLength-- > 0) {
                output.write(input.read());
            }
 
            // Finalize task.
            output.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            // Gently close stream.
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("Download failed.",e);
                }
            }
        }
    }
 
}

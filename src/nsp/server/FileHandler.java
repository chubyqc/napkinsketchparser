package nsp.server;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsp.server.core.SessionManager;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileHandler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			InputStream input = null;
			String layerId = null;
			for (Object obj : upload.parseRequest(req)) {
				FileItem fileItem = (FileItem)obj;
				if (!fileItem.isFormField()) {
					input = fileItem.getInputStream();
				} else if (nsp.client.Utils.get().getLayerIdKey().equals(fileItem.getFieldName())) {
					layerId = fileItem.getString();
				}
			}
			if (input != null && layerId != null) {
				SessionManager.getInstance().getFacade(req.getSession()).addImage(layerId, input);
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
}

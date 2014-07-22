package com.blisscloud.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.blisscloud.constant.MY_SYS_CONSTANT;
import com.blisscloud.util.StringTools;

public class UploadAttachmentServlet extends HttpServlet {

	private static final long serialVersionUID = 1372718825744964920L;
	private String uploadPath = StringTools.getTimeDir(); // upload path
	private String tempPath = MY_SYS_CONSTANT.UPLOAD_EXCEL_TEMP_PATH; // upload
																		// temp
																		// path
	File tempPathFile;
	private static Log uploadAttachLog = LogFactory
			.getLog(UploadAttachmentServlet.class);

	/**
	 * doPost()
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			dirInit();
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Set factory constraints
			factory.setSizeThreshold(4096);
			factory.setRepository(tempPathFile);

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Set overall request size constraint
			upload.setSizeMax(1048576 * 10); // 10M

			// �ϴ��������µ��ļ����
			String lastFileNewName = "";

			List items = upload.parseRequest(request);
			Iterator iterator = items.iterator();
			while (iterator.hasNext()) {
				FileItem fi = (FileItem) iterator.next();
				// 1�������ı��ֶΣ���ȡ�ı��ֶε�ֵ
				if (fi.isFormField()
						&& fi.getFieldName().equalsIgnoreCase("fileNewName"))// isFormField()
				{
					lastFileNewName = fi.getString();
				}
				String fileName = fi.getName();
				// 2��������ı��ֶΣ������?���ļ�
				if (fileName != null && !fi.isFormField()) {
					File fullFile = new File(fi.getName());
					// ���request���µ��ļ��������ϴ����ļ���
					String fileNewName = fullFile.getName();
					if (!StringTools.isNull(lastFileNewName)) {
						String fileExtName = fileNewName.substring(fileNewName
								.lastIndexOf("."));
						fileNewName = lastFileNewName + fileExtName;
					}
					File saveFile = new File(uploadPath, fileNewName);
					fi.write(saveFile);
				}
			}
			uploadAttachLog.info("Attachments upload success!");

			// get file new Name
			String fileNewName = getNewFileName();

			response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			out.write("<html>\r\n");
			out.write("    ");
			String rootPath = request.getContextPath();
			out.write("\r\n");
			out.write("    ");
			out.write("<head>\r\n");
			out.write("<title>File upload</title>\r\n");
			// out.write("<script type=\"text/javascript\">");
			// out.write("alert(\"�ϴ��ɹ�!\");");
			// out.write("</script>");
			out.write("</head>\r\n");
			out.write("<body>\r\n");
			out.write("<form name=\"myform\" action=\""
					+ rootPath
					+ "/servlet/UploadAttachmentServlet\" method=\"post\" enctype=\"multipart/form-data\">\r\n");
			out.write("<input type=\"hidden\" name=\"fileNewName\" id=\"fileNewName\" value=\""
					+ fileNewName + "\"/>\r\n");
			out.write("<input type=\"file\" name=\"attach\" id=\"attach\" />\r\n");
			out.write("<input type=\"submit\" name=\"upload\" id=\"upload\" value=\"Commit\" />\r\n");
			out.write("</form>\r\n");
			out.write("</body>\r\n");
			out.write("</html>\r\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * dirInit()
	 */
	public void dirInit() throws ServletException {
		uploadPath = StringTools.getTimeDir();
		File uploadFile = new File(uploadPath);
		if (!uploadFile.exists()) {
			uploadFile.mkdirs();
		}
		File tempPathFile = new File(tempPath);
		if (tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
	}

	/**
	 * get new file name
	 * 
	 * @return
	 */
	private String getNewFileName() {
		String fileNewName = StringTools.getTimeSequeue();
		return fileNewName;
	}
}
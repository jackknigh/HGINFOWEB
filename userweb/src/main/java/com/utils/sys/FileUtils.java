package com.utils.sys;

import com.dto.pojo.spsys.system.DscvrFilesRsp;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author xuqy
 * @version version 1.0
 * @ClassName FileUtils
 * @Description: 〈文件工具类〉
 * @date 2018/10/31
 * All rights Reserved, Designed By SPINFO
 */
public class FileUtils
{

	/**
	 * 生成检查事件PDF报告
	 * @param dscvrFilesRsps
	 * @return
	 * @throws Exception
	 */
	public static String createEventPdfFile(List<DscvrFilesRsp> dscvrFilesRsps, String path) throws Exception
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BaseFont baseFontChinese = BaseFont.createFont(path + File.separatorChar + "simsun.ttf", BaseFont.IDENTITY_H,
				BaseFont.NOT_EMBEDDED);
		Paragraph newLine = new Paragraph("\n");

		// 1-创建文本对象 Document
		Document document = new Document(PageSize.A4, -30, -30, 0, 5);
		// 2-初始化 pdf输出对象 PdfWriter
		String fileName = System.currentTimeMillis() + ".pdf";
		String filePath = path + File.separator + fileName;
		PdfWriter.getInstance(document, new FileOutputStream(filePath));

		// 3-打开 Document
		document.open();

		// 检查时间
		Font detectDateFont = new Font(baseFontChinese, 10, Font.BOLD);
		Paragraph detectDatePar = new Paragraph("日期:" + format.format(new Date()), detectDateFont);
		detectDatePar.setIndentationLeft(465);
		document.add(detectDatePar);
		document.add(newLine);

		// 标题
		String systitle = "机要局检查事件报告";
		Font systitleFont = new Font(baseFontChinese, 20, Font.BOLD);
		Paragraph systitleParagraph = new Paragraph(systitle, systitleFont);
		systitleParagraph.setAlignment(Element.ALIGN_CENTER);
		document.add(systitleParagraph);
		document.add(newLine);

		// 生成表格
		PdfPTable table = new PdfPTable(10);
		table.setLockedWidth(false);
		table.setTotalWidth(1800);

		// 每列宽度
		table.setTotalWidth(new float[] { 55, 95, 95, 95, 95, 95, 95, 95, 95, 90 });

		Font headcellFont = new Font(baseFontChinese, 8, Font.BOLD);

		PdfPCell cell1 = new PdfPCell(new Phrase("序号", headcellFont));
		cell1.setUseAscender(true);
		cell1.setHorizontalAlignment(Element.ALIGN_CENTER); // 水平居中
		cell1.setVerticalAlignment(Element.ALIGN_MIDDLE); // 垂直居中
		table.addCell(cell1);

		PdfPCell cell2 = new PdfPCell(new Phrase("任务名称", headcellFont));
		cell2.setUseAscender(true);
		cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell2);

		PdfPCell cell3 = new PdfPCell(new Phrase("任务类型", headcellFont));
		cell3.setUseAscender(true);
		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell3);

		PdfPCell cell4 = new PdfPCell(new Phrase("任务状态", headcellFont));
		cell4.setUseAscender(true);
		cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell4);

		PdfPCell cell5 = new PdfPCell(new Phrase("文件名称", headcellFont));
		cell5.setUseAscender(true);
		cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell5);

		PdfPCell cell6 = new PdfPCell(new Phrase("文件类型", headcellFont));
		cell6.setUseAscender(true);
		cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell6);

		PdfPCell cell7 = new PdfPCell(new Phrase("文件路径", headcellFont));
		cell7.setUseAscender(true);
		cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell7);

		PdfPCell cell8 = new PdfPCell(new Phrase("检查时间", headcellFont));
		cell8.setUseAscender(true);
		cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell8);

		PdfPCell cell9 = new PdfPCell(new Phrase("是否加密", headcellFont));
		cell9.setUseAscender(true);
		cell9.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell9);

		PdfPCell cell10 = new PdfPCell(new Phrase("加密算法类型", headcellFont));
		cell10.setUseAscender(true);
		cell10.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell10);

		Font cellFont = new Font(baseFontChinese, 8);

		for (int i = 0; i < dscvrFilesRsps.size(); i++)
		{
			DscvrFilesRsp dscvrFilesRsp = dscvrFilesRsps.get(i);

			// 序号
			PdfPCell eventCell1 = new PdfPCell(new Phrase(i + 1 + "", cellFont));
			eventCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell1);

			// 任务名称
			PdfPCell eventCell2 = new PdfPCell(new Phrase(dscvrFilesRsp.getJobName(), cellFont));
			eventCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell2);

			// 任务类型
			PdfPCell eventCell3 = new PdfPCell(new Phrase(dscvrFilesRsp.getDatabaseType(), cellFont));
			eventCell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell3);

			// 任务状态
			PdfPCell eventCell4 = new PdfPCell(new Phrase(dscvrFilesRsp.getStatus(), cellFont));
			eventCell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell4);

			// 文件名称
			PdfPCell eventCell5 = new PdfPCell(new Phrase(dscvrFilesRsp.getFileName(), cellFont));
			eventCell5.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell5);

			// 文件类型
			PdfPCell eventCell6 = new PdfPCell(new Phrase(dscvrFilesRsp.getFileExtension(), cellFont));
			eventCell6.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell6);

			// 文件路径
			PdfPCell eventCell7 = new PdfPCell(new Phrase(dscvrFilesRsp.getFilePath(), cellFont));
			eventCell7.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell7);

			// 检查时间
			PdfPCell eventCell8 = new PdfPCell(new Phrase(format.format(dscvrFilesRsp.getDetectDateTs()), cellFont));
			eventCell8.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell8.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell8);

			// 是否加密
			PdfPCell eventCell9 = new PdfPCell(
					new Phrase((dscvrFilesRsp.getIsEncrypt() + "").equals("0.0") ? "否" : "是", cellFont));
			eventCell9.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell9.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell9);

			// 加密算法类型
			PdfPCell eventCell10 = new PdfPCell(new Phrase(dscvrFilesRsp.getAlgorithmType(), cellFont));
			eventCell10.setHorizontalAlignment(Element.ALIGN_CENTER);
			eventCell10.setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.addCell(eventCell10);

		}

		document.add(table);

		// 5-关闭 Document
		document.close();

		return fileName;
	}
}

package com.vivekSpringBoot.shopping.utility;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vivekSpringBoot.shopping.model.ProductOrder;
import com.vivekSpringBoot.shopping.service.OrderService;

@Service
public class OrdersPDF {

	@Autowired
	private OrderService orderServiceImpl;
	
	
	private void writeTableHeader(PdfPTable table) {
		
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("Sr No",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Order ID",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Delivery Details",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Order Date",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Product Detail",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Price",font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Status",font));
		table.addCell(cell);
		
	}
	
	
	private void writeTableData(PdfPTable table) {
		
		List<ProductOrder> allOrdersList = orderServiceImpl.getAllOrders();
		
		int count = 0;                // initialized the count
		
		for(ProductOrder order:allOrdersList) {
			
			table.addCell(String.valueOf(++count));
			table.addCell(order.getOrderId());
			table.addCell("Name : "+order.getOrderAddress().getFirstName()+" "+order.getOrderAddress().getLastName()+", Email : "+order.getOrderAddress().getEmail()+
					", Mobile No : "+order.getOrderAddress().getMobileNo()+", Address : "+order.getOrderAddress().getAddress()+
					", City : "+order.getOrderAddress().getCity()+", State : "+order.getOrderAddress().getState()+", Pincode : "+order.getOrderAddress().getPincode());
			table.addCell(""+order.getOrderDate());
			table.addCell(order.getProduct().getTitle());
			table.addCell("Quantity : "+order.getQuantity()+", Price : "+String.valueOf(order.getPrice())+", Total Price : "+String.valueOf(order.getPrice()*order.getQuantity()));
			table.addCell(order.getStatus());
			
		}
		
	}
	
	
	
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		
		// Set response type and header for browser download (suggested by chatGPT)
	  response.setContentType("application/pdf");
//	  response.setHeader("Content-Disposition", "attachment; filename=orders.pdf");                             // this will automatically download the pdf
	  response.setHeader("Content-Disposition", "inline; filename=orders.pdf");                          // this will show pdf and would not automatically download pdf

		
	Document document = new Document(PageSize.A4);
	PdfWriter.getInstance(document, response.getOutputStream());
	
	document.open();
	
	Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
	font.setSize(25);
	font.setColor(Color.RED);
	
	Paragraph paragraph = new Paragraph("Orders",font);                    // setting Header of the PDF
	paragraph.setAlignment(Paragraph.ALIGN_CENTER);
	
	document.add(paragraph);
	
	PdfPTable table = new PdfPTable(7);                        // setting number of columns in the table
	table.setWidthPercentage(100f);
	table.setWidths(new float[] {1.0f,3.0f,3.0f,3.0f,3.0f,3.0f,3.0f});
	table.setSpacingBefore(10.0f);
	
	writeTableHeader(table);
	writeTableData(table);
	
	document.add(table);
	
	document.close();
	
	}
	
	
}

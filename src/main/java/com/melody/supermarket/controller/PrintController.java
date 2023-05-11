package com.melody.supermarket.controller;

import com.melody.supermarket.pojo.Sale;
import com.melody.supermarket.services.SaleServices;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/print")
public class PrintController {
    @Resource
    SaleServices saleServices;
    @SneakyThrows
    @GetMapping("/sales")
    public void printSales(HttpServletResponse response) {
        List<Sale> sales = saleServices.findAll();
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=sale.xls");
        try(Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            Row rowHeader = sheet.createRow(0);
            rowHeader.createCell(0).setCellValue("销售单号");
            rowHeader.createCell(1).setCellValue("销售日期");
            rowHeader.createCell(2).setCellValue("销售数量");
            rowHeader.createCell(3).setCellValue("商品名称");
            rowHeader.createCell(4).setCellValue("商品单价");
            rowHeader.createCell(5).setCellValue("销售总价");
            for (int i = 0; i < sales.size(); i++) {
                sheet.autoSizeColumn(i);
                Sale sale = sales.get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(sale.getId());
                row.createCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(sale.getCreateDate()));
                row.createCell(2).setCellValue(sale.getSaleCount());
                row.createCell(3).setCellValue(sale.getProduct().getName());
                row.createCell(4).setCellValue(sale.getProduct().getNowPrice().toString());
                row.createCell(5).setCellValue((new BigDecimal(sale.getSaleCount().toString()).multiply(sale.getProduct().getNowPrice())).toString());
            }
            workbook.write(outputStream);
            outputStream.close();
        }
    }
}

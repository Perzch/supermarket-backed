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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/print")
public class PrintController {
    @Resource
    SaleServices saleServices;

    /***
     * 导出销售记录
     * @param response 响应对象
     */
    @SneakyThrows
    @GetMapping("/sales")
    public void printSales(HttpServletResponse response) {
        List<Sale> sales = saleServices.findAll();
        ServletOutputStream outputStream = response.getOutputStream();
//        设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment;filename=sale.xls");
        try(Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
//            设置表格头
            Row rowHeader = sheet.createRow(0);
            rowHeader.createCell(0).setCellValue("销售单号");
            rowHeader.createCell(1).setCellValue("销售日期");
            rowHeader.createCell(2).setCellValue("商品信息");
            Row rowHeader2 = sheet.createRow(1);
            rowHeader2.createCell(2).setCellValue("商品编号");
            rowHeader2.createCell(3).setCellValue("商品名称");
            rowHeader2.createCell(4).setCellValue("所属分类");
            rowHeader2.createCell(5).setCellValue("商品进价");
            rowHeader2.createCell(6).setCellValue("商品售价");
            rowHeader2.createCell(7).setCellValue("商品销量");
//            循环设置表格内容
            for (int i = 0; i < sales.size(); i++) {
                sheet.autoSizeColumn(i + 2);
                Sale sale = sales.get(i);
                Row rowMain = sheet.createRow(i + 2);
                rowMain.createCell(0).setCellValue(sale.getId());
                rowMain.createCell(1).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(sale.getCreateDate()));
                for (int j = 0; j< sale.getSaleProducts().size(); j++) {
                    if(i == j) {

                        rowMain.createCell(2).setCellValue(sale.getProducts().get(j).getId());
                        rowMain.createCell(3).setCellValue(sale.getProducts().get(j).getName());
                        rowMain.createCell(4).setCellValue(sale.getProducts().get(j).getCategoryName());
                        rowMain.createCell(5).setCellValue(sale.getProducts().get(j).getPrice().doubleValue());
                        rowMain.createCell(6).setCellValue(sale.getProducts().get(j).getNowPrice().doubleValue());
                        int finalJ = j;
                        rowMain.createCell(7).setCellValue(sale.getSaleProducts().stream().filter(saleProduct -> Objects.equals(saleProduct.getProduct().getId(), sale.getProducts().get(finalJ).getId())).findFirst().get().getCount());
                    } else {
                        Row row = sheet.createRow(j +2);
                        row.createCell(2).setCellValue(sale.getProducts().get(j).getId());
                        row.createCell(3).setCellValue(sale.getProducts().get(j).getName());
                        row.createCell(4).setCellValue(sale.getProducts().get(j).getCategoryName());
                        row.createCell(5).setCellValue(sale.getProducts().get(j).getPrice().doubleValue());
                        row.createCell(6).setCellValue(sale.getProducts().get(j).getNowPrice().doubleValue());
                        int finalJ = j;
                        row.createCell(7).setCellValue(sale.getSaleProducts().stream().filter(saleProduct -> Objects.equals(saleProduct.getProduct().getId(), sale.getProducts().get(finalJ).getId())).findFirst().get().getCount());
                    }
            }
//                row.createCell(2).setCellValue(sale.getSaleCount());
//                row.createCell(3).setCellValue(sale.getProducts().stream().reduce(BigDecimal.ZERO, (a, b) -> a.add(b.getPrice()), BigDecimal::add).multiply(new BigDecimal(sale.getSaleCount())).doubleValue());
            }
            workbook.write(outputStream);
            outputStream.close();
        }
    }
}

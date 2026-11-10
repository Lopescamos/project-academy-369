package com.olimpiadas369.backend.exportacion.infrastructure.excel;

import com.olimpiadas369.backend.registro.domain.model.aggregates.Registro;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Genera un Excel (.xlsx) con las fichas confirmadas de un bloque.
 * La plantilla exacta (cabeceras, logo, colores) se afina contra el formato oficial.
 */
@Component
public class ExcelPorBloqueGenerator {

    public byte[] generar(Long bloqueId, List<Registro> registros) throws Exception {
        try (XSSFWorkbook wb = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = wb.createSheet("Bloque " + bloqueId);

            // Cabecera con estilo
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            CellStyle headerStyle = wb.createCellStyle();
            headerStyle.setFont(headerFont);

            String[] headers = {"#", "Nombre", "Apellidos", "Colegio", "Correctas", "Incorrectas", "Anuladas", "Blancos", "Puntaje"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Ordenamos por puntaje desc para armar el ranking
            List<Registro> ranking = registros.stream()
                    .sorted((a, b) -> Integer.compare(b.getPuntaje() == null ? 0 : b.getPuntaje(),
                            a.getPuntaje() == null ? 0 : a.getPuntaje()))
                    .toList();

            int rowIdx = 1;
            int pos = 1;
            for (Registro r : ranking) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(pos++);
                row.createCell(1).setCellValue(nullSafe(r.getNombre()));
                row.createCell(2).setCellValue(nullSafe(r.getApellidos()));
                row.createCell(3).setCellValue(nullSafe(r.getColegioTextoExtraido()));
                row.createCell(4).setCellValue(r.getCorrectas() == null ? 0 : r.getCorrectas());
                row.createCell(5).setCellValue(r.getIncorrectas() == null ? 0 : r.getIncorrectas());
                row.createCell(6).setCellValue(r.getAnuladas() == null ? 0 : r.getAnuladas());
                row.createCell(7).setCellValue(r.getBlancos() == null ? 0 : r.getBlancos());
                row.createCell(8).setCellValue(r.getPuntaje() == null ? 0 : r.getPuntaje());
            }

            for (int i = 0; i < headers.length; i++) sheet.autoSizeColumn(i);
            wb.write(out);
            return out.toByteArray();
        }
    }

    private static String nullSafe(String s) { return s == null ? "" : s; }
}

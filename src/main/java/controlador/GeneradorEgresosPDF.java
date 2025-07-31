package controlador;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;

public class GeneradorEgresosPDF {

    // Configuración de diseño
    private static final float MARGIN_LEFT = 50;
    private static final float MARGIN_TOP = 70;
    private static final float LOGO_WIDTH = 120;
    private static final float LOGO_HEIGHT = 80;
    private static final float ROW_HEIGHT = 20;
    private static final float FOOTER_Y = 50;
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();

    // Formatos
    private final DecimalFormat decimalFormat = new DecimalFormat("$#,##0.00");
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public void generarPDF(DefaultTableModel tablaModel, String fechaInicio, String fechaFin,
                          String montoTotal, String archivoSalida) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            try {
                float yPosition = setupPage(document, contentStream, page, fechaInicio, fechaFin);
                yPosition = addTable(contentStream, document, page, tablaModel, yPosition);
                addFooter(contentStream, yPosition, montoTotal);
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }

            File file = new File(archivoSalida);
            document.save(file);
            abrirDocumento(file);
        } catch (Exception e) {
            mostrarError("Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private float setupPage(PDDocument document, PDPageContentStream contentStream,
                           PDPage page, String fechaInicio, String fechaFin) throws IOException {
        float yPosition = agregarLogoYEncabezado(document, contentStream);
        yPosition = agregarInformacionReporte(contentStream, yPosition, fechaInicio, fechaFin);
        return yPosition;
    }

    private float agregarLogoYEncabezado(PDDocument document, PDPageContentStream contentStream) throws IOException {
        float yPosition = PDRectangle.A4.getHeight() - 40;

        try {
            URL imageUrl = getClass().getResource("/imagenes/logo_azul_sin_letras.png");
            if (imageUrl != null) {
                PDImageXObject pdImage = PDImageXObject.createFromFile(imageUrl.getPath(), document);
                float logoX = MARGIN_LEFT;
                float logoY = yPosition - LOGO_HEIGHT;
                contentStream.drawImage(pdImage, logoX, logoY, LOGO_WIDTH, LOGO_HEIGHT);

                float textoX = logoX + LOGO_WIDTH + 50;
                float textoY = logoY + LOGO_HEIGHT - 20;

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                escribirTexto(contentStream, "Carpintería y Muebles JoseAbel", textoX, textoY);
                contentStream.setFont(PDType1Font.HELVETICA, 11);
                escribirTexto(contentStream, "Duitama - Boyacá", textoX, textoY - 15);
                escribirTexto(contentStream, "Calle 26 #17-46", textoX, textoY - 30);
                escribirTexto(contentStream, "Cel: 314 7352468", textoX, textoY - 45);

                float lineaY = logoY - 10;
                contentStream.setStrokingColor(183, 183, 183);
                dibujarLinea(contentStream, MARGIN_LEFT, lineaY, PDRectangle.A4.getWidth() - 2 * MARGIN_LEFT);
                contentStream.setStrokingColor(0, 0, 0);

                return lineaY - 20;
            }
        } catch (IOException e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }

        return yPosition - LOGO_HEIGHT - 20;
    }

    private float agregarInformacionReporte(PDPageContentStream contentStream, float yPosition,
                                           String fechaInicio, String fechaFin) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
        escribirTextoCentrado(contentStream, "REPORTE DE EGRESOS", yPosition);
        yPosition -= 40;

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        escribirTexto(contentStream, "Fecha de Emisión: " + dateFormat.format(new Date()), MARGIN_LEFT, yPosition);
        yPosition -= 20;
        escribirTexto(contentStream, "Rango de Fechas: " + safeString(fechaInicio) + " a " + safeString(fechaFin), MARGIN_LEFT, yPosition);
        yPosition -= 30;

        return yPosition;
    }

    private float addTable(PDPageContentStream contentStream, PDDocument document,
                          PDPage page, DefaultTableModel tablaModel, float yPosition) throws IOException {
        String[] headers = {"Fecha Pago", "Monto", "Descripción", "Categoría"};
        float[] columnWidths = {85,100,120,150};

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        yPosition = dibujarEncabezadosTabla(contentStream, headers, columnWidths, yPosition);

        contentStream.setFont(PDType1Font.HELVETICA, 9);
        for (int row = 0; row < tablaModel.getRowCount(); row++) {
            if (yPosition < FOOTER_Y + 50) {
                contentStream.close();
                page = nuevaPagina(document);
                contentStream = new PDPageContentStream(document, page);
                yPosition = PDRectangle.A4.getHeight() - MARGIN_TOP;
                yPosition = dibujarEncabezadosTabla(contentStream, headers, columnWidths, yPosition);
                contentStream.setFont(PDType1Font.HELVETICA, 9);
            }
            yPosition = dibujarFila(contentStream, tablaModel, row, columnWidths, yPosition);
        }

        return yPosition;
    }

    private PDPage nuevaPagina(PDDocument document) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        return page;
    }

    private float dibujarEncabezadosTabla(PDPageContentStream contentStream,
                                         String[] headers, float[] widths, float y) throws IOException {
        float x = MARGIN_LEFT;
        float desplazamientoTexto = 6;
        for (int i = 0; i < headers.length; i++) {
            escribirTexto(contentStream, headers[i], x, y - desplazamientoTexto);
            x += widths[i];
        }
        y -= 15;
        dibujarLinea(contentStream, MARGIN_LEFT, y, calcularAnchoTotal(widths));
        return y - 10;
    }

    private float dibujarFila(PDPageContentStream contentStream, DefaultTableModel model,
                             int row, float[] widths, float y) throws IOException {
        float x = MARGIN_LEFT;
        for (int col = 0; col < widths.length; col++) {
            Object value = model.getValueAt(row, col);
            String text = formatearValor(value, col == 1); // Formatear como moneda solo la columna "Monto" (índice 1)
            escribirTextoTabla(contentStream, text, x, y);
            x += widths[col];
        }
        y -= 15;
        dibujarLinea(contentStream, MARGIN_LEFT, y, calcularAnchoTotal(widths), 0.5f);
        return y - 5;
    }

    private void addFooter(PDPageContentStream contentStream, float yPosition, String montoTotal) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        float columnaDerechaX = PAGE_WIDTH - MARGIN_LEFT - 150;
        escribirTexto(contentStream, "Total Egresos:", columnaDerechaX, yPosition - 20);
        escribirTexto(contentStream, safeString(montoTotal), columnaDerechaX + 70, yPosition - 20);

        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
        escribirTextoCentrado(contentStream, "Gracias por su preferencia - "
                + new SimpleDateFormat("yyyy").format(new Date()), FOOTER_Y);
    }

    private String safeString(Object value, String defaultValue) {
        return value != null ? value.toString() : defaultValue;
    }

    private String safeString(Object value) {
        return safeString(value, "");
    }

    private String formatearValor(Object value, boolean esMoneda) {
        if (value == null) {
            return "";
        }
        String text = value.toString();
        if (esMoneda) {
            try {
                double num = Double.parseDouble(text.replaceAll("[^\\d.]", ""));
                return decimalFormat.format(num);
            } catch (NumberFormatException e) {
                return "$0.00";
            }
        }
        return text;
    }

    private float calcularAnchoTotal(float[] widths) {
        float total = 0;
        for (float w : widths) {
            total += w;
        }
        return total;
    }

    private void escribirTexto(PDPageContentStream stream, String text, float x, float y) throws IOException {
        stream.beginText();
        stream.newLineAtOffset(x, y);
        stream.showText(text);
        stream.endText();
    }

    private void escribirTextoCentrado(PDPageContentStream stream, String text, float y) throws IOException {
        float textWidth = PDType1Font.HELVETICA.getStringWidth(text) / 1000 * 10;
        float x = (PDRectangle.A4.getWidth() - textWidth) / 2;
        escribirTexto(stream, text, x, y);
    }

    private void dibujarLinea(PDPageContentStream stream, float x, float y, float length) throws IOException {
        dibujarLinea(stream, x, y, length, 1f);
    }

    private void dibujarLinea(PDPageContentStream stream, float x, float y, float length, float width) throws IOException {
        stream.setLineWidth(width);
        stream.moveTo(x, y);
        stream.lineTo(x + length, y);
        stream.stroke();
    }

    private void abrirDocumento(File file) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void escribirTextoTabla(PDPageContentStream stream, String text, float x, float y) throws IOException {
        float textoDesplazamiento = 10;
        stream.beginText();
        stream.newLineAtOffset(x, y - textoDesplazamiento);
        stream.showText(text);
        stream.endText();
    }
}
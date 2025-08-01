package controlador;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GeneradorIngresosPDF {
    private static final float MARGIN_LEFT = 50;
    private static final float MARGIN_TOP = 50;
    private static final float PAGE_WIDTH = PDRectangle.A4.getWidth();
    private static final float PAGE_HEIGHT = PDRectangle.A4.getHeight();
    private static final float FOOTER_Y = 50;
    private static final float LINE_SPACING = 15;

    public void generarReporteConsolidado(DefaultTableModel model, String fechaInicio, String fechaFin,
            String totalMonto, String archivoSalida) {
        System.out.println("Generando reporte consolidado - Inicio: filas=" + (model != null ? model.getRowCount() : "null") +
                          ", fechaInicio='" + fechaInicio + "', fechaFin='" + fechaFin + 
                          "', totalMonto='" + totalMonto + "', archivoSalida='" + archivoSalida + "'");
        if (model == null || model.getRowCount() == 0) {
            System.out.println("Error: Modelo de datos nulo o vacío.");
            mostrarError("No hay datos para generar el reporte consolidado.");
            return;
        }
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            try {
                float yPosition = agregarEncabezado(contentStream);
                yPosition = agregarInfoPeriodo(contentStream, yPosition, fechaInicio, fechaFin);
                yPosition = agregarTablaAbonos(contentStream, document, page, model, yPosition);
                agregarTotal(contentStream, yPosition, totalMonto);
                agregarPiePagina(contentStream);
            } catch (IOException e) {
                System.out.println("Error en contenido del PDF: " + e.getMessage());
                e.printStackTrace();
                throw e;
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }

            File file = new File(archivoSalida);
            document.save(file);
            abrirDocumento(file);
        } catch (IOException e) {
            System.out.println("Error al guardar o abrir el PDF: " + e.getMessage());
            mostrarError("Error al generar reporte consolidado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void generarPDF(String nombreCliente, int codigoCliente, String telefonoCliente,
            String direccionCliente, String departamentoCliente, String municipioCliente,
            DefaultTableModel tablaModel, String montoTotal, String archivoSalida,
            String fechaPedido, String numPedido, String pagado, String debido) {
        System.out.println("Generando PDF - Inicio: nombreCliente='" + nombreCliente + 
                          "', codigoCliente=" + codigoCliente + ", fechaPedido='" + fechaPedido + 
                          "', numPedido='" + numPedido + "', filas=" + (tablaModel != null ? tablaModel.getRowCount() : "null"));
        if (tablaModel == null || tablaModel.getRowCount() == 0) {
            System.out.println("Error: Modelo de datos nulo o vacío.");
            mostrarError("No hay datos para generar el PDF.");
            return;
        }
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            try {
                float yPosition = agregarLogoYEncabezado(document, contentStream);
                yPosition = agregarInformacionCliente(contentStream, yPosition, nombreCliente, codigoCliente,
                        telefonoCliente, direccionCliente, departamentoCliente, municipioCliente,
                        fechaPedido, numPedido);
                yPosition = addTableDetalles(contentStream, document, page, tablaModel, yPosition);
                addResumenPagos(contentStream, yPosition, montoTotal, pagado, debido);
            } catch (IOException e) {
                System.out.println("Error en contenido del PDF: " + e.getMessage());
                e.printStackTrace();
                throw e;
            } finally {
                if (contentStream != null) {
                    contentStream.close();
                }
            }

            File file = new File(archivoSalida);
            document.save(file);
            abrirDocumento(file);
        } catch (IOException e) {
            System.out.println("Error al guardar o abrir el PDF: " + e.getMessage());
            mostrarError("Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private float agregarEncabezado(PDPageContentStream contentStream) throws IOException {
        float yPosition = PAGE_HEIGHT - MARGIN_TOP;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        escribirTextoCentrado(contentStream, "REPORTE CONSOLIDADO DE ABONOS", yPosition);
        yPosition -= 50;
        return yPosition;
    }

    private float agregarInfoPeriodo(PDPageContentStream contentStream, float yPosition,
            String fechaInicio, String fechaFin) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        escribirTexto(contentStream, "Período: " + safeString(fechaInicio) + " a " + safeString(fechaFin),
                MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING * 2;
        return yPosition;
    }

    private float agregarTablaAbonos(PDPageContentStream contentStream, PDDocument document,
            PDPage page, DefaultTableModel model, float yPosition) throws IOException {
        String[] headers = {"# Pedido", "Cliente", "Fecha Abono", "Monto Abono", "Método Pago"};
        float[] columnWidths = {80, 150, 100, 100, 100};

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        yPosition = dibujarEncabezadosTabla(contentStream, headers, columnWidths, yPosition);

        contentStream.setFont(PDType1Font.HELVETICA, 10);
        for (int row = 0; row < model.getRowCount(); row++) {
            if (yPosition < FOOTER_Y + 50) {
                contentStream.close();
                page = nuevaPagina(document);
                contentStream = new PDPageContentStream(document, page);
                yPosition = PAGE_HEIGHT - MARGIN_TOP;
                yPosition = dibujarEncabezadosTabla(contentStream, headers, columnWidths, yPosition);
            }
            yPosition = dibujarFila(contentStream, model, row, columnWidths, yPosition);
        }
        return yPosition;
    }

    private void agregarTotal(PDPageContentStream contentStream, float yPosition, String totalMonto)
            throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        float columnaDerechaX = PAGE_WIDTH - MARGIN_LEFT - 150;
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Total Acumulado:", columnaDerechaX, yPosition);
        escribirTexto(contentStream, safeString(totalMonto), columnaDerechaX + 90, yPosition);
    }

    private void agregarPiePagina(PDPageContentStream contentStream) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
        escribirTextoCentrado(contentStream, "Reporte generado - " + new SimpleDateFormat("yyyy").format(new Date()), FOOTER_Y);
    }

    private float agregarLogoYEncabezado(PDDocument document, PDPageContentStream contentStream) throws IOException {
        float yPosition = PAGE_HEIGHT - MARGIN_TOP;
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
        escribirTextoCentrado(contentStream, "DETALLE DE PEDIDO", yPosition);
        yPosition -= 50;
        return yPosition;
    }

    private float agregarInformacionCliente(PDPageContentStream contentStream, float yPosition,
            String nombreCliente, int codigoCliente, String telefonoCliente, String direccionCliente,
            String departamentoCliente, String municipioCliente, String fechaPedido, String numPedido)
            throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        escribirTexto(contentStream, "Cliente: " + safeString(nombreCliente), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Código Cliente: " + codigoCliente, MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Teléfono: " + safeString(telefonoCliente), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Dirección: " + safeString(direccionCliente), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Departamento: " + safeString(departamentoCliente), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Municipio: " + safeString(municipioCliente), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Fecha Pedido: " + safeString(fechaPedido), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Número Pedido: " + safeString(numPedido), MARGIN_LEFT, yPosition);
        yPosition -= LINE_SPACING * 2;
        return yPosition;
    }

    private float addTableDetalles(PDPageContentStream contentStream, PDDocument document,
            PDPage page, DefaultTableModel tablaModel, float yPosition) throws IOException {
        String[] headers = {"Descripción", "Cantidad", "Dimensiones", "Precio Unitario", "Subtotal", "Total"};
        float[] columnWidths = {150, 60, 80, 80, 80, 80};

        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        yPosition = dibujarEncabezadosTabla(contentStream, headers, columnWidths, yPosition);

        contentStream.setFont(PDType1Font.HELVETICA, 10);
        for (int row = 0; row < tablaModel.getRowCount(); row++) {
            if (yPosition < FOOTER_Y + 50) {
                contentStream.close();
                page = nuevaPagina(document);
                contentStream = new PDPageContentStream(document, page);
                yPosition = PAGE_HEIGHT - MARGIN_TOP;
                yPosition = dibujarEncabezadosTabla(contentStream, headers, columnWidths, yPosition);
            }
            Object descripcion = tablaModel.getValueAt(row, 0);
            if (descripcion != null && descripcion.toString().equals("ABONOS REGISTRADOS")) {
                yPosition -= LINE_SPACING * 1.5f;
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
                escribirTexto(contentStream, "Abonos Registrados:", MARGIN_LEFT, yPosition);
                yPosition -= LINE_SPACING;
                contentStream.setFont(PDType1Font.HELVETICA, 10);
                String abonosInfo = safeString(tablaModel.getValueAt(row, 1));
                String[] lineasAbonos = abonosInfo.split("\n");
                for (String linea : lineasAbonos) {
                    if (yPosition < FOOTER_Y + 50) {
                        contentStream.close();
                        page = nuevaPagina(document);
                        contentStream = new PDPageContentStream(document, page);
                        yPosition = PAGE_HEIGHT - MARGIN_TOP;
                    }
                    escribirTexto(contentStream, linea, MARGIN_LEFT, yPosition);
                    yPosition -= LINE_SPACING;
                }
            } else {
                yPosition = dibujarFila(contentStream, tablaModel, row, columnWidths, yPosition);
            }
        }
        return yPosition;
    }

    private void addResumenPagos(PDPageContentStream contentStream, float yPosition,
            String montoTotal, String pagado, String debido) throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
        float columnaDerechaX = PAGE_WIDTH - MARGIN_LEFT - 150;
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Monto Total:", columnaDerechaX, yPosition);
        escribirTexto(contentStream, safeString(montoTotal), columnaDerechaX + 70, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Pagado:", columnaDerechaX, yPosition);
        escribirTexto(contentStream, safeString(pagado), columnaDerechaX + 70, yPosition);
        yPosition -= LINE_SPACING;
        escribirTexto(contentStream, "Debido:", columnaDerechaX, yPosition);
        escribirTexto(contentStream, safeString(debido), columnaDerechaX + 70, yPosition);
        yPosition -= LINE_SPACING;
        contentStream.setFont(PDType1Font.HELVETICA_OBLIQUE, 9);
        escribirTextoCentrado(contentStream, "Gracias por su preferencia - " + new SimpleDateFormat("yyyy").format(new Date()), FOOTER_Y);
    }

    private float dibujarEncabezadosTabla(PDPageContentStream contentStream, String[] headers,
            float[] columnWidths, float yPosition) throws IOException {
        float x = MARGIN_LEFT;
        for (int i = 0; i < headers.length; i++) {
            escribirTexto(contentStream, headers[i], x, yPosition);
            x += columnWidths[i];
        }
        return yPosition - LINE_SPACING;
    }

    private float dibujarFila(PDPageContentStream contentStream, DefaultTableModel model,
            int row, float[] columnWidths, float yPosition) throws IOException {
        float x = MARGIN_LEFT;
        for (int i = 0; i < model.getColumnCount(); i++) {
            String value = safeString(model.getValueAt(row, i));
            escribirTexto(contentStream, value, x, yPosition);
            x += columnWidths[i];
        }
        return yPosition - LINE_SPACING;
    }

    private PDPage nuevaPagina(PDDocument document) {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        return page;
    }

    private void escribirTexto(PDPageContentStream contentStream, String text, float x, float y)
            throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    private void escribirTextoCentrado(PDPageContentStream contentStream, String text, float y)
            throws IOException {
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20); // Tamaño fijo para consistencia
        float textWidth = PDType1Font.HELVETICA_BOLD.getStringWidth(text) / 1000 * 20;
        float x = (PAGE_WIDTH - textWidth) / 2;
        if (x < MARGIN_LEFT || x + textWidth > PAGE_WIDTH - MARGIN_LEFT) {
            System.out.println("Advertencia: Texto fuera de márgenes - x=" + x + ", textWidth=" + textWidth + ", ajustando a " + MARGIN_LEFT);
            x = MARGIN_LEFT; // Ajuste de emergencia
        }
        System.out.println("Centrando texto: '" + text + "', y=" + y + ", textWidth=" + textWidth + ", x=" + x);
        if (y < FOOTER_Y) {
            System.out.println("Advertencia: yPosition (" + y + ") por debajo de FOOTER_Y (" + FOOTER_Y + "), omitiendo texto.");
            return;
        }
        escribirTexto(contentStream, text, x, y);
    }

    private String safeString(Object obj) {
        return obj != null ? obj.toString() : "";
    }

    private void abrirDocumento(File file) throws IOException {
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(file);
        }
    }

    private void mostrarError(String mensaje) {
        javax.swing.JOptionPane.showMessageDialog(null, mensaje, "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    // Método de prueba para verificar el centrado y separación
    public void generarPDFPrueba(String archivoSalida) {
        System.out.println("Generando PDF de prueba: archivo=" + archivoSalida);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            try {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                float y = PAGE_HEIGHT - MARGIN_TOP;
                escribirTextoCentrado(contentStream, "Prueba de Centrado 1", y);
                y -= 50;
                escribirTextoCentrado(contentStream, "Prueba de Centrado 2", y);
                y -= 50;
                escribirTextoCentrado(contentStream, "Prueba de Centrado 3", y);
            } finally {
                contentStream.close();
            }
            File file = new File(archivoSalida);
            document.save(file);
            abrirDocumento(file);
        } catch (IOException e) {
            System.out.println("Error en PDF de prueba: " + e.getMessage());
            mostrarError("Error en PDF de prueba: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
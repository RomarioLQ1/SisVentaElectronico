/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Modelo.Venta;
import Modelo.DetalleVenta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class GeneradorPDF {

    public static byte[] generarPDFComoBytes(Venta venta, int idVenta) throws Exception {
        if (venta.getTipoComprobante().equalsIgnoreCase("BOLETA")) {
            return generarBoletaPDF(venta, idVenta);
        } else {
            return generarFacturaPDF(venta, idVenta);
        }
    }

    public static byte[] generarFacturaPDF(Venta venta, int idVenta) throws Exception {
        return generarPDF(venta, idVenta, "FACTURA ELECTRÓNICA", "F001-");
    }

    public static byte[] generarBoletaPDF(Venta venta, int idVenta) throws Exception {
        return generarPDF(venta, idVenta, "BOLETA DE VENTA", "B001-");
    }

    private static byte[] generarPDF(Venta venta, int idVenta, String tipoDocumento, String serie) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 36, 36, 80, 36);
        PdfWriter.getInstance(doc, baos);
        doc.open();

        DecimalFormat df = new DecimalFormat("0.00");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // === Encabezado ===
        PdfPTable encabezado = new PdfPTable(2);
        encabezado.setWidthPercentage(100);
        encabezado.setWidths(new float[]{70, 30});

        PdfPCell datosEmpresa = new PdfPCell();
        datosEmpresa.setBorder(Rectangle.NO_BORDER);

        Image img = Image.getInstance("src/main/resources/img/electro.png");
        img.scaleToFit(80, 80);
        datosEmpresa.addElement(img);

        datosEmpresa.addElement(new Paragraph("ElectroNova S.A.C.", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        datosEmpresa.addElement(new Paragraph("CONTABILIDAD E INFORMÁTICA"));
        datosEmpresa.addElement(new Paragraph("Av. Los Negocios 123 - Lima, Perú"));
        datosEmpresa.addElement(new Paragraph("Tel: (01) 345-6789"));
        datosEmpresa.addElement(new Paragraph("Email: info@electroNova.pe"));

        PdfPCell datosFactura = new PdfPCell();
        datosFactura.setBorder(Rectangle.BOX);
        datosFactura.setPadding(10);
        datosFactura.addElement(new Paragraph("RUC: 20602655301", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        datosFactura.addElement(new Paragraph(tipoDocumento, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        datosFactura.addElement(new Paragraph(serie + String.format("%08d", idVenta), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));

        encabezado.addCell(datosEmpresa);
        encabezado.addCell(datosFactura);

        doc.add(encabezado);
        doc.add(new Paragraph(" "));

        // === Datos del cliente ===
        PdfPTable datosCliente = new PdfPTable(2);
        datosCliente.setWidthPercentage(100);
        datosCliente.setSpacingBefore(10);

        datosCliente.addCell(getCell("Cliente: " + venta.getNombreCliente(), PdfPCell.ALIGN_LEFT));
        datosCliente.addCell(getCell("Fecha de emisión: " + sdf.format(venta.getFecha()), PdfPCell.ALIGN_LEFT));
        datosCliente.addCell(getCell("DNI/RUC: " + venta.getDniCliente(), PdfPCell.ALIGN_LEFT));
        datosCliente.addCell(getCell("Fecha de vencimiento: " + sdf.format(venta.getFecha()), PdfPCell.ALIGN_LEFT));

        doc.add(datosCliente);
        doc.add(new Paragraph(" "));

        // === Tabla de productos ===
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{4, 1, 2, 2});

        tabla.addCell(getHeaderCell("DESCRIPCIÓN"));
        tabla.addCell(getHeaderCell("CANT."));
        tabla.addCell(getHeaderCell("P.UNIT"));
        tabla.addCell(getHeaderCell("TOTAL"));

        for (DetalleVenta d : venta.getDetalles()) {
            tabla.addCell(getCell(d.getProducto(), PdfPCell.ALIGN_LEFT));
            tabla.addCell(getCell(String.valueOf(d.getCantidad()), PdfPCell.ALIGN_CENTER));
            tabla.addCell(getCell("S/ " + df.format(d.getPrecioUnitario()), PdfPCell.ALIGN_RIGHT));
            tabla.addCell(getCell("S/ " + df.format(d.getSubtotal()), PdfPCell.ALIGN_RIGHT));
        }

        doc.add(tabla);
        doc.add(new Paragraph(" "));

        // === Totales ===
        PdfPTable totales = new PdfPTable(2);
        totales.setWidths(new float[]{70, 30});
        totales.setWidthPercentage(100);

        totales.addCell(getCell("", PdfPCell.ALIGN_LEFT)); // vacío

        PdfPTable subTabla = new PdfPTable(2);
        subTabla.setWidthPercentage(100);

        if (venta.getTipoComprobante().equalsIgnoreCase("FACTURA")) {
            double subtotal = venta.getTotal() / 1.18;
            double igv = venta.getTotal() - subtotal;

            subTabla.addCell(getCell("OP. GRAVADAS:", PdfPCell.ALIGN_RIGHT));
            subTabla.addCell(getCell("S/ " + df.format(subtotal), PdfPCell.ALIGN_RIGHT));

            subTabla.addCell(getCell("IGV (18%):", PdfPCell.ALIGN_RIGHT));
            subTabla.addCell(getCell("S/ " + df.format(igv), PdfPCell.ALIGN_RIGHT));
        }

        subTabla.addCell(getCell("TOTAL A PAGAR:", PdfPCell.ALIGN_RIGHT));
        subTabla.addCell(getCell("S/ " + df.format(venta.getTotal()), PdfPCell.ALIGN_RIGHT));

        PdfPCell totalesCell = new PdfPCell(subTabla);
        totalesCell.setBorder(Rectangle.NO_BORDER);
        totales.addCell(totalesCell);

        doc.add(totales);
        doc.add(new Paragraph(" "));

        // Total en letras
        doc.add(new Paragraph("SON: " + convertirNumeroALetras(venta.getTotal())));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph("Banco BCP - CCI: 00257000247225009604", FontFactory.getFont(FontFactory.HELVETICA, 10)));

        doc.close();
        return baos.toByteArray();
    }

    private static PdfPCell getCell(String texto, int alineacion) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA, 10)));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alineacion);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    private static PdfPCell getHeaderCell(String texto) {
        PdfPCell cell = new PdfPCell(new Phrase(texto, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        return cell;
    }

    public static String convertirNumeroALetras(double numero) {
        String[] unidades = {
            "", "UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS",
            "SIETE", "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE",
            "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE",
            "DIECIOCHO", "DIECINUEVE"
        };

        String[] decenas = {
            "", "", "VEINTE", "TREINTA", "CUARENTA", "CINCUENTA",
            "SESENTA", "SETENTA", "OCHENTA", "NOVENTA"
        };

        String[] centenas = {
            "", "CIENTO", "DOSCIENTOS", "TRESCIENTOS", "CUATROCIENTOS",
            "QUINIENTOS", "SEISCIENTOS", "SETECIENTOS", "OCHOCIENTOS", "NOVECIENTOS"
        };

        int enteros = (int) numero;
        int decimales = (int) Math.round((numero - enteros) * 100);
        StringBuilder resultado = new StringBuilder();

        if (enteros == 0) return "CERO CON " + String.format("%02d", decimales) + "/100 SOLES";

        if (enteros >= 1000000) {
            int millones = enteros / 1000000;
            resultado.append(convertirNumeroALetras(millones)).append(" MILLONES ");
            enteros %= 1000000;
        }

        if (enteros >= 1000) {
            int miles = enteros / 1000;
            if (miles == 1) {
                resultado.append("MIL ");
            } else {
                resultado.append(convertirNumeroALetras(miles)).append(" MIL ");
            }
            enteros %= 1000;
        }

        if (enteros >= 100) {
            if (enteros == 100) {
                resultado.append("CIEN ");
            } else {
                resultado.append(centenas[enteros / 100]).append(" ");
            }
            enteros %= 100;
        }

        if (enteros >= 20) {
            resultado.append(decenas[enteros / 10]);
            if (enteros % 10 != 0) {
                resultado.append(" Y ").append(unidades[enteros % 10]);
            }
        } else if (enteros > 0) {
            resultado.append(unidades[enteros]);
        }

        return resultado.toString().trim() + " CON " + String.format("%02d", decimales) + "/100 SOLES";
    }
}

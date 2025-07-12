/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import Modelo.Venta;
import Modelo.DetalleVenta;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;

public class GeneradorPDF {

    public static byte[] generarPDFComoBytes(Venta venta, int idVenta) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document documento = new Document();

        PdfWriter.getInstance(documento, baos);
        documento.open();

        // Título
        Paragraph titulo = new Paragraph("COMPROBANTE DE VENTA",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
        titulo.setAlignment(Element.ALIGN_CENTER);
        documento.add(titulo);
        documento.add(new Paragraph(" "));

        // Formato de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        // Datos de cabecera
        documento.add(new Paragraph("N° Venta: " + idVenta));
        documento.add(new Paragraph("Cliente: " + venta.getNombreCliente()));
        documento.add(new Paragraph("DNI: " + venta.getDniCliente()));
        documento.add(new Paragraph("Fecha: " + sdf.format(venta.getFecha())));
        documento.add(new Paragraph("Vendedor: " + venta.getNombreUsuario()));
        documento.add(new Paragraph("Tipo: " + venta.getTipoComprobante()));
        documento.add(new Paragraph(" "));

        // Tabla de productos
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new int[]{4, 1, 2, 2});

        tabla.addCell("Producto");
        tabla.addCell("Cant.");
        tabla.addCell("Precio");
        tabla.addCell("Subtotal");

        for (DetalleVenta d : venta.getDetalles()) {
            tabla.addCell(d.getProducto());
            tabla.addCell(String.valueOf(d.getCantidad()));
            tabla.addCell(String.format("S/ %.2f", d.getPrecioUnitario()));
            tabla.addCell(String.format("S/ %.2f", d.getSubtotal()));
        }

        documento.add(tabla);
        documento.add(new Paragraph(" "));

        // Totales
        double total = venta.getTotal();
        double subtotal = total / 1.18;
        double igv = total - subtotal;

        documento.add(new Paragraph(String.format("Subtotal: S/ %.2f", subtotal)));
        documento.add(new Paragraph(String.format("IGV (18%%): S/ %.2f", igv)));
        documento.add(new Paragraph(String.format("Total: S/ %.2f", total)));

        documento.close();
        return baos.toByteArray();
    }
}

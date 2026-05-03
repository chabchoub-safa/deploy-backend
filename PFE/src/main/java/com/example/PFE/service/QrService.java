package com.example.PFE.service;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class QrService {
    public byte[] generatePng(String text, int size) throws Exception {
        var matrix = new com.google.zxing.MultiFormatWriter()
                .encode(text, com.google.zxing.BarcodeFormat.QR_CODE, size, size);

        var out = new ByteArrayOutputStream();
        com.google.zxing.client.j2se.MatrixToImageWriter.writeToStream(matrix, "PNG", out);
        return out.toByteArray();
    }
}

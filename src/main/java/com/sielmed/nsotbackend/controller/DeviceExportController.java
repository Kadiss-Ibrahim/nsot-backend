package com.sielmed.nsotbackend.controller;

import com.sielmed.nsotbackend.service.DeviceExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/v1/devices/export")
@RequiredArgsConstructor
public class DeviceExportController {

    private final DeviceExportService deviceExportService;

    @GetMapping("/excel")
    public ResponseEntity<byte[]> exportExcel() {
        ByteArrayOutputStream out = deviceExportService.exportAllToExcel();
        byte[] bytes = out.toByteArray();

        String filename = "devices_" + LocalDate.now(ZoneOffset.UTC) + ".xlsx";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }
}
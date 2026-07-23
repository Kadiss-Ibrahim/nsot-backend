package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.entity.Device;
import com.sielmed.nsotbackend.entity.Site;
import com.sielmed.nsotbackend.enums.Criticality;
import com.sielmed.nsotbackend.enums.Status;
import com.sielmed.nsotbackend.repository.DeviceRepository;
import com.sielmed.nsotbackend.service.DeviceExportService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceExportServiceImpl implements DeviceExportService {

    private final DeviceRepository deviceRepository;

    private static final String[] HEADERS = {
            "Hostname", "Site", "Ville", "Rôle", "Manufacturer", "Model",
            "Serial Number", "IP Management", "OS", "Version", "Rack",
            "Position Rack", "Statut", "Criticité", "Owner", "Dernière révision"
    };

    @Override
    public ByteArrayOutputStream exportAllToExcel() {
        return buildExcel(deviceRepository.findAll());
    }

    @Override
    public ByteArrayOutputStream exportToExcel(List<Long> deviceIds) {
        return buildExcel(deviceRepository.findAllById(deviceIds));
    }

    private ByteArrayOutputStream buildExcel(List<Device> devices) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Devices");

            createHeaderRow(sheet, workbook);
            populateDeviceRows(sheet, devices);
            autoSizeColumns(sheet);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out;

        } catch (IOException e) {
            throw new UncheckedIOException("Erreur lors de la génération du fichier Excel", e);
        }
    }

    private void createHeaderRow(Sheet sheet, Workbook workbook) {
        CellStyle headerStyle = createHeaderStyle(workbook);
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    private void populateDeviceRows(Sheet sheet, List<Device> devices) {
        int rowIndex = 1;
        for (Device device : devices) {
            Row row = sheet.createRow(rowIndex++);
            populateDeviceRow(row, device);
        }
    }

    private void populateDeviceRow(Row row, Device device) {
        row.createCell(0).setCellValue(device.getHostname());
        row.createCell(1).setCellValue(nullToEmpty(device.getSite(), Site::getNom));
        row.createCell(2).setCellValue(nullToEmpty(device.getSite(), Site::getVille));
        row.createCell(3).setCellValue(nullToEmpty(device.getDeviceRole(), d -> d.getNom()));
        row.createCell(4).setCellValue(nullToEmpty(device.getManufacturer(), d -> d.getNom()));
        row.createCell(5).setCellValue(nullToEmpty(device.getModel()));
        row.createCell(6).setCellValue(nullToEmpty(device.getSerialNumber()));
        row.createCell(7).setCellValue(nullToEmpty(device.getManagementIp()));
        row.createCell(8).setCellValue(nullToEmpty(device.getOs()));
        row.createCell(9).setCellValue(nullToEmpty(device.getCurrentVersion()));
        row.createCell(10).setCellValue(nullToEmpty(device.getRack()));
        row.createCell(11).setCellValue(nullToEmpty(device.getRackPosition()));
        row.createCell(12).setCellValue(nullToEmpty(device.getStatus(), Status::name));
        row.createCell(13).setCellValue(nullToEmpty(device.getCriticality(), Criticality::name));
        row.createCell(14).setCellValue(nullToEmpty(device.getOwner()));
        row.createCell(15).setCellValue(nullToEmpty(device.getLastReview(), d -> d.format(DateTimeFormatter.ISO_DATE)));
    }

    private void autoSizeColumns(Sheet sheet) {
        for (int i = 0; i < HEADERS.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private String nullToEmpty(String value) {
        return value != null ? value : "";
    }

    private <T> String nullToEmpty(T obj, java.util.function.Function<T, String> mapper) {
        return obj != null ? mapper.apply(obj) : "";
    }
}
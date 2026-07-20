package com.sielmed.nsotbackend.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

public interface DeviceExportService {
    ByteArrayOutputStream exportToExcel(List<Long> deviceIds);
    ByteArrayOutputStream exportAllToExcel();
}
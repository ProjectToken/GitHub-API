package com.company.githubreport;

import com.company.githubreport.controller.ReportController;
import com.company.githubreport.model.AccessReport;
import com.company.githubreport.service.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ReportControllerTest {

    @Test
    void getReport_returnsAccessReport() {
        ReportService service = Mockito.mock(ReportService.class);
        ReportController controller = new ReportController(service);

        AccessReport expected = new AccessReport("ProjectToken", List.of());
        Mockito.when(service.buildAccessReport("ProjectToken")).thenReturn(expected);

        ResponseEntity<?> response = controller.getReport("ProjectToken");

        assertEquals(200, response.getStatusCode().value());
        assertSame(expected, response.getBody());
    }
}

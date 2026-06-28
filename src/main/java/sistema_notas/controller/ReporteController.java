package sistema_notas.controller;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private DataSource dataSource;

    // REPORTE GENERAL

    @GetMapping("/alumnos")
    public ResponseEntity<byte[]> generarReporteAlumnos() throws Exception {

        try (
                InputStream reporteStream =
                        new ClassPathResource("reports/alumnos/reporte_alumnos.jasper").getInputStream();

                InputStream logoStream =
                        new ClassPathResource("reports/alumnos/logo.png").getInputStream();

                Connection conexion = dataSource.getConnection()
        ) {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("LOGO", logoStream);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            reporteStream,
                            parametros,
                            conexion
                    );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reporte_alumnos.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        }
    }

    // =========================
    // REPORTE INDIVIDUAL
    // =========================
    @GetMapping("/alumnos/{codigo}")
    public ResponseEntity<byte[]> generarReporteAlumno(
            @PathVariable String codigo
    ) throws Exception {

        try (
                InputStream reporteStream =
                        new ClassPathResource("reports/alumnos/reporte_alumno.jasper").getInputStream();

                InputStream logoStream =
                        new ClassPathResource("reports/alumnos/logo.png").getInputStream();

                Connection conexion = dataSource.getConnection()
        ) {

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("LOGO", logoStream);
            parametros.put("CODIGO", codigo);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            reporteStream,
                            parametros,
                            conexion
                    );

            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=reporte_alumno.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        }
    }



    // =========================
    // REPORTE MATRICULAS
    // =========================

    @GetMapping("/matriculas")
public ResponseEntity<byte[]> generarReporteMatriculas() throws Exception {

    InputStream reporteStream =
            new ClassPathResource(
                    "reports/matriculas/reporte_matriculas.jasper"
            ).getInputStream();

    Map<String, Object> parametros = new HashMap<>();

    parametros.put(
            "LOGO",
            new ClassPathResource(
                    "reports/alumnos/logo.png"
            ).getInputStream()
    );

    Connection conexion = dataSource.getConnection();

    JasperPrint jasperPrint =
            JasperFillManager.fillReport(
                    reporteStream,
                    parametros,
                    conexion
            );

    byte[] pdf =
            JasperExportManager.exportReportToPdf(
                    jasperPrint
            );

    conexion.close();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=reporte_matriculas.pdf"
            )
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}


@GetMapping("/notas")
public ResponseEntity<byte[]> generarReporteNotas() throws Exception {

    InputStream reporteStream =
            new ClassPathResource(
                    "reports/notas/reporte_notas.jasper"
            ).getInputStream();

    Map<String, Object> parametros =
            new HashMap<>();

    parametros.put(
            "LOGO",
            new ClassPathResource(
                    "reports/notas/logo.png"
            ).getInputStream()
    );

    Connection conexion =
            dataSource.getConnection();

    JasperPrint jasperPrint =
            JasperFillManager.fillReport(
                    reporteStream,
                    parametros,
                    conexion
            );

    byte[] pdf =
            JasperExportManager.exportReportToPdf(
                    jasperPrint
            );

    conexion.close();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=reporte_notas.pdf"
            )
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}


// REPORTE HISTORIAL ALUMNO

@GetMapping("/alumnos/{codigo}/historial")
public ResponseEntity<byte[]> generarHistorialAlumno(
        @PathVariable String codigo
) throws Exception {

    InputStream reporteStream =
            new ClassPathResource(
                    "reports/alumnos/reporte_historial_alumno.jasper"
            ).getInputStream();

    Map<String, Object> parametros = new HashMap<>();

    parametros.put(
            "LOGO",
            new ClassPathResource(
                    "reports/alumnos/logo.png"
            ).getInputStream()
    );

    parametros.put("CODIGO", codigo);

    Connection conexion = dataSource.getConnection();

    JasperPrint jasperPrint =
            JasperFillManager.fillReport(
                    reporteStream,
                    parametros,
                    conexion
            );

    byte[] pdf =
            JasperExportManager.exportReportToPdf(jasperPrint);

    conexion.close();

    return ResponseEntity.ok()
            .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=historial_alumno.pdf"
            )
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}

}
package com.example.fa_request_app.fa;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/api/fa-requests")
public class FaController {

  private final FaRequestRepo repo;

  public FaController(FaRequestRepo repo) {
    this.repo = repo;
  }

  @PostMapping
  public ResponseEntity<String> createFaRequest(@Valid @RequestBody FaRequest faRequest) {
    FaRequestEntity entity = new FaRequestEntity();
    entity.setProgramName(faRequest.programName());
    entity.setProductName(faRequest.productName());
    entity.setContact(faRequest.contact());
    entity.setJoNumber(faRequest.joNumber());
    entity.setSubmissionDate(faRequest.submissionDate());
    entity.setNeedByDate(faRequest.needByDate());
    entity.setDescription(faRequest.description());

    repo.save(entity);

    // (Optional) Append a simple log line
    try (PrintWriter out = new PrintWriter(new java.io.FileWriter("fa_requests.log", true))) {
      out.println("New FA Request: " + entity.getId() + " - " + entity.getProgramName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return ResponseEntity.ok("FA Request created successfully");
  }

  @GetMapping
  public List<FaRequestEntity> list() {
    return repo.findAll();
  }

  // Excel-friendly CSV export
  @GetMapping(value = "/download.csv", produces = "text/csv")
public void downloadCsv(HttpServletResponse resp) throws Exception {
    var list = repo.findAll();
    resp.setHeader("Content-Disposition", "attachment; filename=\"fa-requests.csv\"");
    try (PrintWriter w = new PrintWriter(resp.getOutputStream())) {
        w.println("ID,Program Name,Product Name,Contact,JO#,Submission Date,Need By,Description");
        for (var e : list) {
            w.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                e.getId(),
                csv(e.getProgramName()),
                csv(e.getProductName()),
                csv(e.getContact()),
                csv(e.getJoNumber()),
                csv(e.getSubmissionDate()),
                csv(e.getNeedByDate()),
                csv(e.getDescription())
            );
        }
    }
  }

  private static String csv(String s) {
    if (s == null) return "";
    return '"' + s.replace("\"", "\"\"") + '"';
  }
}

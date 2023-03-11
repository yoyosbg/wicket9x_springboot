package app.controllers;

import app.services.AttachmentsService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.Objects.nonNull;

@Controller
@RequestMapping("/files")
public class FilesController {

  @Autowired
  AttachmentsService attachmentsService;

  @GetMapping("/download")
  public ResponseEntity<GridFsResource> downloadFile(@RequestParam String id) {
    GridFSFile item = attachmentsService.findOneById(id);
    if (nonNull(item)) {
      HttpHeaders headers = new HttpHeaders();
      headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
      headers.add("Pragma", "no-cache");
      headers.add("Expires", "0");
      headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + item.getFilename() + "\"");

      return ResponseEntity.ok()
              .headers(headers)
              .contentLength(item.getLength())
              .contentType(MediaType.parseMediaType("application/octet-stream"))
              .body(attachmentsService.getGridFsTemplate().getResource(item));
    } else {
      return ResponseEntity.notFound().build();
    }
  }

}

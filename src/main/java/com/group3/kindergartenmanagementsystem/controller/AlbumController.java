package com.group3.kindergartenmanagementsystem.controller;

import com.group3.kindergartenmanagementsystem.payload.AddAlbumDTO;
import com.group3.kindergartenmanagementsystem.payload.AlbumDTO;
import com.group3.kindergartenmanagementsystem.service.AlbumService;
import com.group3.kindergartenmanagementsystem.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.util.List;

@RestController
@RequestMapping("api/albums")
@AllArgsConstructor
public class AlbumController {
    AlbumService albumService;
    FileStorageService fileStorageService;
    @GetMapping
    public ResponseEntity<List<AlbumDTO>> getAllPictures(){
        return ResponseEntity.ok(albumService.getAllPicture());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDTO> getPictureById(@PathVariable Integer id){
        return ResponseEntity.ok(albumService.getPictureById(id));
    }

    @GetMapping("/child/{id}")
    public ResponseEntity<List<AlbumDTO>> getAlbumByChildId(@PathVariable(value = "id") Integer childId){
        return ResponseEntity.ok(albumService.getAlbumByChildId(childId));
    }

    @PostMapping
    public ResponseEntity<AlbumDTO> createNewAlbum(@ModelAttribute AddAlbumDTO addAlbumDTO){
        return new ResponseEntity<>(albumService.createNewPicture(addAlbumDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePictureById(@PathVariable Integer id){
        albumService.deletePictureById(id);
        return ResponseEntity.ok("Delete successfully");
    }

    @GetMapping("/picture/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename){
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                .body(file);
    }

    @GetMapping("/download/picture/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename){
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
}

package org.streaming.Audio;

import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/audio")
@AllArgsConstructor
public class AudioController {

    private AudioService audioService;


    @GetMapping("/{id}")
    public ResponseEntity<AudioGetDTO> getAudio(@PathVariable Long id) {
        return ResponseEntity.ok(audioService.getAudioByID(id));
    }

    @GetMapping()
    public ResponseEntity<List<AudioGetDTO>> getAllAudio() {
        return ResponseEntity.ok(audioService.getAllAudio());
    }

    @PostMapping
    private ResponseEntity<AudioEntity> createAudio(@RequestBody AudioPostDTO newAudioRequest) {
        AudioEntity savedAudio = audioService.saveAudio(newAudioRequest);
        return ResponseEntity.ok(savedAudio);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<AudioEntity> updateAudio(
            @PathVariable Long id,
            @RequestBody AudioPostDTO audioDetails,
            UriComponentsBuilder ucb) {

        AudioEntity savedAudio = audioService.update(id, audioDetails);
        return ResponseEntity.ok(savedAudio);
    }
    @PostMapping(value = "/{id}/upload-chunk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadChunk(@RequestParam("file") MultipartFile file,
                              @RequestParam("chunkIndex") int chunkIndex,
                              @PathParam("id") Long audioId
    ) throws IOException {
        audioService.uploadChunk(file, chunkIndex, audioId);
        return "Chunk " + chunkIndex + " received";
    }

    @GetMapping("/{id}/download-chunk")
    public ResponseEntity<byte[]> downloadChunk(
            @PathParam("audioID") Long audioId,
            @RequestParam("chunkIndex") int chunkIndex
    ) {
        Optional<FileChunk> fileChunk = audioService.downloadChunk(audioId, chunkIndex);

        if (fileChunk.isPresent()) {
            FileChunk chunk = fileChunk.get();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; audioId=" + chunk.getFile().getAudio().getId() + "-chunk-" + chunkIndex);
            return new ResponseEntity<>(chunk.getData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

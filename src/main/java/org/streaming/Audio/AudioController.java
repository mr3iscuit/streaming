package org.streaming.Audio;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/audio")
@AllArgsConstructor
public class AudioController {

    private IAudioService audioService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<AudioGetDTO> getAudio(@PathVariable Long id) {
        return ResponseEntity.ok(audioService.getAudioByID(id));
    }


    @GetMapping()
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<AudioGetDTO>> getAllAudio() {
        return ResponseEntity.ok(audioService.getAllAudio());
    }


    @PostMapping()
    @PreAuthorize("hasRole('MANAGER')")
    private ResponseEntity<AudioGetDTO> createAudio(@RequestBody AudioPostDTO newAudioRequest) {
        return ResponseEntity.ok(audioService.saveAudio(newAudioRequest));
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    private ResponseEntity<AudioEntity> updateAudio(
            @PathVariable Long id,
            @RequestBody AudioPostDTO audioDetails,
            UriComponentsBuilder ucb) {

        AudioEntity savedAudio = audioService.update(id, audioDetails);
        return ResponseEntity.ok(savedAudio);
    }


    @PostMapping(value = "/{audioId}/upload-chunk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public String uploadChunk(
            @PathVariable Long audioId,
            @RequestParam("chunkIndex") int chunkIndex,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        audioService.uploadChunk(file, chunkIndex, audioId);
        return "Chunk " + chunkIndex + " received";
    }


    @GetMapping("/{audioId}/download-chunk")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<byte[]> downloadChunk(
            @PathVariable Long audioId,
            @RequestParam("chunkIndex") int chunkIndex
    ) {
        Optional<FileChunk> fileChunk = audioService.downloadChunk(audioId, chunkIndex);

        if (fileChunk.isPresent()) {
            FileChunk chunk = fileChunk.get();
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=audioID" + chunk.getFile().getAudio().getId() + "-chunk-" + chunkIndex);
            return new ResponseEntity<>(chunk.getData(), headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}

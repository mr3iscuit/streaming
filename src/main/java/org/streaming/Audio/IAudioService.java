package org.streaming.Audio;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IAudioService {

    public AudioGetDTO getAudioByID(Long id);

    public List<AudioGetDTO> getAllAudio();

    public AudioGetDTO saveAudio(AudioPostDTO poReq);

    public AudioEntity update(Long id, AudioPostDTO poReq);

    public void uploadChunk(MultipartFile file, int chunkIndex, Long audioId) throws IOException;

    public Optional<FileChunk> downloadChunk(Long audioId, int chunkIndex);
}

package org.streaming.Audio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AudioRepository extends JpaRepository<AudioEntity, Long> {

}

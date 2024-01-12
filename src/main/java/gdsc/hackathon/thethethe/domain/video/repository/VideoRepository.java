package gdsc.hackathon.thethethe.domain.video.repository;

import gdsc.hackathon.thethethe.domain.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {

}

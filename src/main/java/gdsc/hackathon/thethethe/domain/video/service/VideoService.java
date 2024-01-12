package gdsc.hackathon.thethethe.domain.video.service;

import gdsc.hackathon.thethethe.domain.video.dto.response.VideoResponse;

import java.util.List;

public interface VideoService {
    void crawlVideo(String keyword);
    List<VideoResponse> getVideoList();
}

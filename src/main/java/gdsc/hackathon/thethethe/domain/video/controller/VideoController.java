package gdsc.hackathon.thethethe.domain.video.controller;

import gdsc.hackathon.thethethe.domain.video.dto.response.VideoResponse;
import gdsc.hackathon.thethethe.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/crawl/{keyword}")
    public String crawlVideo(@PathVariable String keyword) {
        videoService.crawlVideo(keyword);
        return keyword + " 크롤링 완료";
    }

    @GetMapping("")
    public List<VideoResponse> getVideoList() {
        return videoService.getVideoList();
    }
}

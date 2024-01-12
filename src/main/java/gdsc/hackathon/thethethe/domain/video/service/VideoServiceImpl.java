package gdsc.hackathon.thethethe.domain.video.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc.hackathon.thethethe.domain.video.dto.response.VideoResponse;
import gdsc.hackathon.thethethe.domain.video.entity.Video;
import gdsc.hackathon.thethethe.domain.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    @Value("${youtube.api.url}")
    private String youtubeApiUrl;
    @Value("${youtube.api.key}")
    private String youtubeApiKey;

    @Override
    @Transactional
    public void crawlVideo(String keyword) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(youtubeApiUrl)
                .queryParam("key", youtubeApiKey)
                .queryParam("part", "snippet")
                .queryParam("maxResults", 50)
                .queryParam("q", keyword)
                .queryParam("type", "video")
                .queryParam("videoDuration", "medium")
                .build();

        String apiUrl = uriComponents.toUriString();

        RestTemplate restTemplate = new RestTemplate();
        String apiResponse = restTemplate.getForObject(apiUrl, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(apiResponse);

            List<Video> videos = StreamSupport.stream(
                            Spliterators.spliteratorUnknownSize(jsonNode.get("items").elements(), 0), false)
                    .map(item -> {
                        String videoId = item.get("id").get("videoId").asText();
                        String title = item.get("snippet").get("title").asText();
                        String thumbnailUrl = item.get("snippet").get("thumbnails").get("medium").get("url").asText();

                        return Video.builder()
                                .id(videoId)
                                .title(title)
                                .thumbnailUrl(thumbnailUrl)
                                .build();
                    })
                    .toList();

            videoRepository.deleteAll();
            saveVideosInBatch(videos);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing YouTube API response", e);
        }
    }

    private void saveVideosInBatch(List<Video> videos) {
        final int batchSize = 50;
        for (int i = 0; i < videos.size(); i += batchSize) {
            List<Video> batchList = videos.subList(i, Math.min(i + batchSize, videos.size()));
            videoRepository.saveAll(batchList);
            videoRepository.flush();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponse> getVideoList() {
        return videoRepository.findAll().stream()
                .map(video -> VideoResponse.builder()
                        .videoId(video.getId())
                        .title(video.getTitle())
                        .thumbnailUrl(video.getThumbnailUrl())
                        .build())
                .toList();
    }
}

package gdsc.hackathon.thethethe.domain.couple.controller;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleCreateRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleResponse;
import gdsc.hackathon.thethethe.domain.couple.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/couples")
public class CoupleController {

    private final CoupleService coupleService;

    @GetMapping("")
    public CoupleResponse findCouple(Principal principal) {
        return coupleService.findCouple(principal.getName());
    }

    @PostMapping("")
    public String createCouple(Principal principal, @RequestBody CoupleCreateRequest coupleCreateRequest) {
        return coupleService.createCouple(principal.getName(), coupleCreateRequest);
    }

    @PostMapping("{secretCode}")
    public String acceptCouple(Principal principal, @PathVariable("secretCode") String secretCode) {
        return coupleService.acceptCouple(principal.getName(), secretCode);
    }

    @GetMapping("/ranking")
    public List<CoupleResponse> findCoupleRanking() {
        return coupleService.findCoupleRanking();
    }
}

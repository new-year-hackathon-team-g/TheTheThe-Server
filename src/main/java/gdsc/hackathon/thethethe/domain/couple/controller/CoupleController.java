package gdsc.hackathon.thethethe.domain.couple.controller;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleSaveRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.request.PopUpdateRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleFindResponse;
import gdsc.hackathon.thethethe.domain.couple.dto.response.PopFindResponse;
import gdsc.hackathon.thethethe.domain.couple.service.CoupleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController

public class CoupleController {
    private final CoupleService coupleService;

    @GetMapping("/couples/{id}")
    public CoupleFindResponse findCoupleDetail(@PathVariable("id") Long id){
        return  coupleService.findCouple(id);
    }

    @GetMapping("/couples")
    public List<CoupleFindResponse> findCoupleRanking(){
        return coupleService.findCoupleRanking();
    }

    // Todo
    @PostMapping("/couples")
    public void saveCouple(@RequestBody CoupleSaveRequest coupleSaveRequest){
        coupleService.saveCouple(coupleSaveRequest);
    }

    @DeleteMapping("/couples/{id}")
    public void deleteCouple(@PathVariable("id") Long id){
        coupleService.deleteCouple(id);
    }

    @GetMapping("/pops")
    public PopFindResponse findCouplePop(Principal principal){
        return coupleService.findPop(Long.valueOf(principal.getName()));
    }

    @PatchMapping("/pops")
    public void updateCouplePop(@RequestBody PopUpdateRequest popUpdateRequest, Principal principal){
       coupleService.updatePop(Long.valueOf(principal.getName()),popUpdateRequest);
    }
}

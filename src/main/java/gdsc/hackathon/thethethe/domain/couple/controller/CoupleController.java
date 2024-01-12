package gdsc.hackathon.thethethe.domain.couple.controller;

import gdsc.hackathon.thethethe.domain.couple.dto.request.CoupleSaveRequest;
import gdsc.hackathon.thethethe.domain.couple.dto.response.CoupleFindResponse;
import gdsc.hackathon.thethethe.domain.couple.service.CoupleService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/couples")
    public void saveCouple(@ModelAttribute CoupleSaveRequest coupleSaveRequest){
        coupleService.saveCouple(coupleSaveRequest);
    }

    @DeleteMapping("/couples/{id}")
    public void deleteCouple(@PathVariable("id") Long id){
        coupleService.deleteCouple(id);
    }

//    @GetMapping("/couples/{id}/pop")
//    public void findCouplePop( , Principal principal){
//
//    }
}

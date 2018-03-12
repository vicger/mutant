package com.exercise.controller;

import com.exercise.dto.MutantRequestDTO;
import com.exercise.dto.MutantStatsDTO;
import com.exercise.service.MutantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vgerb on 3/9/18.
 */
@RestController
@RequestMapping("/mutant")
@Api(value = "Mutant API", description = "Services to validate mutants and expose stats")
public class MutantController {

    private MutantService mutantService;

    @Autowired
    public MutantController(MutantService mutantService){
        this.mutantService = mutantService;
    }


    @PostMapping
    @ApiOperation(value = "Validates a mutant", notes = "This service validates a mutant based on the given DNA")
    public ResponseEntity isMutant(@RequestBody MutantRequestDTO mutantRequestDTO) {
        mutantRequestDTO.setMutant(mutantService.isMutant(mutantRequestDTO.getDna()));
        mutantService.process(mutantRequestDTO);
        if(mutantRequestDTO.isMutant()){
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/stats")
    @ApiOperation(value = "Inform stats", notes = "This service informs current stats")
    public ResponseEntity<MutantStatsDTO> stats() {
        MutantStatsDTO mutantStatsDTO = this.mutantService.getStats();
        return new ResponseEntity<>(mutantStatsDTO, HttpStatus.OK);
    }

}

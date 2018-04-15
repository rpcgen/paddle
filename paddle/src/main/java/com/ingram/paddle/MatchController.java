package com.ingram.paddle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1")
public class MatchController {

    private MatchRepository matchRepository;

    @Autowired
    public void setMatchRepository(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @RequestMapping(
            path     = "/matches",
            method   = POST)
    public MatchDto save(@RequestBody MatchDto match) throws Exception {
        return matchRepository.save(match);
    }

    @RequestMapping(
            path     = "/matches/{id}",
            method   = GET)
    public MatchDto find(@PathVariable long id) throws Exception {
        Optional<MatchDto> dto = matchRepository.find(id);
        if (dto.isPresent())
            return dto.get();
        else
            throw new NotFoundException();
    }

    @RequestMapping(
            path     = "/matches",
            method   = GET)
    public Collection<MatchDto> findAll(@RequestBody MatchDto match) throws Exception {
        return matchRepository.findAll();
    }
}

package com.ingram.paddle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/v1")
public class PointController {

    private PointRepository pointRepository;

    @Autowired
    public void setPointRepository(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    private MatchRepository matchRepository;

    @Autowired
    public void setMatchRepository(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @RequestMapping(
            path     = "/points",
            method   = POST)
    public PointDto score(@RequestBody PointDto point) throws Exception {
        pointRepository.save(point);
        matchRepository.find(point.matchId).ifPresent(match -> {

            if (match.score1 != Score.WINNER && match.score2 != Score.WINNER)
                if (point.player.equals(match.player1)) {
                    Score[] newscore = updateScore(match.score1, match.score2);
                    match.score1 = newscore[0];
                    match.score2 = newscore[1];
                } else {
                    Score[] newscore = updateScore(match.score2, match.score1);
                    match.score2 = newscore[0];
                    match.score1 = newscore[1];
                }
        });
        return point;
    }

    private Score[] updateScore(Score score1, Score score2) {
        switch (score1) {
            case _00:
                return new Score[] {Score._15, score2};

            case _15:
                return new Score[] {Score._30, score2};

            case _30:
                if (score2 == Score._40)
                    return new Score[] {Score.DEUCE, Score.DEUCE};
                else
                    return new Score[] {Score._40, score2};

            case _40:
                return new Score[] {Score.WINNER, score2};

            case DEUCE:
                if (score2 == Score.ADVANTAGE)
                    return new Score[] {Score.DEUCE, Score.DEUCE};
                else
                    return new Score[] {Score.ADVANTAGE, Score.DEUCE};

            case ADVANTAGE:
                return new Score[] {Score.WINNER, score2};

            default:
                return new Score[] {score1, score2};
        }
    }
}

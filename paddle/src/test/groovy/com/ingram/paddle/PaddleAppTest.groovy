package com.ingram.paddle

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PaddleAppTest extends Specification {

    @Autowired
    MatchController matchController

    @Autowired
    PointController pointController

    def 'player 1 wins'() {

        MatchDto dto = matchController.save(new MatchDto(
                player1: 'p1',
                player2: 'p2'
        ))

        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score._15
        matchController.find(dto.id).score2 == Score._00


        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score._30
        matchController.find(dto.id).score2 == Score._00

        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score._40
        matchController.find(dto.id).score2 == Score._00

        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.WINNER
        matchController.find(dto.id).score2 == Score._00
    }

    def 'player 2 wins'() {

        MatchDto dto = matchController.save(new MatchDto(
                player1: 'p1',
                player2: 'p2'
        ))

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score2 == Score._15
        matchController.find(dto.id).score1 == Score._00


        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score2 == Score._30
        matchController.find(dto.id).score1 == Score._00

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score2 == Score._40
        matchController.find(dto.id).score1 == Score._00

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score2 == Score.WINNER
        matchController.find(dto.id).score1 == Score._00
    }

    def 'deuce/advantage'() {

        MatchDto dto = matchController.save(new MatchDto(
                player1: 'p1',
                player2: 'p2'
        ))

        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score2 == Score.DEUCE
        matchController.find(dto.id).score1 == Score.DEUCE

        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.ADVANTAGE
        matchController.find(dto.id).score2 == Score.DEUCE

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.DEUCE
        matchController.find(dto.id).score2 == Score.DEUCE

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.DEUCE
        matchController.find(dto.id).score2 == Score.ADVANTAGE

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.DEUCE
        matchController.find(dto.id).score2 == Score.WINNER
    }

    def 'a match with a winner cannot be modified'() {

        MatchDto dto = matchController.save(new MatchDto(
                player1: 'p1',
                player2: 'p2'
        ))

        when:

        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))
        pointController.score(new PointDto(
                player: 'p1',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.WINNER
        matchController.find(dto.id).score2 == Score._00

        when:

        pointController.score(new PointDto(
                player: 'p2',
                matchId: dto.id
        ))

        then:

        matchController.find(dto.id).score1 == Score.WINNER
        matchController.find(dto.id).score2 == Score._00
    }
}

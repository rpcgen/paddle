package com.ingram.paddle;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class MatchRepository {

    private static Map<Long, MatchDto> map = new HashMap<>();

    public MatchDto save(MatchDto match) {
        match.id = map.size();
        match.score1 = Score._00;
        match.score2 = Score._00;
        map.put(match.id, match);
        return match;
    }

    public Optional<MatchDto> find(long id) {
        if (map.containsKey(id))
            return Optional.of(map.get(id));
        else
            return Optional.empty();
    }

    public Collection<MatchDto> findAll() {
        return Collections.unmodifiableCollection(map.values());
    }
}

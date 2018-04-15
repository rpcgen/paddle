package com.ingram.paddle;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class PointRepository {

    private static List<PointDto> list = new LinkedList<>();

    public PointDto save(PointDto point) {
        list.add(point);
        return point;
    }

    public List<PointDto> findAll() {
        return Collections.unmodifiableList(list);
    }
}

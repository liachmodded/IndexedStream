package com.github.liachmodded.indexedstream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

@SuppressWarnings("preview")
class IndexedGatherersTest {
    @Test
    void testGatherIndex() {
        var l = List.of(1,2,3).stream()
                .gather(IndexedGatherers.gatherIndex())
                .filter((idd) -> idd.item()*idd.index() >= 2)
                .map((idd) -> idd.index() * idd.item())
                .toList();
        Assertions.assertEquals(List.of(2, 6), l);
    }

    @Test
    void testLocalGather() {
        var l = List.of(1,2,3).stream()
                .gather(IndexedGatherers.filter((idx, val) -> idx*val >= 2))
                .gather(IndexedGatherers.map((idx, val) -> idx * val))
                .toList();
        Assertions.assertEquals(List.of(0, 3), l);
    }
}

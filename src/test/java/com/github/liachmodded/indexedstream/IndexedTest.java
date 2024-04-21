package com.github.liachmodded.indexedstream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Gatherer;
import java.util.stream.IntStream;

@SuppressWarnings("preview")
class IndexedTest {
    @Test
    void testCollector() {
        var l = IntStream.range(0, 100)
                .boxed()
                .collect(Indexed.collector(Collectors.toList()));
        for (var idd : l) {
            Assertions.assertEquals(idd.item().intValue(), idd.index());
        }
    }

    @Test
    void testCollectorFailParallel() {
        var l = IntStream.range(0, 100)
                .boxed()
                .parallel();
        Assertions.assertThrows(UnsupportedOperationException.class, () ->
                l.collect(Indexed.collector(Collectors.toList())));
    }

    @Test
    void testGatherer() {
        var l = IntStream.range(0, 100)
                .boxed()
                .<Integer>gather(Indexed.gatherer(Gatherer.ofSequential((_, indexed, sink) ->
                        indexed.index() % 2 != 0 || sink.push(indexed.item()))))
                .toList();
        for (var item : l) {
            Assertions.assertEquals(0, item % 2, () -> "item " + item);
        }
    }

    @Test
    @Disabled // Gatherer is not parallel in current state...
    void testGathererFailParallel() {
        // TODO
        var l = IntStream.range(0, 100)
                .boxed()
                .parallel()
        .<Integer>gather(Indexed.gatherer(Gatherer.of((_, indexed, sink) ->
                                indexed.index() % 2 != 0 || sink.push(indexed.item()))))
                        .toList();
        for (var item : l) {
            Assertions.assertEquals(0, item % 2, () -> "item " + item);
        }
    }
}

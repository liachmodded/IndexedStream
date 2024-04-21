package com.github.liachmodded.indexedstream;

import java.util.stream.Gatherer;

/**
 * A few gatherers for indexed stream operations.
 */
@SuppressWarnings("preview")
public final class IndexedGatherers {
    @SuppressWarnings("rawtypes")
    private static final Gatherer GATHER_INDEX = Gatherer.ofSequential(() -> new int[] {0},
            Gatherer.Integrator.ofGreedy((counter, v, sink) ->
                    sink.push(new IndexedImpl<>(counter[0]++, v))));

    @SuppressWarnings("unchecked")
    public static <T> Gatherer<T, ?, Indexed<T>> gatherIndex() {
        return (Gatherer<T, ?, Indexed<T>>) GATHER_INDEX;
    }

    public interface IndexedFunction<T, R> {
        R apply(int idx, T val);
    }

    public interface IndexedPredicate<T> {
        boolean test(int idx, T val);
    }

    public static <T, R> Gatherer<T, ?, R> map(IndexedFunction<T, R> function) {
        return Indexed.gatherer(Gatherer.ofSequential(Gatherer.Integrator.ofGreedy((_, indexed, sink) ->
                sink.push(function.apply(indexed.index(), indexed.item())))));
    }

    public static <T> Gatherer<T, ?, T> filter(IndexedPredicate<T> predicate) {
        return Indexed.gatherer(Gatherer.ofSequential(Gatherer.Integrator.ofGreedy((_, indexed, sink) ->
                !predicate.test(indexed.index(), indexed.item()) || sink.push(indexed.item()))));
    }
}

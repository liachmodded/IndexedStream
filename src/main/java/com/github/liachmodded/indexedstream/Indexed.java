package com.github.liachmodded.indexedstream;

import java.util.stream.Collector;
import java.util.stream.Gatherer;

/**
 * An indexed item, and main entrypoint to indexed APIs.
 *
 * @param <T> the item type
 */
@SuppressWarnings("preview")
public interface Indexed<T> {

    /**
     * Creates a sequential collector from another collector that
     * accepts an index.
     *
     * @param collector the collector that takes an index
     * @param <T>       element type
     * @param <A>       intermediate/stateful type
     * @param <R>       result type
     * @return the index-generating, sequential collector
     */
    static <T, A, R> Collector<T, ?, R> collector(Collector<Indexed<T>, A, R> collector) {
        return new IndexedCollector<>(collector);
    }

    /**
     * Creates a sequential gatherer from another gatherer that
     * accepts an index.
     *
     * @param gatherer the gatherer that takes an index
     * @param <T>      element type
     * @param <A>      intermediate/stateful type
     * @param <R>      result type
     * @return the index-generating, sequential gatherer
     */
    static <T, A, R> Gatherer<T, ?, R> gatherer(Gatherer<Indexed<T>, A, R> gatherer) {
        return new IndexedGatherer<>(gatherer);
    }

    /**
     * {@return the index of the item}
     */
    int index();

    /**
     * {@return the item}
     */
    T item();
}

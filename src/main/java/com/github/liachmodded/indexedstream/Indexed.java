package com.github.liachmodded.indexedstream;

import java.util.stream.Collector;
import java.util.stream.Gatherer;

/**
 * An indexed item.
 *
 * @param <T> the item type
 */
@SuppressWarnings("preview")
public interface Indexed<T> {

    static <T, A, R> Collector<T, ?, R> collector(Collector<Indexed<T>, A, R> collector) {
        return new IndexedCollector<>(collector);
    }

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

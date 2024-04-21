package com.github.liachmodded.indexedstream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Gatherer;

@SuppressWarnings("preview")
record IndexedCollector<T, A, R>(Collector<Indexed<T>, A, R> collector) implements Collector<T, IndexState<A>, R> {
    @Override
    public Supplier<IndexState<A>> supplier() {
        var supp = collector.supplier();
        return () -> new IndexState<>(supp.get());
    }

    @Override
    public BiConsumer<IndexState<A>, T> accumulator() {
        var accm = collector.accumulator();
        return (ia, value) -> accm.accept(ia.data, new IndexedImpl<>(ia.index++, value));
    }

    @Override
    public BinaryOperator<IndexState<A>> combiner() {
        return Gatherer.defaultCombiner();
    }

    @Override
    public Function<IndexState<A>, R> finisher() {
        var fin = collector.finisher();
        return is -> fin.apply(is.data);
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(); // No characteristic applies
    }
}

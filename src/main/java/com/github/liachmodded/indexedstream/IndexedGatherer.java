package com.github.liachmodded.indexedstream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

@SuppressWarnings("preview")
record IndexedGatherer<T, A, R>(Gatherer<Indexed<T>, A, R> gatherer) implements Gatherer<T, IndexState<A>, R> {
    @Override
    public Integrator<IndexState<A>, T, R> integrator() {
        var integrator = gatherer.integrator();
        return integrator instanceof Integrator.Greedy<A, Indexed<T>, R> greedy ?
                Integrator.ofGreedy((is, t, downstream) -> greedy.integrate(is.data, new IndexedImpl<>(is.index++, t), downstream)) :
                Integrator.of((is, t, downstream) -> integrator.integrate(is.data, new IndexedImpl<>(is.index++, t), downstream));
    }

    @Override
    public Supplier<IndexState<A>> initializer() {
        var init = gatherer.initializer();
        return () -> new IndexState<>(init.get());
    }

    @Override
    public BinaryOperator<IndexState<A>> combiner() {
        return Gatherer.defaultCombiner();
    }

    @Override
    public BiConsumer<IndexState<A>, Downstream<? super R>> finisher() {
        var fin = gatherer.finisher();
        return (is, downstream) -> fin.accept(is.data, downstream);
    }
}

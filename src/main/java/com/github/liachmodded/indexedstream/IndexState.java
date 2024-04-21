package com.github.liachmodded.indexedstream;

final class IndexState<T> {
    int index;
    final T data;

    IndexState(T data) {
        this.data = data;
    }
}

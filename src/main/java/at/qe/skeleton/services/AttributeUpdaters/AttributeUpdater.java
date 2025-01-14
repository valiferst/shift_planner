package at.qe.skeleton.services.AttributeUpdaters;

@FunctionalInterface
public interface AttributeUpdater<T, V> {
    void update(T target, V value);
}

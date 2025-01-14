package at.qe.skeleton.services.AttributeUpdaters;

public class IdUpdater {
    public void update(Object target, Object value) {
        try {
            target.getClass().getMethod("setId", Long.class).invoke(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}



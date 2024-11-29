package management.exception;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public EntityNotFoundException(Class<?> entityClass, Object id) {
        super(entityClass.getSimpleName() + id);
    }
}

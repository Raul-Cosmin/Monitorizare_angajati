package monitorizare.validation;

public interface Validator<T> {
    void validate(T entity) throws RuntimeException;

}

package app.main.hierarchy;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransactionalInterface {

    void foo();

}

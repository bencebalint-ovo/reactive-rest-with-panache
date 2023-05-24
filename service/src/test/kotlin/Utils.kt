import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.test.vertx.UniAsserter
import io.quarkus.test.vertx.UniAsserterInterceptor
import io.smallrye.mutiny.Uni
import java.util.function.Supplier


//fun UniAsserter.withTransaction(block: (recv: UniAsserter) -> Unit) = block(TransactionalUniAsserterInterceptor(this))


@Suppress("ReactiveStreamsUnusedPublisher")
fun PersonRepository.createTestPerson(asserter: UniAsserter, person: Person) = asserter.withTransaction {
    execute(Supplier { persist(person) })
}.let {
    person
}

fun UniAsserter.withTransaction(block: UniAsserter.() -> Unit) = block(TransactionalUniAsserterInterceptor(this))

class TransactionalUniAsserterInterceptor(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {
    override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> =
        Supplier { Panache.withTransaction(uniSupplier) }
}

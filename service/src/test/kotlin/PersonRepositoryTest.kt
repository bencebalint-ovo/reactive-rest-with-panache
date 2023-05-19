import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.quarkus.test.vertx.UniAsserterInterceptor
import io.smallrye.mutiny.Uni
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import java.util.function.Supplier


@QuarkusTest
@Suppress("ReactiveStreamsUnusedPublisher")
class PersonRepositoryTest {
    @Inject
    lateinit var repository: PersonRepository

    @Test
    @RunOnVertxContext
    fun `repository set up correctly`(asserter: UniAsserter) {
        TransactionalUniAsserterInterceptor(asserter).run {
            execute(Supplier { repository.persist(Person().apply { name = "D. B. Testerson" }) })
            assertEquals({ repository.count() }, 1)
            execute(Supplier { repository.deleteAll() })
        }
    }

    class TransactionalUniAsserterInterceptor(asserter: UniAsserter) : UniAsserterInterceptor(asserter) {
        override fun <T> transformUni(uniSupplier: Supplier<Uni<T>>): Supplier<Uni<T>> =
            Supplier { Panache.withTransaction(uniSupplier) }
    }
}

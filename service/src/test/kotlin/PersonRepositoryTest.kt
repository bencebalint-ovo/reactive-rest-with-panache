import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import jakarta.inject.Inject
import org.junit.jupiter.api.Test


@QuarkusTest
@Suppress("ReactiveStreamsUnusedPublisher")
class PersonRepositoryTest {
    @Inject
    lateinit var repository: PersonRepository

    @Test
    @RunOnVertxContext
    fun `repository set up correctly`(asserter: UniAsserter) = asserter.withTransaction {
        assertThat({ repository.findById(1) }, { person ->
            person.id shouldBe 1
            person.name shouldBe "Repo Testerson"
        })
    }
}

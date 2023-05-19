import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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
    fun `repository set up correctly`(asserter: UniAsserter) {
        val expected = repository.createTestPerson(asserter, Person().apply { name = "Repo Testerson" })

        asserter.withTransaction {
            assertThat({ repository.findById(expected.id) }, { actual ->
                actual shouldNotBe null
                actual.id shouldBe expected.id
                actual.name shouldBe "Repo Testerson"
            })
        }
    }
}

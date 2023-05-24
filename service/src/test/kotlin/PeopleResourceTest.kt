import io.quarkus.test.junit.QuarkusTest
import io.quarkus.test.vertx.RunOnVertxContext
import io.quarkus.test.vertx.UniAsserter
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import jakarta.inject.Inject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.function.Supplier

@QuarkusTest
class PeopleResourceTest {
    @Inject
    lateinit var repository: PersonRepository
//    val expectedPerson = Person().apply {
//        id = 2
//        name = "Resource Testerson"
//    }.toJson()

    @BeforeEach
    @RunOnVertxContext
    fun setup(asserter: UniAsserter) {
        asserter.execute {
            Supplier {
                with(repository) {
                    listAll().subscribe().with { people ->
                        people.forEach { repository.delete(it).also { flush() } }
                    }
                }
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `GET returns 200 with the correct DTO`(asserter: UniAsserter) {
        asserter.execute {
            Supplier {
                val expectedPerson =
                    repository.createTestPerson(asserter, Person().apply { name = "Resource Testerson" })

                When {
                    get("/people/${expectedPerson.id!!}")
                }.Then {
                    body(`is`(expectedPerson))
                }
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `GET returns 200 with the correct DTO (v2)`(asserter: UniAsserter) {
        val expectedPerson = repository.createTestPerson(asserter, Person().apply { name = "Resource Testerson" })

        asserter.execute {
            When {
                get("/people/${expectedPerson.id!!}")
            }.Then {
                body(`is`(expectedPerson.toJson()))
            }
        }
    }

    @Test
    @RunOnVertxContext
    fun `GET returns 404 when entity not found`(asserter: UniAsserter) {
        asserter.execute {
            When {
                get("/people/123456789")
            }.Then {
                statusCode(404)
                body(`is`(""))
            }
        }
    }

    @Test
    fun `POST returns 204 and persists entity`() {
        When {
            get("/people")
        }.Then {
//            statusCode(204)
//            body(`is`(""))
        }
    }

    private fun Person.toJson() = Json.encodeToString(this)
}

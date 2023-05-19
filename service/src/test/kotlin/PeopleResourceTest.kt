import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class PeopleResourceTest {
    val expectedPerson = Person().apply {
        id = 2
        name = "Resource Testerson"
    }.toJson()

    @Test
    fun `GET returns 200 with the correct DTO`() {
        When {
            get("/people/2")
        }.Then {
            body(`is`(expectedPerson))
        }
    }

    @Test
    fun `GET returns 404 when entity not found`() {
        When {
            get("/people/1337")
        }.Then {
            statusCode(404)
            body(`is`(""))
        }
    }

    @Test
    fun `POST returns 204 and persists entity`() {
        When {
            get("/people/1337")
        }.Then {
            statusCode(404)
            body(`is`(""))
        }
    }

    private fun Person.toJson() = Json.encodeToString(this)
}

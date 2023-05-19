import io.quarkus.test.junit.QuarkusTest
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test

@QuarkusTest
class PeopleResourceTest {
    @Test
    fun `GET returns 200 with the correct DTO`() {
        val expectedPerson = Person().apply { id = 2; name = "Resource Testerson" }.toJson()

        When {
            get("/people/2")
        }.Then {
            body(`is`(expectedPerson))
        }.Extract { "person" }
    }

    private fun Person.toJson() = Json.encodeToString(this)
}

import io.kotest.matchers.shouldBe
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

@QuarkusTest
class HelloServiceTest {
    @Inject
    lateinit var helloService: HelloService

    @Test
    fun hello() {
        helloService.hello() shouldBe "hello"
    }
}
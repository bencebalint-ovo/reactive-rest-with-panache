import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class HelloService {
    fun hello() = "hello"
}
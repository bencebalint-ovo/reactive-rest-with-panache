import io.quarkus.hibernate.reactive.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Person {
    @Id
    @GeneratedValue
    @Suppress("unused")
    var id: Long? = null

    lateinit var name: String
}

@ApplicationScoped
class PersonRepository: PanacheRepository<Person>

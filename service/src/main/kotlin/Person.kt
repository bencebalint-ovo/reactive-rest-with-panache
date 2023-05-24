import io.quarkus.hibernate.reactive.panache.PanacheRepository
import io.quarkus.hibernate.reactive.rest.data.panache.PanacheRepositoryResource
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import kotlinx.serialization.Serializable

@Entity
@Serializable
class Person {
    @Id
    @GeneratedValue
    var id: Long? = null

    lateinit var name: String
}

@ApplicationScoped
class PersonRepository : PanacheRepository<Person>

@Suppress("unused")
interface PeopleResource : PanacheRepositoryResource<PersonRepository, Person, Long>

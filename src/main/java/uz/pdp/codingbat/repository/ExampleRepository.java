package uz.pdp.codingbat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.codingbat.entity.Example;

public interface ExampleRepository extends JpaRepository<Example,Integer> {

}
